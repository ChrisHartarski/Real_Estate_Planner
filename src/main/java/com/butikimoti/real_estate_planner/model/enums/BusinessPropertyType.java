package com.butikimoti.real_estate_planner.model.enums;

public enum BusinessPropertyType {
    OFFICE("Офис"),
    STORE("Магазин"),
    WAREHOUSE("Склад"),
    FACTORY("Помишлено помещение"),
    BUSINESS_PROPERTY("Бизнес имот");

    private final String label;

    BusinessPropertyType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
