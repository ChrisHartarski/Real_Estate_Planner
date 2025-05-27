package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.House;
import com.butikimoti.real_estate_planner.repository.HouseRepository;
import com.butikimoti.real_estate_planner.service.HouseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class HouseServiceImpl implements HouseService {
    private final HouseRepository houseRepository;
    private final ModelMapper modelMapper;

    public HouseServiceImpl(HouseRepository houseRepository, ModelMapper modelMapper) {
        this.houseRepository = houseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addHouse(AddPropertyDTO addPropertyDTO) {
        House house = modelMapper.map(addPropertyDTO, House.class);
        houseRepository.saveAndFlush(house);
    }

    @Override
    public boolean houseRepositoryIsEmpty() {
        return houseRepository.count() == 0;
    }
}
