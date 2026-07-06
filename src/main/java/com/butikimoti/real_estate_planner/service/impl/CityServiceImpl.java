package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.city.CityDTO;
import com.butikimoti.real_estate_planner.model.entity.City;
import com.butikimoti.real_estate_planner.repository.CityRepository;
import com.butikimoti.real_estate_planner.service.CityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public CityServiceImpl(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
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
    public City getCity(UUID cityId) {
        return cityRepository.findById(cityId).orElse(null);
    }

    @Override
    public City getCity(String cityName) {
        return cityRepository.findCityByName(cityName).orElse(null);
    }

    @Override
    public boolean repoIsEmpty() {
        return cityRepository.count() == 0;
    }

    @Override
    public Set<String> getAllCityNames() {
        return cityRepository.findAll()
                .stream()
                .map(City::getName)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public List<CityDTO> getAllCities() {
        return cityRepository.findAll()
                .stream()
                .map(city -> modelMapper.map(city, CityDTO.class))
                .toList();
    }
}
