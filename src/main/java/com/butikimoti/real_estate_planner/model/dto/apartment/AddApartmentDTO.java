package com.butikimoti.real_estate_planner.model.dto.apartment;

import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.enums.ApartmentType;
import com.butikimoti.real_estate_planner.model.enums.AreaUnit;
import com.butikimoti.real_estate_planner.model.enums.ConstructionType;
import com.butikimoti.real_estate_planner.model.enums.SaleOrRent;

import java.time.LocalDateTime;

public class AddApartmentDTO {

    private Company ownerCompany;

    private String address;

    private double price;

    private int area;

    private AreaUnit areaUnit;

    private SaleOrRent saleOrRent;

    private String contactName;

    private String contactPhone;

    private String contactEmail;

    private String description;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private ApartmentType apartmentType;

    private int roomCount;

    private ConstructionType constructionType;

    private int year;

    private int floor;

    private int buildingFloors;

    private boolean hasElevator;

    private String facing;

    public AddApartmentDTO() {
    }

    public AddApartmentDTO(Company ownerCompany, String address, double price, int area, AreaUnit areaUnit, SaleOrRent saleOrRent, String contactName, String contactPhone, String contactEmail, String description, ApartmentType apartmentType, int roomCount, ConstructionType constructionType, int year, int floor, int buildingFloors, boolean hasElevator, String facing) {
        this.ownerCompany = ownerCompany;
        this.address = address;
        this.price = price;
        this.area = area;
        this.areaUnit = areaUnit;
        this.saleOrRent = saleOrRent;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.description = description;
        this.apartmentType = apartmentType;
        this.roomCount = roomCount;
        this.constructionType = constructionType;
        this.year = year;
        this.floor = floor;
        this.buildingFloors = buildingFloors;
        this.hasElevator = hasElevator;
        this.facing = facing;
        this.createdOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
    }

    public AddApartmentDTO(Company ownerCompany, String address, double price, int area, AreaUnit areaUnit, SaleOrRent saleOrRent, String contactName, String contactPhone, String contactEmail, String description, LocalDateTime createdOn, LocalDateTime updatedOn, ApartmentType apartmentType, int roomCount, ConstructionType constructionType, int year, int floor, int buildingFloors, boolean hasElevator, String facing) {
        this(ownerCompany, address, price, area, areaUnit, saleOrRent, contactName, contactPhone, contactEmail, description, apartmentType, roomCount, constructionType, year, floor, buildingFloors, hasElevator, facing);
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public Company getOwnerCompany() {
        return ownerCompany;
    }

    public void setOwnerCompany(Company ownerCompany) {
        this.ownerCompany = ownerCompany;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public AreaUnit getAreaUnit() {
        return areaUnit;
    }

    public void setAreaUnit(AreaUnit areaUnit) {
        this.areaUnit = areaUnit;
    }

    public SaleOrRent getSaleOrRent() {
        return saleOrRent;
    }

    public void setSaleOrRent(SaleOrRent saleOrRent) {
        this.saleOrRent = saleOrRent;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
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
