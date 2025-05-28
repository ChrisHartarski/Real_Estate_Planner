package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.entity.House;

public interface HouseService {
    boolean houseRepositoryIsEmpty();
    House saveHouse(AddPropertyDTO addPropertyDTO);
}
