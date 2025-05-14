package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.repository.GarageRepository;
import com.butikimoti.real_estate_planner.service.GarageService;
import org.springframework.stereotype.Service;

@Service
public class GarageServiceImpl implements GarageService {
    private final GarageRepository garageRepository;

    public GarageServiceImpl(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }
}
