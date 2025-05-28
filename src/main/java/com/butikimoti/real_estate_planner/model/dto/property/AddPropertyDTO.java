package com.butikimoti.real_estate_planner.model.dto.property;

import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.enums.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class AddPropertyDTO {

    @NotNull(message = "{propertyType.notEmpty}")
    private PropertyType propertyType;

    private Company ownerCompany;

    @NotEmpty(message = "{propertyAddress.notEmpty}")
    private String address;

    @NotNull(message = "{propertyPrice.notEmpty}")
    @Positive(message = "{propertyPrice.positive}")
    private Double price;

    @NotNull(message = "{propertyArea.notEmpty}")
    @Positive(message = "{propertyArea.positive}")
    private Integer area;

    @NotNull(message = "{propertyArea.unitNotEmpty}")
    private AreaUnit areaUnit;

    @NotNull(message = "{offerType.notEmpty}")
    private OfferType offerType;

    @NotEmpty(message = "{contactName.notEmpty}")
    @Size(min = 1, max = 80, message = "{contactName.length}")
    private String contactName;

    @NotEmpty(message = "{phone.notEmpty}")
    @Pattern(regexp = "[+]?\\d{6,15}", message = "{phone.pattern}")
    private String contactPhone;

    @NotEmpty(message = "{email.notEmpty}")
    @Email(message = "{email.invalid}")
    private String contactEmail;

    @Size(max = 1000, message = "{description.length}")
    private String description;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    //common
    private ConstructionType constructionType;

    private Integer year;

    private Integer roomCount;

    private Integer floor;

    private Integer buildingFloors;

    private String facing;

    //apartment
    private ApartmentType apartmentType;

    private boolean hasElevator;

    //house
    private HouseType houseType;

    private Integer yardArea;

    private AreaUnit yardAreaUnit;

    private Integer floorsCount;

    private String additionalStructures;

    //garage
    private GarageType garageType;

    //land
    private LandType landType;

    //business property
    private BusinessPropertyType businessPropertyType;


    public AddPropertyDTO() {
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public AreaUnit getAreaUnit() {
        return areaUnit;
    }

    public void setAreaUnit(AreaUnit areaUnit) {
        this.areaUnit = areaUnit;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
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

    public ConstructionType getConstructionType() {
        return constructionType;
    }

    public void setConstructionType(ConstructionType constructionType) {
        this.constructionType = constructionType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getBuildingFloors() {
        return buildingFloors;
    }

    public void setBuildingFloors(Integer buildingFloors) {
        this.buildingFloors = buildingFloors;
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(ApartmentType apartmentType) {
        this.apartmentType = apartmentType;
    }

    public boolean isHasElevator() {
        return hasElevator;
    }

    public void setHasElevator(boolean hasElevator) {
        this.hasElevator = hasElevator;
    }

    public HouseType getHouseType() {
        return houseType;
    }

    public void setHouseType(HouseType houseType) {
        this.houseType = houseType;
    }

    public Integer getYardArea() {
        return yardArea;
    }

    public void setYardArea(Integer yardArea) {
        this.yardArea = yardArea;
    }

    public AreaUnit getYardAreaUnit() {
        return yardAreaUnit;
    }

    public void setYardAreaUnit(AreaUnit yardAreaUnit) {
        this.yardAreaUnit = yardAreaUnit;
    }

    public Integer getFloorsCount() {
        return floorsCount;
    }

    public void setFloorsCount(Integer floorsCount) {
        this.floorsCount = floorsCount;
    }

    public String getAdditionalStructures() {
        return additionalStructures;
    }

    public void setAdditionalStructures(String additionalStructures) {
        this.additionalStructures = additionalStructures;
    }

    public GarageType getGarageType() {
        return garageType;
    }

    public void setGarageType(GarageType garageType) {
        this.garageType = garageType;
    }

    public LandType getLandType() {
        return landType;
    }

    public void setLandType(LandType landType) {
        this.landType = landType;
    }

    public BusinessPropertyType getBusinessPropertyType() {
        return businessPropertyType;
    }

    public void setBusinessPropertyType(BusinessPropertyType businessPropertyType) {
        this.businessPropertyType = businessPropertyType;
    }
}
