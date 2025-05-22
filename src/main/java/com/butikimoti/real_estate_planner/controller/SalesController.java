package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.model.dto.property.PropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.service.BasePropertyService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller

public class SalesController {
    private final BasePropertyService basePropertyService;

    public SalesController(BasePropertyService basePropertyService) {
        this.basePropertyService = basePropertyService;
    }


    @GetMapping("/sales")
    public String viewSales(@PageableDefault(size = 10, sort = "updatedOn", direction = Sort.Direction.DESC) Pageable pageable,
                            Model model) {
        PagedModel<PropertyDTO> properties = basePropertyService.getAllPropertiesByCompany(pageable);
        model.addAttribute("properties", properties);
        return "sales";
    }

    @GetMapping("/sales/property-page")
    public String getOfferPage() {
        return "property-page";
    }
}
