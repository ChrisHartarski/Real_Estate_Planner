package com.butikimoti.real_estate_planner.model.dto.userEntity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public class EditUserPassDTO {
    private UUID id;

    private String currentPassword;

    @NotEmpty(message = "{password.notEmpty}")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*_)(?!.*\\W)(?!.* ).{8,}$", message = "{password.pattern}")
    private String newPassword;

    @NotEmpty(message = "{password.notEmpty}")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*_)(?!.*\\W)(?!.* ).{8,}$", message = "{password.pattern}")
    private String confirmNewPassword;

    public EditUserPassDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public @NotEmpty(message = "{password.notEmpty}") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*_)(?!.*\\W)(?!.* ).{8,}$", message = "{password.pattern}") String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(@NotEmpty(message = "{password.notEmpty}") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*_)(?!.*\\W)(?!.* ).{8,}$", message = "{password.pattern}") String newPassword) {
        this.newPassword = newPassword;
    }

    public @NotEmpty(message = "{password.notEmpty}") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*_)(?!.*\\W)(?!.* ).{8,}$", message = "{password.pattern}") String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(@NotEmpty(message = "{password.notEmpty}") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*_)(?!.*\\W)(?!.* ).{8,}$", message = "{password.pattern}") String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
