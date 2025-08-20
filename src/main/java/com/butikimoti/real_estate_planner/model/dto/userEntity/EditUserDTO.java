package com.butikimoti.real_estate_planner.model.dto.userEntity;

import com.butikimoti.real_estate_planner.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public class EditUserDTO {
    private UUID id;

    @NotEmpty(message = "{email.notEmpty}")
    @Email(message = "{email.invalid}")
    private String email;

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

    public EditUserDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotEmpty(message = "{email.notEmpty}") @Email(message = "{email.invalid}") String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "{email.notEmpty}") @Email(message = "{email.invalid}") String email) {
        this.email = email;
    }

    public @NotEmpty(message = "{companyName.notEmpty}") String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(@NotEmpty(message = "{companyName.notEmpty}") String companyName) {
        this.companyName = companyName;
    }

    public @NotEmpty(message = "{firstName.notEmpty}") @Size(min = 2, max = 40, message = "{firstName.length}") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotEmpty(message = "{firstName.notEmpty}") @Size(min = 2, max = 40, message = "{firstName.length}") String firstName) {
        this.firstName = firstName;
    }

    public @NotEmpty(message = "{lastName.notEmpty}") @Size(min = 2, max = 40, message = "{lastName.length}") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotEmpty(message = "{lastName.notEmpty}") @Size(min = 2, max = 40, message = "{lastName.length}") String lastName) {
        this.lastName = lastName;
    }

    public @NotEmpty(message = "{phone.notEmpty}") @Pattern(regexp = "[+]?\\d{6,15}", message = "{phone.pattern}") String getPhone() {
        return phone;
    }

    public void setPhone(@NotEmpty(message = "{phone.notEmpty}") @Pattern(regexp = "[+]?\\d{6,15}", message = "{phone.pattern}") String phone) {
        this.phone = phone;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
