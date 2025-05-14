package com.butikimoti.real_estate_planner.model.enums;

public enum ConstructionType {
    BRICK("тухла"),
    PANEL("панел"),
    EPK("ЕПК"),
    JOIST("гредоред"),
    PREFABRICATED("сглобяема конструкция");

    private final String label;

    ConstructionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
