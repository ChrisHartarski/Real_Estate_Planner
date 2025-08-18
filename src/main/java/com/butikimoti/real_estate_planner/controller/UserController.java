package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.model.dto.userEntity.UserDTO;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.enums.UserRole;
import com.butikimoti.real_estate_planner.service.CompanyService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserEntityService userEntityService;
    private final CompanyService companyService;

    public UserController(UserEntityService userEntityService, CompanyService companyService) {
        this.userEntityService = userEntityService;
        this.companyService = companyService;
    }

    @ModelAttribute("registerUserData")
    public UserDTO registerUserData() {
        return new UserDTO();
    }

    @GetMapping("")
    public String getUsers(@RequestParam(value = "userFirstLastNameParam",required = false) String userFirstLastName,
                            @RequestParam(value = "userEmailParam", required = false) String userEmail,
                            @RequestParam(value = "userCompanyParam", required = false) String userCompanyName,
                            @RequestParam(value = "userRoleParam", required = false) UserRole userRole,
                            @PageableDefault(size = 10, sort = "registeredOn", direction = Sort.Direction.DESC) Pageable pageable,
                            Model model) {
        Page<UserDTO> users = userEntityService.getAllUsers(pageable, userFirstLastName, userEmail, userCompanyName, userRole);
        model.addAttribute("users", users);
        model.addAttribute("userFirstLastNameParam", userFirstLastName);
        model.addAttribute("userEmailParam", userEmail);
        model.addAttribute("userCompanyParam", userCompanyName);
        model.addAttribute("userRoleParam", userRole);

        return "users";
    }

    @GetMapping("/register")
    public String register() {
        return "register-user";
    }

    @PostMapping("/register")
    public String register(@Valid UserDTO registerUserData,
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

        Company company = companyService.getCompany(registerUserData.getCompanyName());
        userEntityService.registerUser(registerUserData, company);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login-user";
    }

    @GetMapping("/login-error")
    public String viewLoginError(Model model) {
        model.addAttribute("wrongUsernameOrPassword", true);
        return "login-user";
    }
}
