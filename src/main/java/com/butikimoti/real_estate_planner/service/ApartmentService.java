package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.Apartment;

public interface ApartmentService {
    Apartment saveApartment(AddPropertyDTO addPropertyDTO);
    Apartment updateApartment(Apartment apartment);
}
