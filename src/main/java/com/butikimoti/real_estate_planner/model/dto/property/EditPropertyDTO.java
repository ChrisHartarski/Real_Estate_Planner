package com.butikimoti.real_estate_planner.model.dto.property;

import com.butikimoti.real_estate_planner.model.enums.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class EditPropertyDTO implements HasPropertyType {
    private UUID id;

    private PropertyType propertyType;

    private String city;

    private String neighbourhood;

    private String address;

    private double price;

    private int area;

    private AreaUnit areaUnit;

    private OfferType offerType;

    private String contactName;

    private String contactPhone;

    private String contactEmail;

    private String description;

    private LocalDateTime updatedOn;

    //common
    private ConstructionType constructionType;

    private int year;

    private int roomCount;

    private int floor;

    private int buildingFloors;

    private String facing;

    private HeatingType heatingType;

    //apartment
    private ApartmentType apartmentType;

    private boolean hasElevator;

    //house
    private HouseType houseType;

    private int yardArea;

    private AreaUnit yardAreaUnit;

    private int floorsCount;

    private String additionalStructures;

    //garage
    private GarageType garageType;

    //land
    private LandType landType;

    //business property
    private BusinessPropertyType businessPropertyType;



    public EditPropertyDTO() {
    }

    public EditPropertyDTO(UUID id, PropertyType propertyType, String city, String neighbourhood, String address, double price, int area, AreaUnit areaUnit, OfferType offerType, String contactName, String contactPhone, String contactEmail, String description, LocalDateTime updatedOn, ConstructionType constructionType, int year, int roomCount, int floor, int buildingFloors, String facing, HeatingType heatingType, ApartmentType apartmentType, boolean hasElevator, HouseType houseType, int yardArea, AreaUnit yardAreaUnit, int floorsCount, String additionalStructures, GarageType garageType, LandType landType, BusinessPropertyType businessPropertyType) {
        this.id = id;
        this.propertyType = propertyType;
        this.city = city;
        this.neighbourhood = neighbourhood;
        this.address = address;
        this.price = price;
        this.area = area;
        this.areaUnit = areaUnit;
        this.offerType = offerType;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.description = description;
        this.updatedOn = updatedOn;
        this.constructionType = constructionType;
        this.year = year;
        this.roomCount = roomCount;
        this.floor = floor;
        this.buildingFloors = buildingFloors;
        this.facing = facing;
        this.heatingType = heatingType;
        this.apartmentType = apartmentType;
        this.hasElevator = hasElevator;
        this.houseType = houseType;
        this.yardArea = yardArea;
        this.yardAreaUnit = yardAreaUnit;
        this.floorsCount = floorsCount;
        this.additionalStructures = additionalStructures;
        this.garageType = garageType;
        this.landType = landType;
        this.businessPropertyType = businessPropertyType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
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

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public HeatingType getHeatingType() {
        return heatingType;
    }

    public void setHeatingType(HeatingType heatingType) {
        this.heatingType = heatingType;
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

    public void setFloorsCount(int floorsCount) {
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
