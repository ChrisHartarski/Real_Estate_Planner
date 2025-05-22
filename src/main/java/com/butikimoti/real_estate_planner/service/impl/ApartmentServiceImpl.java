package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.entity.Apartment;
import com.butikimoti.real_estate_planner.repository.ApartmentRepository;
import com.butikimoti.real_estate_planner.service.ApartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ModelMapper modelMapper;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, ModelMapper modelMapper) {
        this.apartmentRepository = apartmentRepository;
        this.modelMapper = modelMapper;
    }

}
