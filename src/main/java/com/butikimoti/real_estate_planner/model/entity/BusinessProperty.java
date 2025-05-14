package com.butikimoti.real_estate_planner.model.entity;

import com.butikimoti.real_estate_planner.model.enums.BusinessPropertyType;
import com.butikimoti.real_estate_planner.model.enums.ConstructionType;
import com.butikimoti.real_estate_planner.model.enums.SaleOrRent;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class BusinessProperty extends BaseProperty {
    @Column(name = "busines_property_type", nullable = false)
    private BusinessPropertyType businessPropertyType;

    @Column(name = "construction_type", nullable = false)
    private ConstructionType constructionType;

    @Column
    private int floor;

    @Column(name = "building_floors", nullable = false)
    private int buildingFloors;

    public BusinessProperty() {
    }

    public BusinessProperty(String id, Company ownerCompany, String address, double price, int area, SaleOrRent saleOrRent, String contactName, String contactPhone, String contactEmail, String description, LocalDateTime createdOn, LocalDateTime updatedOn, BusinessPropertyType businessPropertyType, ConstructionType constructionType, int floor, int buildingFloors) {
        super(id, ownerCompany, address, price, area, saleOrRent, contactName, contactPhone, contactEmail, description, createdOn, updatedOn);
        this.businessPropertyType = businessPropertyType;
        this.constructionType = constructionType;
        this.floor = floor;
        this.buildingFloors = buildingFloors;
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
