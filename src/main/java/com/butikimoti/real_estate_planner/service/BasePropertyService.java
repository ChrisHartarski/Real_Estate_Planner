package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.property.PropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.util.List;
import java.util.UUID;

public interface BasePropertyService {
    List<BaseProperty> getAllProperties();
    PagedModel<PropertyDTO> getAllPropertiesByCompany(Pageable pageable, OfferType saleOrRent);
    BaseProperty savePropertyToDB(AddPropertyDTO addPropertyDTO);
    BaseProperty getPropertyByID(UUID id);
}
