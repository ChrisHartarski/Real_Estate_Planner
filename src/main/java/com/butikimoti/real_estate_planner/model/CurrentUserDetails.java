package com.butikimoti.real_estate_planner.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class CurrentUserDetails extends User {
    private final UUID id;
    private final String companyName;
    private final String firstName;
    private final String lastName;
    private final String prone;


    public CurrentUserDetails(String email, String password, Collection<? extends GrantedAuthority> authorities, UUID id, String companyName, String firstName, String lastName, String prone) {
        super(email, password, authorities);
        this.id = id;
        this.companyName = companyName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.prone = prone;
    }

    public UUID getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProne() {
        return prone;
    }
}
