package com.butikimoti.real_estate_planner.model.entity;

import com.butikimoti.real_estate_planner.model.enums.BusinessPropertyType;
import com.butikimoti.real_estate_planner.model.enums.ConstructionType;
import jakarta.persistence.*;

@Entity
@Table(name = "business_properties")
public class BusinessProperty extends BaseProperty {
    @Enumerated(EnumType.STRING)
    @Column(name = "busines_property_type", nullable = false)
    private BusinessPropertyType businessPropertyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "construction_type", nullable = false)
    private ConstructionType constructionType;

    @Column
    private int floor;

    @Column(name = "building_floors", nullable = false)
    private int buildingFloors;

    public BusinessProperty() {
    }

    public BusinessPropertyType getBusinessPropertyType() {
        return businessPropertyType;
    }

    public void setBusinessPropertyType(BusinessPropertyType businessPropertyType) {
        this.businessPropertyType = businessPropertyType;
    }

    public ConstructionType getConstructionType() {
        return constructionType;
    }

    public void setConstructionType(ConstructionType constructionType) {
        this.constructionType = constructionType;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getBuildingFloors() {
        return buildingFloors;
    }

    public void setBuildingFloors(int buildingFloors) {
        this.buildingFloors = buildingFloors;
    }
}
