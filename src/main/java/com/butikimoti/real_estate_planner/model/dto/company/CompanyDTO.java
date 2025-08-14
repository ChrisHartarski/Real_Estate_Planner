package com.butikimoti.real_estate_planner.model.dto.company;

import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class CompanyDTO {
    private List<UserEntity> users;

    @NotEmpty(message = "{companyName.notEmpty}")
    @Size(min = 2, max = 40, message = "{companyName.length}")
    private String name;

    @NotEmpty(message = "{company.address.notEmpty}")
    @Size(max = 200, message = "{company.address.length}")
    private String address;

    @NotEmpty(message = "{phone.notEmpty}")
    @Pattern(regexp = "[+]?\\d{6,15}", message = "{phone.pattern}")
    private String phone;

    @NotEmpty(message = "{email.notEmpty}")
    @Email(message = "{email.invalid}")
    private String email;

    private List<BaseProperty> properties;

    public CompanyDTO() {
        this.users = new ArrayList<>();
        this.properties = new ArrayList<>();
    }

    public CompanyDTO(String name, String address, String phone, String email) {
        this();
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<BaseProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<BaseProperty> properties) {
        this.properties = properties;
    }
}
