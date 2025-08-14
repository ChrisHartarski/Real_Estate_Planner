package com.butikimoti.real_estate_planner.model.dto.userEntity;

import com.butikimoti.real_estate_planner.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotEmpty(message = "{email.notEmpty}")
    @Email(message = "{email.invalid}")
    private String email;

    @NotEmpty(message = "{password.notEmpty}")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*_)(?!.*\\W)(?!.* ).{8,}$", message = "{password.pattern}")
    private String password;

    @NotEmpty(message = "{password.notEmpty}")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*_)(?!.*\\W)(?!.* ).{8,}$", message = "{password.pattern}")
    private String confirmPassword;

    @NotEmpty(message = "{companyName.notEmpty}")
    private String companyName;

    @NotEmpty(message = "{firstName.notEmpty}")
    @Size(min = 2, max = 40, message = "{firstName.length}")
    private String firstName;

    @NotEmpty(message = "{lastName.notEmpty}")
    @Size(min = 2, max = 40, message = "{lastName.length}")
    private String lastName;

    @NotEmpty(message = "{phone.notEmpty}")
    @Pattern(regexp = "[+]?\\d{6,15}", message = "{phone.pattern}")
    private String phone;

    private UserRole userRole;

    public UserDTO() {
        this.userRole = UserRole.USER;
    }

    public UserDTO(String email, String password, String confirmPassword, String companyName, String firstName, String lastName, String phone) {
        this();
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.companyName = companyName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public UserDTO(String email, String password, String confirmPassword, String companyName, String firstName, String lastName, String phone, UserRole userRole) {
        this(email, password, confirmPassword, companyName, firstName, lastName, phone);
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
