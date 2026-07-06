package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.model.dto.city.CityDTO;
import com.butikimoti.real_estate_planner.model.dto.comment.AddCommentDTO;
import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.property.EditPropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.property.HasPropertyType;
import com.butikimoti.real_estate_planner.model.dto.property.PropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.util.CloudinaryImageInfoDTO;
import com.butikimoti.real_estate_planner.model.dto.util.PropertiesPageContext;
import com.butikimoti.real_estate_planner.model.dto.util.filter.PropertyFilter;
import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.entity.PropertyPicture;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.model.enums.PropertyType;
import com.butikimoti.real_estate_planner.service.BasePropertyService;
import com.butikimoti.real_estate_planner.service.CityService;
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
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/properties")
@SessionAttributes({
        "propertyFilter",
        "propertiesPageContext"
})
public class PropertyController {
    private final BasePropertyService basePropertyService;
    private final UserEntityService userEntityService;
    private final CommentService commentService;
    private final CloudinaryService cloudinaryService;
    private final CityService cityService;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public PropertyController(BasePropertyService basePropertyService, UserEntityService userEntityService, CommentService commentService, CloudinaryService cloudinaryService, CityService cityService, ModelMapper modelMapper, Validator validator) {
        this.basePropertyService = basePropertyService;
        this.userEntityService = userEntityService;
        this.commentService = commentService;
        this.cloudinaryService = cloudinaryService;
        this.cityService = cityService;
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

    @ModelAttribute("propertyFilter")
    public PropertyFilter propertyFilter() {
        return new PropertyFilter();
    }

    @ModelAttribute("propertiesPageContext")
    public PropertiesPageContext propertiesPageContext() {
        return new PropertiesPageContext();
    }

    @GetMapping("/sales")
    public String viewSales(
            @ModelAttribute ("propertyFilter") PropertyFilter filter,
            @ModelAttribute ("propertiesPageContext") PropertiesPageContext context,
            @PageableDefault(size = 10, sort = "updatedOn", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {

        if (isContextChanged(context, OfferType.SALE, false)) {
            filter.clear();
        }

        context.setOfferType(OfferType.SALE);
        context.setArchived(false);

        model.addAttribute("pageType", "sales");

        return viewProperties(context, filter, pageable, model);
    }

    @GetMapping("/rents")
    public String viewRents(
            @ModelAttribute ("propertyFilter") PropertyFilter filter,
            @ModelAttribute ("propertiesPageContext") PropertiesPageContext context,
            @PageableDefault(size = 10, sort = "updatedOn", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {

        if (isContextChanged(context, OfferType.RENT, false)) {
            filter.clear();
        }

        context.setOfferType(OfferType.RENT);
        context.setArchived(false);

        model.addAttribute("pageType", "rents");

        return viewProperties(context, filter, pageable, model);
    }

    @GetMapping("/archivedSales")
    public String viewArchivedSales(
            @ModelAttribute ("propertyFilter") PropertyFilter filter,
            @ModelAttribute ("propertiesPageContext") PropertiesPageContext context,
            @PageableDefault(size = 10, sort = "updatedOn", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {

        if (isContextChanged(context, OfferType.SALE, true)) {
            filter.clear();
        }

        context.setOfferType(OfferType.SALE);
        context.setArchived(true);

        model.addAttribute("pageType", "archivedSales");

        return viewProperties(context, filter, pageable, model);
    }

    @GetMapping("/archivedRents")
    public String viewArchivedRents(
            @ModelAttribute ("propertyFilter") PropertyFilter filter,
            @ModelAttribute ("propertiesPageContext") PropertiesPageContext context,
            @PageableDefault(size = 10, sort = "updatedOn", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {

        if (isContextChanged(context, OfferType.RENT, true)) {
            filter.clear();
        }

        context.setOfferType(OfferType.RENT);
        context.setArchived(true);

        model.addAttribute("pageType", "archivedRents");

        return viewProperties(context, filter, pageable, model);
    }

    @GetMapping("/add")
    public String viewAddProperty(Model model) {
        List<CityDTO> cityList = cityService.getAllCities();
        model.addAttribute("cities", cityList);
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
//        return "redirect:/properties/";
    }

    @GetMapping("/{id}")
    public String getPropertyInfoPage(@PathVariable UUID id, Model model) {
        PropertyDTO propertyData = getPropertyDTOById(id);

        if (!isCompanyMatching(propertyData)) {
            return "error-unauthorized";
        }

        model.addAttribute("property", propertyData);
        return "property-page";
    }

    @PatchMapping("/{id}")
    public String editProperty(@PathVariable UUID id,
                               @Valid EditPropertyDTO editPropertyData,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        validateWithGroup(editPropertyData, bindingResult);

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

    @PatchMapping("/{id}/archive")
    public String archiveProperty(@PathVariable UUID id) {

        basePropertyService.archiveProperty(id);

        return "redirect:/properties/" + id;
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

        EditPropertyDTO editPropertyData = (EditPropertyDTO) model.getAttribute("editPropertyData");

        if (editPropertyData == null) {
            editPropertyData = getEditPropertyDTOById(id);
            model.addAttribute("editPropertyData", editPropertyData);
        }

        List<CityDTO> cityList = cityService.getAllCities();
        model.addAttribute("cities", cityList);

        return "edit-property";
    }

    @PostMapping("/{id}/upload-picture")
    public String uploadPicture(@PathVariable UUID id,
                                @RequestParam("images") List<MultipartFile> images) {
        BaseProperty property = basePropertyService.getPropertyByID(id);
        List<CloudinaryImageInfoDTO> imageList = cloudinaryService.uploadImages(images);
        imageList.forEach(image -> {
            String imageUrl = image.getImageUrl();
            String imagePublicId = image.getPublicId();

            PropertyPicture picture = new PropertyPicture(imageUrl, property, imagePublicId);
            property.getPictures().add(picture);
        });

        basePropertyService.savePropertyToDB(property);

        return "redirect:/properties/" + id;
    }

    @DeleteMapping("/{id}/delete-picture/{pictureId}")
    public String deletePicture(@PathVariable UUID id, @PathVariable UUID pictureId) throws IOException {
        basePropertyService.deletePicture(id, pictureId);

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
        addCommentData.setUserFirstName(user.getFirstName());
        addCommentData.setUserLastName(user.getLastName());

        BaseProperty property = basePropertyService.getPropertyByID(id);
        if (property == null) {
            throw new RuntimeException("Property not found");
        }
        addCommentData.setProperty(property);

        commentService.addComment(addCommentData);

        return "redirect:/properties/" + id;
    }

    private String viewProperties(PropertiesPageContext context, PropertyFilter propertyFilter, Pageable pageable, Model model) {
        Page<PropertyDTO> properties = basePropertyService.getAllPropertiesByCompany(pageable, context.getOfferType(), context.isArchived(), propertyFilter);

        model.addAttribute("properties", properties);
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("propertyFilter", propertyFilter);
        model.addAttribute("context", context);

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

    private <T extends HasPropertyType> void validateWithGroup (T dto, BindingResult bindingResult) {
        //determine the group by PropertyType
        Class<?> group = determineValidationGroup(dto.getPropertyType());

        //get the violations
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(dto, group);

        //add the violations to bindingResult
        for (ConstraintViolation<T> violation : constraintViolations) {
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

    private boolean isContextChanged(PropertiesPageContext ctx,
                                     OfferType newType,
                                     boolean newArchived) {

        return ctx.getOfferType() != newType
                || ctx.isArchived() != newArchived;
    }
}
