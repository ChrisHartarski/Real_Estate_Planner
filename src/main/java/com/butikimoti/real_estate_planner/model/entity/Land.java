package com.butikimoti.real_estate_planner.model.entity;

import com.butikimoti.real_estate_planner.model.enums.LandType;
import com.butikimoti.real_estate_planner.model.enums.SaleOrRent;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "land_properties")
public class Land extends BaseProperty {
    @Enumerated(EnumType.STRING)
    @Column(name = "land_type", nullable = false)
    private LandType landType;

    public Land() {
    }

    public Land(String id, Company ownerCompany, String address, double price, int area, SaleOrRent saleOrRent, String contactName, String contactPhone, String contactEmail, String description, LocalDateTime createdOn, LocalDateTime updatedOn, LandType landType) {
        super(id, ownerCompany, address, price, area, saleOrRent, contactName, contactPhone, contactEmail, description, createdOn, updatedOn);
        this.landType = landType;
    }

    public LandType getLandType() {
        return landType;
    }

    public void setLandType(LandType landType) {
        this.landType = landType;
    }
}
