package com.butikimoti.real_estate_planner.model.entity;

import com.butikimoti.real_estate_planner.model.enums.AreaUnit;
import com.butikimoti.real_estate_planner.model.enums.LandType;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.model.enums.PropertyType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "land_properties")
public class Land extends BaseProperty {
    @Enumerated(EnumType.STRING)
    @Column(name = "land_type", nullable = false)
    private LandType landType;

    public Land() {
    }

    public Land(UUID id, PropertyType propertyType, Company ownerCompany, String city, String neighbourhood, String address, double price, int area, AreaUnit areaUnit, OfferType offerType, String contactName, String contactPhone, String contactEmail, String description, LocalDateTime createdOn, LocalDateTime updatedOn, LandType landType) {
        super(id, propertyType, ownerCompany, city, neighbourhood, address, price, area, areaUnit, offerType, contactName, contactPhone, contactEmail, description, createdOn, updatedOn);
        this.landType = landType;
    }

    public LandType getLandType() {
        return landType;
    }

    public void setLandType(LandType landType) {
        this.landType = landType;
    }
}
