package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.entity.City;
import com.butikimoti.real_estate_planner.repository.CityRepository;
import com.butikimoti.real_estate_planner.service.CityService;

import java.util.Set;
import java.util.stream.Collectors;

public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public void addCity(String cityName) {
        if (getAllCityNames().contains(cityName)) {
            return;
        }

        City city = new City(cityName);
        cityRepository.saveAndFlush(city);
    }

    @Override
    public void deleteCity(String cityName) {
        if (!getAllCityNames().contains(cityName)) {
            return;
        }

        cityRepository.deleteCityByName(cityName);
    }

    @Override
    public City getCity(String cityName) {
        return cityRepository.findCityByName(cityName).orElse(null);
    }


    Set<String> getAllCityNames() {
        return cityRepository.findAll()
                .stream()
                .map(City::getName)
                .collect(Collectors.toSet());
    }
}
