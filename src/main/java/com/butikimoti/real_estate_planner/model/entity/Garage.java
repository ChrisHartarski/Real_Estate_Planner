package com.butikimoti.real_estate_planner.model.entity;

import com.butikimoti.real_estate_planner.model.enums.GarageType;
import com.butikimoti.real_estate_planner.model.enums.SaleOrRent;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "garages")
public class Garage extends BaseProperty {
    @Column(name = "garage_type", nullable = false)
    private GarageType garageType;

    @Column(nullable = false)
    private int floor;

    public Garage() {
    }

    public Garage(String id, Company ownerCompany, String address, double price, int area, SaleOrRent saleOrRent, String contactName, String contactPhone, String contactEmail, String description, LocalDateTime createdOn, LocalDateTime updatedOn, GarageType garageType, int floor) {
        super(id, ownerCompany, address, price, area, saleOrRent, contactName, contactPhone, contactEmail, description, createdOn, updatedOn);
        this.garageType = garageType;
        this.floor = floor;
    }

    public GarageType getGarageType() {
        return garageType;
    }

    public void setGarageType(GarageType garageType) {
        this.garageType = garageType;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
