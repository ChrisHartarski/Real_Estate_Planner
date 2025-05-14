package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.repository.HouseRepository;
import com.butikimoti.real_estate_planner.service.HouseService;
import org.springframework.stereotype.Service;

@Service
public class HouseServiceImpl implements HouseService {
    private final HouseRepository houseRepository;

    public HouseServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }
}
