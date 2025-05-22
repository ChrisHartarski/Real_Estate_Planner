package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.house.AddHouseDTO;

public interface HouseService {
    void addHouse(AddHouseDTO addHouseDTO);
    boolean houseRepositoryIsEmpty();
}
