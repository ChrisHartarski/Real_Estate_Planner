package com.butikimoti.real_estate_planner.model.dto.house;

import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.enums.AreaUnit;
import com.butikimoti.real_estate_planner.model.enums.ConstructionType;
import com.butikimoti.real_estate_planner.model.enums.HouseType;
import com.butikimoti.real_estate_planner.model.enums.SaleOrRent;

import java.time.LocalDateTime;

public class AddHouseDTO {
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

    private HouseType houseType;

    private ConstructionType constructionType;

    private int yardArea;

    private int floors;

    private String additionalStructures;

    public AddHouseDTO() {
    }

    public AddHouseDTO(Company ownerCompany, String address, double price, int area, AreaUnit areaUnit, SaleOrRent saleOrRent, String contactName, String contactPhone, String contactEmail, String description, HouseType houseType, ConstructionType constructionType, int yardArea, int floors, String additionalStructures) {
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
        this.houseType = houseType;
        this.constructionType = constructionType;
        this.yardArea = yardArea;
        this.floors = floors;
        this.additionalStructures = additionalStructures;
        this.createdOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
    }

    public AddHouseDTO(Company ownerCompany, String address, double price, int area, AreaUnit areaUnit, SaleOrRent saleOrRent, String contactName, String contactPhone, String contactEmail, String description, LocalDateTime createdOn, LocalDateTime updatedOn, HouseType houseType, ConstructionType constructionType, int yardArea, int floors, String additionalStructures) {
        this(ownerCompany, address, price, area, areaUnit, saleOrRent, contactName, contactPhone, contactEmail, description, houseType, constructionType, yardArea, floors, additionalStructures);
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
