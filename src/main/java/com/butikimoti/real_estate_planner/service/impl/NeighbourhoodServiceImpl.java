package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.neighbourhood.NeighbourhoodDTO;
import com.butikimoti.real_estate_planner.model.entity.City;
import com.butikimoti.real_estate_planner.model.entity.Neighbourhood;
import com.butikimoti.real_estate_planner.repository.NeighbourhoodRepository;
import com.butikimoti.real_estate_planner.service.CityService;
import com.butikimoti.real_estate_planner.service.NeighbourhoodService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NeighbourhoodServiceImpl implements NeighbourhoodService {
    private final NeighbourhoodRepository neighbourhoodRepository;
    private final CityService cityService;
    private final ModelMapper modelMapper;

    public NeighbourhoodServiceImpl(NeighbourhoodRepository neighbourhoodRepository, CityService cityService, ModelMapper modelMapper) {
        this.neighbourhoodRepository = neighbourhoodRepository;
        this.cityService = cityService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addNeighbourhood(String neighbourhoodName, String cityName) {
        if (getNeighbourhoodNamesInCity(cityName).contains(neighbourhoodName)) {
            return;
        }

        City city = cityService.getCity(cityName);
        Neighbourhood neighbourhood = new Neighbourhood(neighbourhoodName, city);
        neighbourhoodRepository.saveAndFlush(neighbourhood);
    }

    @Override
    public void deleteNeighbourhood(UUID neighbourhoodID) {
        neighbourhoodRepository.deleteById(neighbourhoodID);
    }

    @Override
    public void deleteNeighbourhood(String neighbourhoodName, String cityName) {
        neighbourhoodRepository.deleteByNameAndCityName(neighbourhoodName, cityName);
    }

    @Override
    public Neighbourhood getNeighbourhood(UUID neighbourhoodID) {
        return neighbourhoodRepository.findById(neighbourhoodID).orElse(null);
    }

    @Override
    public Neighbourhood getNeighbourhood(String neighbourhoodName, String cityName) {
        return neighbourhoodRepository.findByNameAndCityName(neighbourhoodName, cityName).orElse(null);
    }

    @Override
    public boolean repoIsEmpty() {
        return neighbourhoodRepository.count() == 0;
    }

    @Override
    public NeighbourhoodDTO getNeighbourhoodDTO(Neighbourhood n) {
        NeighbourhoodDTO dto = modelMapper.map(n, NeighbourhoodDTO.class);
        dto.setCityId(n.getCity().getId());

        return dto;
    }

    Set<String> getNeighbourhoodNamesInCity(String cityName) {
        return neighbourhoodRepository.findAllByCityName(cityName)
                .stream()
                .map(Neighbourhood::getName)
                .collect(Collectors.toSet());
    }
}
