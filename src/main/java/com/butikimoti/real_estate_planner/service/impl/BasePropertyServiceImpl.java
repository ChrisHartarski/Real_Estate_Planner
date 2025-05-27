package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.property.PropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.*;
import com.butikimoti.real_estate_planner.model.enums.PropertyType;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.repository.BasePropertyRepository;
import com.butikimoti.real_estate_planner.service.BasePropertyService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BasePropertyServiceImpl implements BasePropertyService {
    private final BasePropertyRepository basePropertyRepository;
    private final UserEntityService userEntityService;
    private final ModelMapper modelMapper;

    public BasePropertyServiceImpl(BasePropertyRepository basePropertyRepository, UserEntityService userEntityService, ModelMapper modelMapper) {
        this.basePropertyRepository = basePropertyRepository;
        this.userEntityService = userEntityService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BaseProperty> getAllProperties() {
        return basePropertyRepository.findAll();
    }

    @Override
    public PagedModel<PropertyDTO> getAllPropertiesByCompany(Pageable pageable, OfferType saleOrRent) {
        UUID ownerCompanyId = userEntityService.getCurrentUser().getCompany().getId();
        Page<BaseProperty> properties = basePropertyRepository.findByOwnerCompanyIdAndOfferType(ownerCompanyId, pageable, saleOrRent);

        return new PagedModel<>(properties.map(this::mapBasePropertyToPropertyDTO));
    }

    private PropertyDTO mapBasePropertyToPropertyDTO(BaseProperty baseProperty) {
        PropertyDTO dto = modelMapper.map(baseProperty, PropertyDTO.class);
        setPropertyTypeToDTO(dto, baseProperty);

        return dto;
    }

    private void setPropertyTypeToDTO(PropertyDTO dto, BaseProperty baseProperty) {
        if (baseProperty.getClass() == Apartment.class) {
            dto.setPropertyType(PropertyType.APARTMENT);
        }
        if (baseProperty.getClass() == BusinessProperty.class) {
            dto.setPropertyType(PropertyType.BUSINESS);
        }
        if (baseProperty.getClass() == Garage.class) {
            dto.setPropertyType(PropertyType.GARAGE);
        }
        if (baseProperty.getClass() == House.class) {
            dto.setPropertyType(PropertyType.HOUSE);
        }
        if (baseProperty.getClass() == Land.class) {
            dto.setPropertyType(PropertyType.LAND);
        }
    }
}
