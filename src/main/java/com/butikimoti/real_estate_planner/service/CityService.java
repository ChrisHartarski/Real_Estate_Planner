package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.entity.City;

import java.util.Set;

public interface CityService {
    void addCity(String cityName);
    void deleteCity(String cityName);
    City getCity(String cityName);
    boolean repoIsEmpty();
    Set<String> getAllCityNames();
}
