package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.model.dto.userEntity.RegisterUserDTO;
import com.butikimoti.real_estate_planner.service.CompanyService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserEntityService userEntityService;
    private final CompanyService companyService;

    public UserController(UserEntityService userEntityService, CompanyService companyService, ModelMapper modelMapper) {
        this.userEntityService = userEntityService;
        this.companyService = companyService;
    }

    @ModelAttribute("registerUserData")
    public RegisterUserDTO registerUserData() {
        return new RegisterUserDTO();
    }

    @GetMapping("/register")
    public String register() {
        return "register-user";
    }

    @PostMapping("/register")
    public String register(@Valid RegisterUserDTO registerUserData,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerUserData", registerUserData);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerUserData", bindingResult);
            return "redirect:/users/register";
        }

        if (userEntityService.userExists(registerUserData.getEmail())) {
            redirectAttributes.addFlashAttribute("registerUserData", registerUserData);
            redirectAttributes.addFlashAttribute("userExists", true);
            return "redirect:/users/register";
        }

        if (!registerUserData.getPassword().equals(registerUserData.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("registerUserData", registerUserData);
            redirectAttributes.addFlashAttribute("passwordsDoNotMatch", true);
            return "redirect:/users/register";
        }

        if (!companyService.companyExists(registerUserData.getCompanyName())) {
            redirectAttributes.addFlashAttribute("registerUserData", registerUserData);
            redirectAttributes.addFlashAttribute("companyDoesNotExist", true);
            return "redirect:/users/register";
        }

        userEntityService.register(registerUserData);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String viewLoginError(Model model) {
        model.addAttribute("wrongUsernameOrPassword", true);
        return "login";
    }

}
