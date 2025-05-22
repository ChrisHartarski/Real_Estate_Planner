package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.property.PropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.enums.SaleOrRent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.util.List;

public interface BasePropertyService {
    List<BaseProperty> getAllProperties();
    PagedModel<PropertyDTO> getAllPropertiesByCompany(Pageable pageable, SaleOrRent saleOrRent);
}
