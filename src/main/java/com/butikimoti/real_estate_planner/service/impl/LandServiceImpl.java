package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.Land;
import com.butikimoti.real_estate_planner.repository.LandRepository;
import com.butikimoti.real_estate_planner.service.LandService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class LandServiceImpl implements LandService {
    private final LandRepository landRepository;
    private final ModelMapper modelMapper;

    public LandServiceImpl(LandRepository landRepository, ModelMapper modelMapper) {
        this.landRepository = landRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Land saveLand(AddPropertyDTO addPropertyDTO) {
        Land land = modelMapper.map(addPropertyDTO, Land.class);
        return landRepository.saveAndFlush(land);
    }
}
