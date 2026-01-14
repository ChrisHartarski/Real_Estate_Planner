package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.entity.City;

public interface CityService {
    void addCity(String cityName);
    void deleteCity(String cityName);
    City getCity(String cityName);
}
