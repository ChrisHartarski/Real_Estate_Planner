package com.butikimoti.real_estate_planner.model.dto.neighbourhood;

import java.util.UUID;

public class NeighbourhoodDTO {
    private UUID id;
    private String name;
    private UUID cityId;

    public NeighbourhoodDTO() {
    }

    public NeighbourhoodDTO(UUID id, String name, UUID cityId) {
        this.id = id;
        this.name = name;
        this.cityId = cityId;
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

    public UUID getCityId() {
        return cityId;
    }

    public void setCityId(UUID cityId) {
        this.cityId = cityId;
    }
}
