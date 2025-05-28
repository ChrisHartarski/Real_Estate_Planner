package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.entity.BusinessProperty;

public interface BusinessPropertyService {
    BusinessProperty saveBusinessProperty(AddPropertyDTO addPropertyDTO);
}
