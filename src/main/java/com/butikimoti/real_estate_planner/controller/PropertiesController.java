package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.model.dto.property.PropertyDTO;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.service.BasePropertyService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class PropertiesController {
    private final BasePropertyService basePropertyService;

    public PropertiesController(BasePropertyService basePropertyService) {
        this.basePropertyService = basePropertyService;
    }

    @GetMapping("/properties")
    public String viewSales(@RequestParam(value = "offerType") String offerType,
                            @PageableDefault(size = 10, sort = "updatedOn", direction = Sort.Direction.DESC) Pageable pageable,
                            Model model) {
        OfferType saleOrRent = mapOfferType(offerType);
        PagedModel<PropertyDTO> properties = basePropertyService.getAllPropertiesByCompany(pageable, saleOrRent);
        model.addAttribute("properties", properties);
        return "properties";
    }

    @GetMapping("/sales/property-page")
    public String getOfferPage() {
        return "property-page";
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
