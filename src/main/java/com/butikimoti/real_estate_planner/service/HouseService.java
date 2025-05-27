package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;

public interface HouseService {
    void addHouse(AddPropertyDTO addPropertyDTO);
    boolean houseRepositoryIsEmpty();
}
