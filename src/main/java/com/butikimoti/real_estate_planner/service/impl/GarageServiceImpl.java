package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.Garage;
import com.butikimoti.real_estate_planner.repository.GarageRepository;
import com.butikimoti.real_estate_planner.service.GarageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GarageServiceImpl implements GarageService {
    private final GarageRepository garageRepository;
    private final ModelMapper modelMapper;

    public GarageServiceImpl(GarageRepository garageRepository, ModelMapper modelMapper) {
        this.garageRepository = garageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Garage saveGarage(AddPropertyDTO addPropertyDTO) {
        Garage garage = modelMapper.map(addPropertyDTO, Garage.class);
        return garageRepository.saveAndFlush(garage);
    }
}
