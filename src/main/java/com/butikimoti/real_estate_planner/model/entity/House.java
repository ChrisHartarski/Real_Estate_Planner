package com.butikimoti.real_estate_planner.model.entity;

import com.butikimoti.real_estate_planner.model.enums.ConstructionType;
import com.butikimoti.real_estate_planner.model.enums.HouseType;
import jakarta.persistence.*;

@Entity
@Table(name = "houses")
public class House extends BaseProperty {
    @Enumerated(EnumType.STRING)
    @Column(name = "house_type", nullable = false)
    private HouseType houseType;

    @Enumerated(EnumType.STRING)
    @Column(name = "construction_type", nullable = false)
    private ConstructionType constructionType;

    @Column(name = "yard_area", nullable = false)
    private int yardArea;

    @Column(nullable = false)
    private int floorsCount;

    @Column
    private String additionalStructures;

    public House() {
    }

    public HouseType getHouseType() {
        return houseType;
    }

    public void setHouseType(HouseType houseType) {
        this.houseType = houseType;
    }

    public ConstructionType getConstructionType() {
        return constructionType;
    }

    public void setConstructionType(ConstructionType constructionType) {
        this.constructionType = constructionType;
    }

    public int getYardArea() {
        return yardArea;
    }

    public void setYardArea(int yardArea) {
        this.yardArea = yardArea;
    }

    public int getFloorsCount() {
        return floorsCount;
    }

    public void setFloorsCount(int floors) {
        this.floorsCount = floors;
    }

    public String getAdditionalStructures() {
        return additionalStructures;
    }

    public void setAdditionalStructures(String additionalStructures) {
        this.additionalStructures = additionalStructures;
    }
}
