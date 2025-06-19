package com.butikimoti.real_estate_planner.model.entity;

import com.butikimoti.real_estate_planner.model.enums.BusinessPropertyType;
import com.butikimoti.real_estate_planner.model.enums.ConstructionType;
import com.butikimoti.real_estate_planner.model.enums.HeatingType;
import jakarta.persistence.*;

@Entity
@Table(name = "business_properties")
public class BusinessProperty extends BaseProperty {
    @Enumerated(EnumType.STRING)
    @Column(name = "business_property_type", nullable = false)
    private BusinessPropertyType businessPropertyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "construction_type", nullable = false)
    private ConstructionType constructionType;

    @Column
    private int year;

    @Column(name = "room_count")
    private int roomCount;

    @Column
    private int floor;

    @Column(name = "building_floors", nullable = false)
    private int buildingFloors;

    @Enumerated(EnumType.STRING)
    @Column(name = "heating_type", nullable = false)
    private HeatingType heatingType;

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
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

    public HeatingType getHeatingType() {
        return heatingType;
    }

    public void setHeatingType(HeatingType heatingType) {
        this.heatingType = heatingType;
    }
}
