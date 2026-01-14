package com.butikimoti.real_estate_planner.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "neighbourhoods")
public class Neighbourhood {
    @Id
    private UUID id;

    @Column
    private String name;

    @ManyToOne
    private City city;

    public Neighbourhood() {
    }

    public Neighbourhood(String name) {
        this();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return getName();
    }
}
