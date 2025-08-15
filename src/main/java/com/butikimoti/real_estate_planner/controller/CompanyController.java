package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.model.dto.company.CompanyDTO;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.service.CompanyService;
import jakarta.validation.Valid;
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

    @GetMapping("")
    public String getCompanies(
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "companyEmail", required = false) String companyEmail,
            @PageableDefault(size = 10, sort = "registeredOn", direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ) {
        return viewCompanies(companyName, companyEmail, pageable, model);
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

    @GetMapping("/{id}")
    public String getCompanyPage(@PathVariable UUID id, Model model) {
        CompanyDTO companyDTO = companyService.getCompanyDTO(id);
        model.addAttribute("company", companyDTO);

        return "company-page";
    }

    @DeleteMapping("/{id}")
    public String deleteCompany(@PathVariable UUID id) throws IOException {
        companyService.deleteCompany(id);

        return "redirect:/";
    }

    @PatchMapping("/{id}")
    public String editCompany(@PathVariable UUID id,
                              @Valid CompanyDTO editCompanyData,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("company", editCompanyData);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editCompanyData", bindingResult);

            return "redirect:/companies/" + id + "/edit";
        }

        Company editedCompany = companyService.editAndSaveCompanyToDB(editCompanyData);
        if (editedCompany == null) {
            throw new RuntimeException("Error in saving property.");
        }

        return "redirect:/companies/" + editedCompany.getId();
    }

    @GetMapping("/{id}/edit")
    public String editCompany(@PathVariable UUID id, Model model) {
        CompanyDTO companyDTO = companyService.getCompanyDTO(id);

        model.addAttribute("editCompanyData", companyDTO);

        return "edit-company";
    }

    @PostMapping("/{id}/upload-logo")
    public String uploadLogo(@PathVariable UUID id,
                             @RequestParam("image") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            companyService.addLogo(id, file);
        }

        return "redirect:/companies/" + id;
    }

    @PutMapping("/{id}/replace-logo")
    public String replaceLogo(@PathVariable UUID id,
                              @RequestParam("image") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            companyService.replaceLogo(id, file);
        }

        return "redirect:/companies/" + id;
    }

    private String viewCompanies(String name, String email, Pageable pageable, Model model) {
        Page<CompanyDTO> companies = companyService.getAllCompanies(pageable, name, email);
        model.addAttribute("companies", companies);
        model.addAttribute("companyNameParam", name);
        model.addAttribute("companyEmailParam", email);

        return "companies";
    }
}
