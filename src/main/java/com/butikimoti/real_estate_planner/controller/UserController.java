package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.model.dto.userEntity.RegisterUserDTO;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.service.CompanyService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserEntityService userEntityService;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    public UserController(UserEntityService userEntityService, CompanyService companyService, ModelMapper modelMapper) {
        this.userEntityService = userEntityService;
        this.companyService = companyService;
        this.modelMapper = modelMapper;
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
    public String register(UserEntity userEntity) {
        //TODO:

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
