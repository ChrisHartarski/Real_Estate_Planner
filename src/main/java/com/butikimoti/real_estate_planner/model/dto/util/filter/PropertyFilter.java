package com.butikimoti.real_estate_planner.model.dto.util.filter;

import com.butikimoti.real_estate_planner.model.enums.PropertyType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PropertyFilter {

    private PropertyType propertyType;
    private UUID city;
    private List<UUID> neighbourhoods;
    private String contactPhone;
    private Double minPrice;
    private Double maxPrice;

    public PropertyFilter() {
        neighbourhoods = new ArrayList<>();
    }

    public PropertyFilter(PropertyType propertyType, UUID city, List<UUID> neighbourhoods, String contactPhone, Double minPrice, Double maxPrice) {
        this.propertyType = propertyType;
        this.city = city;
        this.neighbourhoods = neighbourhoods;
        this.contactPhone = contactPhone;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public UUID getCity() {
        return city;
    }

    public void setCity(UUID city) {
        this.city = city;
    }

    public List<UUID> getNeighbourhoods() {
        return neighbourhoods;
    }

    public void setNeighbourhoods(List<UUID> neighbourhoods) {
        this.neighbourhoods = neighbourhoods;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void clear() {
        setCity(null);
        setNeighbourhoods(new ArrayList<>());
        setContactPhone(null);
        setMinPrice(null);
        setMaxPrice(null);
    }
}