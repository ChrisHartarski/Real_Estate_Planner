package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.model.dto.company.CompanyDTO;
import com.butikimoti.real_estate_planner.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @ModelAttribute("companyData")
    public CompanyDTO companyData() {
        return new CompanyDTO();
    }


    @GetMapping("/register")
    public String register() {
        return "register-company";
    }

    @PostMapping("/register")
    public String register(@Valid CompanyDTO companyData,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("companyData", companyData);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.companyData", bindingResult);
            return "redirect:/companies/register";
        }

        if (companyService.companyExists(companyData.getName())) {
            redirectAttributes.addFlashAttribute("companyData", companyData);
            redirectAttributes.addFlashAttribute("companyExists", true);
            return "redirect:/companies/register";
        }

        companyService.registerCompany(companyData);
        return "redirect:/";
    }
}
