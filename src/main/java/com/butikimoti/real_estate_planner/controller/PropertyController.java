package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.model.dto.comment.AddCommentDTO;
import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.property.EditPropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.property.PropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.util.CloudinaryImageInfoDTO;
import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.entity.PropertyPicture;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.model.enums.PropertyType;
import com.butikimoti.real_estate_planner.service.BasePropertyService;
import com.butikimoti.real_estate_planner.service.CommentService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import com.butikimoti.real_estate_planner.service.util.CloudinaryService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/properties")
public class PropertyController {
    private final BasePropertyService basePropertyService;
    private final UserEntityService userEntityService;
    private final CommentService commentService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public PropertyController(BasePropertyService basePropertyService, UserEntityService userEntityService, CommentService commentService, CloudinaryService cloudinaryService, ModelMapper modelMapper, Validator validator) {
        this.basePropertyService = basePropertyService;
        this.userEntityService = userEntityService;
        this.commentService = commentService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @ModelAttribute("addPropertyData")
    public AddPropertyDTO addPropertyData() {
        return new AddPropertyDTO();
    }

    @ModelAttribute("addCommentData")
    public AddCommentDTO addCommentData() {
        return new AddCommentDTO();
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

        return viewProperties(OfferType.SALE, propertyType, city, neighbourhood, address, minPrice, maxPrice, pageable, model);
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

        return viewProperties(OfferType.RENT, propertyType, city, neighbourhood, address, minPrice, maxPrice, pageable, model);
    }

    @GetMapping("/add")
    public String viewAddProperty() {
        return "add-property";
    }

    @PostMapping("/add")
    public String addProperty(@Valid AddPropertyDTO addPropertyData,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        //Validations of fields depending on propertyType
        validateWithGroup(addPropertyData, bindingResult);

        //Display messages depending on errors
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addPropertyData", addPropertyData);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addPropertyData", bindingResult);
            return "redirect:/properties/add";
        }

        //add property to DB
        BaseProperty savedProperty = basePropertyService.saveNewPropertyToDB(addPropertyData);

        if (savedProperty == null) {
            redirectAttributes.addFlashAttribute("propertySaved", false);
            return "redirect:/";
        }

        return "redirect:/properties/" + savedProperty.getId();
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

    @PatchMapping("/{id}")
    public String editProperty(@PathVariable UUID id,
                               @Valid EditPropertyDTO editPropertyData,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editPropertyData", editPropertyData);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editPropertyData", bindingResult);

            return "redirect:/properties/" + id + "/edit";
        }

        BaseProperty editedProperty = basePropertyService.editPropertyAndAddToDB(editPropertyData);

        if(editedProperty == null) {
            throw new RuntimeException("Error in saving property.");
        }

        return "redirect:/properties/" + editedProperty.getId();
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

    @GetMapping("/{id}/edit")
    public String editProperty(@PathVariable UUID id, Model model) {
        EditPropertyDTO editPropertyData = getEditPropertyDTOById(id);

        model.addAttribute("editPropertyData", editPropertyData);

        return "edit-property";
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

    @PostMapping("/{id}/add-comment")
    public String addComment(@PathVariable UUID id,
                             @Valid AddCommentDTO addCommentData,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addCommentData", addCommentData);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addCommentData", bindingResult);
            return "redirect:/properties/" + id;
        }

        UserEntity user = userEntityService.getCurrentUser();
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        addCommentData.setUser(user);

        BaseProperty property = basePropertyService.getPropertyByID(id);
        if (property == null) {
            throw new RuntimeException("Property not found");
        }
        addCommentData.setProperty(property);

        commentService.addComment(addCommentData);

        return "redirect:/properties/" + id;
    }

    @DeleteMapping("/{id}/delete-picture/{pictureId}")
    public String deletePicture(@PathVariable UUID id, @PathVariable UUID pictureId) throws IOException {
        basePropertyService.deletePicture(id, pictureId);

        return "redirect:/properties/" + id;
    }

    private String viewProperties(OfferType offerType, PropertyType propertyType, String city, String neighbourhood, String address, Double minPrice, Double maxPrice, Pageable pageable, Model model) {
        Page<PropertyDTO> properties = basePropertyService.getAllPropertiesByCompany(pageable, offerType, propertyType, city, neighbourhood, address, minPrice, maxPrice);
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

    private EditPropertyDTO getEditPropertyDTOById(UUID id) {
        return modelMapper.map(basePropertyService.getPropertyByID(id), EditPropertyDTO.class);
    }

    private boolean isCompanyMatching(PropertyDTO property) {
        String userCompanyName = userEntityService.getCurrentUser().getCompany().getName();

        return userCompanyName.equalsIgnoreCase(property.getOwnerCompanyName());
    }

    private void validateWithGroup(AddPropertyDTO addPropertyData, BindingResult bindingResult) {
        //determine the group by PropertyType
        Class<?> group = determineValidationGroup(addPropertyData.getPropertyType());

        //get the violations
        Set<ConstraintViolation<AddPropertyDTO>> constraintViolations = validator.validate(addPropertyData, group);

        //add the violations to bindingResult
        for (ConstraintViolation<AddPropertyDTO> violation : constraintViolations) {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            bindingResult.addError(new FieldError(bindingResult.getObjectName(), field, message));
        }
    }

    private Class<?> determineValidationGroup(PropertyType propertyType) {
        return switch (propertyType) {
            case PropertyType.APARTMENT -> AddPropertyDTO.ApartmentGroup.class;
            case PropertyType.BUSINESS -> AddPropertyDTO.BusinessPropertyGroup.class;
            case PropertyType.HOUSE -> AddPropertyDTO.HouseGroup.class;
            case PropertyType.GARAGE -> AddPropertyDTO.GarageGroup.class;
            case PropertyType.LAND -> AddPropertyDTO.LandGroup.class;
            default -> throw new RuntimeException("Unknown property type");
        };
    }
}
