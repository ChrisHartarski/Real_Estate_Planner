package com.butikimoti.real_estate_planner.model.entity;

import com.butikimoti.real_estate_planner.model.enums.AreaUnit;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.model.enums.PropertyType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "property")
    @OrderBy("createdOn")
    private List<PropertyPicture> pictures;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type", nullable = false)
    private PropertyType propertyType;

    @ManyToOne
    private Company ownerCompany;

    @Column(nullable = false)
    private String city;

    @Column
    private String neighbourhood;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int area;

    @Enumerated(EnumType.STRING)
    @Column(name = "area_unit", nullable = false)
    private AreaUnit areaUnit;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_or_rent", nullable = false)
    private OfferType offerType;

    @Column(name = "contact_name", nullable = false)
    private String contactName;

    @Column(name = "contact_phone", nullable = false)
    private String contactPhone;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column (columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "updated_on", nullable = false)
    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "property", orphanRemoval = true)
    @OrderBy("date DESC")
    private List<Comment> comments;

    protected BaseProperty() {
        this.pictures = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    protected BaseProperty(UUID id, PropertyType propertyType, Company ownerCompany, String city, String neighbourhood, String address, double price, int area, AreaUnit areaUnit, OfferType offerType, String contactName, String contactPhone, String contactEmail, String description, LocalDateTime createdOn, LocalDateTime updatedOn) {
        this();
        this.id = id;
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
    }

    protected BaseProperty(UUID id, List<PropertyPicture> pictures, PropertyType propertyType, Company ownerCompany, String city, String neighbourhood, String address, double price, int area, AreaUnit areaUnit, OfferType offerType, String contactName, String contactPhone, String contactEmail, String description, LocalDateTime createdOn, LocalDateTime updatedOn, List<Comment> comments) {
        this(id, propertyType, ownerCompany, city, neighbourhood, address, price, area, areaUnit, offerType, contactName, contactPhone, contactEmail, description, createdOn, updatedOn);
        this.pictures = pictures;
        this.comments = comments;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<PropertyPicture> getPictures() {
        return pictures;
    }

    public void setPictures(List<PropertyPicture> pictures) {
        this.pictures = pictures;
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

    public void setOfferType(OfferType saleOrRent) {
        this.offerType = saleOrRent;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public double getPricePerSqMeter() {
        return this.price / this.area;
    }

}
