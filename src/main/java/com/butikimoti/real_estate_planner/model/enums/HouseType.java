package com.butikimoti.real_estate_planner.model.enums;

public enum HouseType {
    HOUSE("Къща"),
    HOUSE_FLOOR("Етаж от къща"),
    VILLA("вила");

    private final String label;

    HouseType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
