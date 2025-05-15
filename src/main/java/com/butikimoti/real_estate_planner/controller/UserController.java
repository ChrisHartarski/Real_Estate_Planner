package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserEntityService userEntityService;

    public UserController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @GetMapping("/register")
    public String register() {
        return "register-user";
    }

    @PostMapping("/register")
    public String register(UserEntity userEntity) {
        //TODO:
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
