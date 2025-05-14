package com.butikimoti.real_estate_planner.model.enums;

public enum GarageType {
    GARAGE("Гараж"),
    PARKING_SPACE("Паркомясто");

    private final String label;

    GarageType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
