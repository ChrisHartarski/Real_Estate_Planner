package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.property.PropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.model.enums.PropertyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.UUID;

public interface BasePropertyService {
    Page<PropertyDTO> getAllPropertiesByCompany(Pageable pageable, OfferType saleOrRent, PropertyType propertyType, String city, String neighbourhood, String address, Double minPrice, Double maxPrice);
    BaseProperty savePropertyToDB(BaseProperty property);
    BaseProperty saveNewPropertyToDB(AddPropertyDTO addPropertyDTO);
    BaseProperty getPropertyByID(UUID id);
    void deleteProperty(UUID id) throws IOException;
    void deletePicture(UUID id, UUID pictureId) throws IOException;
}
