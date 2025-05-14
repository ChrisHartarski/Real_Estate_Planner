package com.butikimoti.real_estate_planner.model.entity;

import com.butikimoti.real_estate_planner.model.enums.ConstructionType;
import com.butikimoti.real_estate_planner.model.enums.HouseType;
import com.butikimoti.real_estate_planner.model.enums.SaleOrRent;
import jakarta.persistence.*;

import java.time.LocalDateTime;

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
    private int floors;

    @Column
    private String additionalStructures;

    public House() {
    }

    public House(String id, Company ownerCompany, String address, double price, int area, SaleOrRent saleOrRent, String contactName, String contactPhone, String contactEmail, String description, LocalDateTime createdOn, LocalDateTime updatedOn, HouseType houseType, ConstructionType constructionType, int yardArea, int floors, String additionalStructures) {
        super(id, ownerCompany, address, price, area, saleOrRent, contactName, contactPhone, contactEmail, description, createdOn, updatedOn);
        this.houseType = houseType;
        this.constructionType = constructionType;
        this.yardArea = yardArea;
        this.floors = floors;
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

    public int getYardArea() {
        return yardArea;
    }

    public void setYardArea(int yardArea) {
        this.yardArea = yardArea;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public String getAdditionalStructures() {
        return additionalStructures;
    }

    public void setAdditionalStructures(String additionalStructures) {
        this.additionalStructures = additionalStructures;
    }
}
