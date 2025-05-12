package com.butikimoti.real_estate_planner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class OffersController {

    @GetMapping("/offers")
    public String getOffers() {
        return "offers";
    }

    @GetMapping("/offers/offer-page")
    public String getOfferPage() {
        return "offer-page";
    }
}
