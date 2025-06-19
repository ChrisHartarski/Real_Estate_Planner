package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.property.PropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.util.CloudinaryImageInfoDTO;
import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.entity.PropertyPicture;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.model.enums.PropertyType;
import com.butikimoti.real_estate_planner.service.BasePropertyService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import com.butikimoti.real_estate_planner.service.util.CloudinaryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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
    public String viewSales(
            @RequestParam(value = "propertyType", required = false) PropertyType propertyType,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "neighbourhood", required = false) String neighbourhood,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @PageableDefault(size = 10, sort = "updatedOn", direction = Sort.Direction.DESC) Pageable pageable,
                            Model model) {

        model.addAttribute("pageType", "sales");

        return viewProperties(propertyType, city, neighbourhood, address, minPrice, maxPrice, pageable, model);
    }

    @GetMapping("/rents")
    public String viewRents(@RequestParam(value = "propertyType", required = false) PropertyType propertyType,
                            @RequestParam(value = "city", required = false) String city,
                            @RequestParam(value = "neighbourhood", required = false) String neighbourhood,
                            @RequestParam(value = "address", required = false) String address,
                            @RequestParam(value = "minPrice", required = false) Double minPrice,
                            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                            @PageableDefault(size = 10, sort = "updatedOn", direction = Sort.Direction.DESC) Pageable pageable,
                            Model model) {

        model.addAttribute("pageType", "rents");

        return viewProperties(propertyType, city, neighbourhood, address, minPrice, maxPrice, pageable, model);
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

        BaseProperty savedProperty = basePropertyService.saveNewPropertyToDB(addPropertyData);
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

    @DeleteMapping("/{id}")
    public String deleteProperty(@PathVariable UUID id) throws IOException {
        OfferType offerType = basePropertyService.getPropertyByID(id).getOfferType();
        basePropertyService.deleteProperty(id);

        if(offerType == OfferType.SALE) {
            return "redirect:/properties/sales";
        }

        if (offerType == OfferType.RENT) {
            return "redirect:/properties/rents";
        }

        return "redirect:/";
    }

    @PostMapping("/{id}/upload-picture")
    public String uploadPicture(@PathVariable UUID id,
                                @RequestParam("image") MultipartFile file) throws IOException {
        BaseProperty property = basePropertyService.getPropertyByID(id);
        CloudinaryImageInfoDTO cloudinaryImageInfo = cloudinaryService.uploadImage(file);
        String imageUrl = cloudinaryImageInfo.getImageUrl();
        String imagePublicId = cloudinaryImageInfo.getPublicId();

        PropertyPicture picture = new PropertyPicture(imageUrl, property, imagePublicId);
        property.getPictures().add(picture);
        basePropertyService.savePropertyToDB(property);

        return "redirect:/properties/" + id;
    }

    @DeleteMapping("/{id}/delete-picture/{pictureId}")
    public String deletePicture(@PathVariable UUID id, @PathVariable UUID pictureId) throws IOException {
        basePropertyService.deletePicture(id, pictureId);

        return "redirect:/properties/" + id;
    }

    private String viewProperties(PropertyType propertyType, String city, String neighbourhood, String address, Double minPrice, Double maxPrice, Pageable pageable, Model model) {
        Page<PropertyDTO> properties = basePropertyService.getAllPropertiesByCompany(pageable, OfferType.SALE, propertyType, city, neighbourhood, address, minPrice, maxPrice);
        model.addAttribute("properties", properties);
        model.addAttribute("propertyTypeParam", propertyType);
        model.addAttribute("cityParam", city);
        model.addAttribute("neighbourhoodParam", neighbourhood);
        model.addAttribute("addressParam", address);
        model.addAttribute("minPriceParam", minPrice);
        model.addAttribute("maxPriceParam", maxPrice);

        return "properties";
    }

    private PropertyDTO getPropertyDTOById(UUID id) {
        return modelMapper.map(basePropertyService.getPropertyByID(id), PropertyDTO.class);
    }

    private boolean isCompanyMatching(PropertyDTO property) {
        String userCompanyName = userEntityService.getCurrentUser().getCompany().getName();

        return userCompanyName.equalsIgnoreCase(property.getOwnerCompanyName());
    }
}
