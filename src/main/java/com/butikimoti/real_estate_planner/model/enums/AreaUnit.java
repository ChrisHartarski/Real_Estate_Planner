package com.butikimoti.real_estate_planner.model.enums;

public enum AreaUnit {
    SQUARE_METER("кв. м"),
    DECARE("дка");

    private final String label;

    AreaUnit(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
