package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.apartment.AddApartmentDTO;

public interface ApartmentService {
    void addApartment(AddApartmentDTO addApartmentDTO);
    boolean apartmentRepositoryIsEmpty();
}
