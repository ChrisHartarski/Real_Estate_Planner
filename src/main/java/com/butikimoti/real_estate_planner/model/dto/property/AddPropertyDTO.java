package com.butikimoti.real_estate_planner.model.dto.property;

import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.enums.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class AddPropertyDTO implements HasPropertyType {
    public interface ApartmentGroup {}
    public interface BusinessPropertyGroup {}
    public interface HouseGroup {}
    public interface GarageGroup {}
    public interface LandGroup {}

    @NotNull(message = "{propertyType.notEmpty}")
    private PropertyType propertyType;

    private Company ownerCompany;

    @NotNull(message = "{propertyCity.notEmpty}")
    private String city;

    private String neighbourhood;

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
    @NotNull(groups = {ApartmentGroup.class, BusinessPropertyGroup.class, HouseGroup.class}, message = "{propertyConstructionType.notEmpty}")
    private ConstructionType constructionType;

    @NotNull(groups = {ApartmentGroup.class, BusinessPropertyGroup.class, HouseGroup.class}, message = "{propertyYear.notEmpty}")
    @Positive(groups = {ApartmentGroup.class, BusinessPropertyGroup.class, HouseGroup.class}, message = "{propertyYear.positive}")
    private Integer year;

    @NotNull(groups = {ApartmentGroup.class, BusinessPropertyGroup.class, HouseGroup.class}, message = "{propertyRoomCount.notEmpty}")
    private Integer roomCount;

    @NotNull(groups = {ApartmentGroup.class, BusinessPropertyGroup.class}, message = "{propertyFloor.notEmpty}")
    private Integer floor;

    @NotNull(groups = {ApartmentGroup.class, BusinessPropertyGroup.class}, message = "{propertyBuildingFloors.notEmpty}")
    private Integer buildingFloors;

    private String facing;

    @NotNull(groups = {ApartmentGroup.class, BusinessPropertyGroup.class, HouseGroup.class}, message = "{propertyHeatingType.notEmpty}")
    private HeatingType heatingType;

    //apartment
    @NotNull(groups = ApartmentGroup.class, message = "{propertyApartmentType.notEmpty}")
    private ApartmentType apartmentType;

    private boolean hasElevator;

    //house
    @NotNull(groups = HouseGroup.class, message = "{propertyHouseType.notEmpty}")
    private HouseType houseType;

    private Integer yardArea;

    private AreaUnit yardAreaUnit;

    @NotNull(groups = HouseGroup.class, message = "{propertyFloorsCount.notEmpty}")
    private Integer floorsCount;

    private String additionalStructures;

    //garage
    @NotNull(groups = GarageGroup.class, message = "{propertyGarageType.notEmpty}")
    private GarageType garageType;

    //land
    @NotNull(groups = LandGroup.class, message = "{propertyLandType.notEmpty}")
    private LandType landType;

    //business property
    @NotNull(groups = BusinessPropertyGroup.class, message = "{propertyBusinessPropertyType.notEmpty}")
    private BusinessPropertyType businessPropertyType;


    public AddPropertyDTO() {
    }

    public AddPropertyDTO(PropertyType propertyType, Company ownerCompany, String city, String neighbourhood, String address, Double price, Integer area, AreaUnit areaUnit, OfferType offerType, String contactName, String contactPhone, String contactEmail, String description, LocalDateTime createdOn, LocalDateTime updatedOn, ConstructionType constructionType, Integer year, Integer roomCount, Integer floor, Integer buildingFloors, String facing, HeatingType heatingType, ApartmentType apartmentType, boolean hasElevator, HouseType houseType, Integer yardArea, AreaUnit yardAreaUnit, Integer floorsCount, String additionalStructures, GarageType garageType, LandType landType, BusinessPropertyType businessPropertyType) {
        this.propertyType = propertyType;
        this.ownerCompany = ownerCompany;
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
        this.createdOn = createdOn;
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
