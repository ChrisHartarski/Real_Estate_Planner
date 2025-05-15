package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.repository.ApartmentRepository;
import com.butikimoti.real_estate_planner.service.ApartmentService;
import org.springframework.stereotype.Service;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }
}
