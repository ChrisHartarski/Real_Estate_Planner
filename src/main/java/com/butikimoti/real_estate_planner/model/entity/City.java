package com.butikimoti.real_estate_planner.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name;

    @OneToMany (mappedBy = "city", cascade = CascadeType.REMOVE)
    private List<Neighbourhood> neighbourhoods;

    public City() {
        neighbourhoods = new ArrayList<Neighbourhood>();
    }

    public City(String name) {
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

    public List<Neighbourhood> getNeighbourhoods() {
        return neighbourhoods;
    }

    public void setNeighbourhoods(List<Neighbourhood> neighbourhoods) {
        this.neighbourhoods = neighbourhoods;
    }

    @Override
    public String toString() {
        return getName();
    }
}
