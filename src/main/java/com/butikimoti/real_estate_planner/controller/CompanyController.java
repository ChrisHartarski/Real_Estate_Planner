package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/register")
    public String register() {
        return "register-company";
    }

    @PostMapping("/register")
    public String register(Company company) {
        //TODO:
        return "index";
    }
}
