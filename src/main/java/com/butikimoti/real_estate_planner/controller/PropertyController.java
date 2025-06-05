package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.property.PropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.entity.PropertyPicture;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.service.BasePropertyService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import com.butikimoti.real_estate_planner.service.util.CloudinaryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

@Controller
@RequestMapping("/properties")
public class PropertyController {
    private final BasePropertyService basePropertyService;
    private final UserEntityService userEntityService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public PropertyController(BasePropertyService basePropertyService, UserEntityService userEntityService, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.basePropertyService = basePropertyService;
        this.userEntityService = userEntityService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("addPropertyData")
    public AddPropertyDTO addPropertyData() {
        return new AddPropertyDTO();
    }

    @GetMapping("/sales")
    public String viewSales(@PageableDefault(size = 10, sort = "updatedOn", direction = Sort.Direction.DESC) Pageable pageable,
                            Model model) {
        PagedModel<PropertyDTO> properties = basePropertyService.getAllPropertiesByCompany(pageable, OfferType.SALE);
        model.addAttribute("properties", properties);
        model.addAttribute("page", "sales");
        return "properties";
    }

    @GetMapping("/rents")
    public String viewRents(@PageableDefault(size = 10, sort = "updatedOn", direction = Sort.Direction.DESC) Pageable pageable,
                            Model model) {
        PagedModel<PropertyDTO> properties = basePropertyService.getAllPropertiesByCompany(pageable, OfferType.RENT);
        model.addAttribute("properties", properties);
        model.addAttribute("page", "rents");
        return "properties";
    }

    @GetMapping("/add")
    public String viewAddProperty() {
        return "add-property";
    }

    @PostMapping("/add")
    public String addProperty(@Valid AddPropertyDTO addPropertyData,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addPropertyData", addPropertyData);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addPropertyData", bindingResult);
            return "redirect:/properties/add";
        }

        BaseProperty savedProperty = basePropertyService.savePropertyToDB(addPropertyData);
        if (savedProperty != null) {
            redirectAttributes.addFlashAttribute("propertySaved", true);
        } else {
            redirectAttributes.addFlashAttribute("propertySaved", false);
        }
        return "redirect:/";
    }

    @GetMapping("/property-page")
    public String getOfferPage() {
        return "property-page";
    }

    @GetMapping("/{id}")
    public String getPropertyInfoPage(@PathVariable UUID id, Model model) {
        PropertyDTO propertyData = getPropertyDTOById(id);

        if (!isCompanyMatching(propertyData)) {
            return "unauthorized";
        }

        model.addAttribute("property", propertyData);
        return "property-page";
    }

    @PostMapping("/{id}/upload-picture")
    public String uploadPicture(@PathVariable UUID id,
                                @RequestParam("image") MultipartFile file) throws IOException {
        BaseProperty property = basePropertyService.getPropertyByID(id);
        String imageUrl = cloudinaryService.uploadImage(file);

        PropertyPicture picture = new PropertyPicture();
        picture.setPictureLink(imageUrl);
        property.getPictures().add(picture);
        basePropertyService.updateProperty(property);

        return "redirect:/properties/" + id;
    }

    private PropertyDTO getPropertyDTOById(UUID id) {
        return modelMapper.map(basePropertyService.getPropertyByID(id), PropertyDTO.class);
    }

    private boolean isCompanyMatching(PropertyDTO property) {
        String userCompanyName = userEntityService.getCurrentUser().getCompany().getName();

        return userCompanyName.equalsIgnoreCase(property.getOwnerCompanyName());
    }


    private OfferType mapOfferType(String offerType) {
        if (offerType.equalsIgnoreCase("sale")) {
            return OfferType.SALE;
        }

        if (offerType.equalsIgnoreCase("rent")) {
            return OfferType.RENT;
        }

        throw new IllegalArgumentException("Invalid saleOrRent: " + offerType);
    }



}
