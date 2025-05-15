package com.butikimoti.real_estate_planner.model.enums;

public enum ApartmentType {
    ONE_ROOM("1-стаен апартамент"),
    TWO_ROOM("2-стаен апартамент"),
    THREE_ROOM("3-стаен апартамент"),
    FOUR_ROOM("4-стаен апартамент"),
    MANY_ROOM("Многостаен апартамент"),
    PENTHOUSE("Мезонет"),
    ATTIC("Ателие/таван");

    private final String label;

    ApartmentType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
