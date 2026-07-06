package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.city.CityDTO;
import com.butikimoti.real_estate_planner.model.entity.City;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CityService {
    void addCity(String cityName);
    void deleteCity(String cityName);
    City getCity(UUID cityId);
    City getCity(String cityName);
    boolean repoIsEmpty();
    Set<String> getAllCityNames();
    List<CityDTO> getAllCities();
}
