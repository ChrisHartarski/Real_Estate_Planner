package com.butikimoti.real_estate_planner.model.entity;

import com.butikimoti.real_estate_planner.model.enums.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "apartments")
public class Apartment extends BaseProperty {
    @Enumerated(EnumType.STRING)
    @Column(name = "apartment_type", nullable = false)
    private ApartmentType apartmentType;

    @Column(name = "room_count", nullable = false)
    private int roomCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "construction_type", nullable = false)
    private ConstructionType constructionType;

    @Column
    private int year;

    @Column(nullable = false)
    private int floor;

    @Column(name = "building_floors", nullable = false)
    private int buildingFloors;

    @Column(name = "has_elevator", nullable = false)
    private boolean hasElevator;

    @Column
    private String facing;

    public Apartment() {
    }

    public Apartment(UUID id, PropertyType propertyType, Company ownerCompany, String address, double price, int area, AreaUnit areaUnit, OfferType offerType, String contactName, String contactPhone, String contactEmail, String description, LocalDateTime createdOn, LocalDateTime updatedOn, ApartmentType apartmentType, int roomCount, ConstructionType constructionType, int year, int floor, int buildingFloors, boolean hasElevator, String facing) {
        super(id, propertyType, ownerCompany, address, price, area, areaUnit, offerType, contactName, contactPhone, contactEmail, description, createdOn, updatedOn);
        this.apartmentType = apartmentType;
        this.roomCount = roomCount;
        this.constructionType = constructionType;
        this.year = year;
        this.floor = floor;
        this.buildingFloors = buildingFloors;
        this.hasElevator = hasElevator;
        this.facing = facing;
    }

    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(ApartmentType apartmentType) {
        this.apartmentType = apartmentType;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
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

    public boolean isHasElevator() {
        return hasElevator;
    }

    public void setHasElevator(boolean hasElevator) {
        this.hasElevator = hasElevator;
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }
}
