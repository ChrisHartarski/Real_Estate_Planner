package com.butikimoti.real_estate_planner.model.dto.city;

import java.util.UUID;

public class CityDTO {
    private UUID id;
    private String name;

    public CityDTO() {
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
}
