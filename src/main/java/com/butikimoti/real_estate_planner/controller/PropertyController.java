package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.property.PropertyDTO;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.service.BasePropertyService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/properties")
public class PropertyController {
    private final BasePropertyService basePropertyService;
    private final UserEntityService userEntityService;

    public PropertyController(BasePropertyService basePropertyService, UserEntityService userEntityService) {
        this.basePropertyService = basePropertyService;
        this.userEntityService = userEntityService;
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

    @GetMapping("/sales/property-page")
    public String getOfferPage() {
        return "property-page";
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

        basePropertyService.savePropertyToDB(addPropertyData);
        return "redirect:/";
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
