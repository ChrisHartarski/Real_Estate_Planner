package com.butikimoti.real_estate_planner.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "company")
    private List<UserEntity> users = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "ownerCompany")
    private List<BaseProperty> properties = new ArrayList<>();

    public Company() {
    }

    public Company(UUID id, String name, String address, String phone, String email) {
        this.id = id;
        this.users = new ArrayList<>();
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.properties = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
