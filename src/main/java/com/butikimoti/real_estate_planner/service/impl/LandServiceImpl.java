package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.repository.LandRepository;
import com.butikimoti.real_estate_planner.service.LandService;
import org.springframework.stereotype.Service;

@Service
public class LandServiceImpl implements LandService {
    private final LandRepository landRepository;

    public LandServiceImpl(LandRepository landRepository) {
        this.landRepository = landRepository;
    }
}
