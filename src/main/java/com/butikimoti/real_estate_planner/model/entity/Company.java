package com.butikimoti.real_estate_planner.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<UserEntity> users;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "ownerCompany")
    private List<BaseProperty> properties;

    @Column(name = "registered_on", nullable = false)
    private LocalDateTime registeredOn;

    public Company() {
        this.users = new ArrayList<>();
        this.properties = new ArrayList<>();
    }

    public Company(String name, String address, String phone, String email) {
        this();
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public Company(UUID id, String name, String address, String phone, String email) {
        this(name, address, phone, email);
        this.id = id;
    }

    public Company(List<UserEntity> users, String name, String address, String phone, String email, List<BaseProperty> properties) {
        this(name, address, phone, email);
        this.users = users;
        this.properties = properties;
    }

    public Company(UUID id, List<UserEntity> users, String name, String address, String phone, String email, List<BaseProperty> properties) {
        this(users, name, address, phone, email, properties);
        this.id = id;
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

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }
}
