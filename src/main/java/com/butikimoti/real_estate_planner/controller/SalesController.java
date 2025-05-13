package com.butikimoti.real_estate_planner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class SalesController {

    @GetMapping("/sales")
    public String getOffers() {
        return "sales";
    }

    @GetMapping("/sales/property-page")
    public String getOfferPage() {
        return "property-page";
    }
}
