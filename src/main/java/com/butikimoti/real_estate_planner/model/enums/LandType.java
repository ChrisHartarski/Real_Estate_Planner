package com.butikimoti.real_estate_planner.model.enums;

public enum LandType {
    PLOT("Парцел"),
    ARABLE_LAND("Земеделска земя");

    private final String label;

    LandType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
