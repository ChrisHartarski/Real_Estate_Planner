package com.butikimoti.real_estate_planner.model.entity;

import com.butikimoti.real_estate_planner.model.enums.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "houses")
public class House extends BaseProperty {
    @Enumerated(EnumType.STRING)
    @Column(name = "house_type", nullable = false)
    private HouseType houseType;

    @Enumerated(EnumType.STRING)
    @Column(name = "construction_type", nullable = false)
    private ConstructionType constructionType;

    @Column
    private int year;

    @Column(name = "yard_area", nullable = false)
    private int yardArea;

    @Enumerated(EnumType.STRING)
    @Column(name = "yard_area_unit", nullable = false)
    private AreaUnit yardAreaUnit;

    @Column(nullable = false)
    private int floorsCount;

    @Column
    private String additionalStructures;

    public House() {
    }

    public House(UUID id, PropertyType propertyType, Company ownerCompany, String address, double price, int area, AreaUnit areaUnit, OfferType offerType, String contactName, String contactPhone, String contactEmail, String description, LocalDateTime createdOn, LocalDateTime updatedOn, HouseType houseType, ConstructionType constructionType, int year, int yardArea, AreaUnit yardAreaUnit, int floorsCount, String additionalStructures) {
        super(id, propertyType, ownerCompany, address, price, area, areaUnit, offerType, contactName, contactPhone, contactEmail, description, createdOn, updatedOn);
        this.houseType = houseType;
        this.constructionType = constructionType;
        this.year = year;
        this.yardArea = yardArea;
        this.yardAreaUnit = yardAreaUnit;
        this.floorsCount = floorsCount;
        this.additionalStructures = additionalStructures;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYardArea() {
        return yardArea;
    }

    public void setYardArea(int yardArea) {
        this.yardArea = yardArea;
    }

    public AreaUnit getYardAreaUnit() {
        return yardAreaUnit;
    }

    public void setYardAreaUnit(AreaUnit yardAreaUnit) {
        this.yardAreaUnit = yardAreaUnit;
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
