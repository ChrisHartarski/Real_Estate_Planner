package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.property.PropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.*;
import com.butikimoti.real_estate_planner.model.enums.PropertyType;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.repository.BasePropertyRepository;
import com.butikimoti.real_estate_planner.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BasePropertyServiceImpl implements BasePropertyService {
    private final BasePropertyRepository basePropertyRepository;
    private final UserEntityService userEntityService;
    private final ApartmentService apartmentService;
    private final BusinessPropertyService businessPropertyService;
    private final GarageService garageService;
    private final HouseService houseService;
    private final LandService landService;
    private final ModelMapper modelMapper;

    public BasePropertyServiceImpl(BasePropertyRepository basePropertyRepository, UserEntityService userEntityService, ApartmentService apartmentService, BusinessPropertyService businessPropertyService, GarageService garageService, HouseService houseService, LandService landService, ModelMapper modelMapper) {
        this.basePropertyRepository = basePropertyRepository;
        this.userEntityService = userEntityService;
        this.apartmentService = apartmentService;
        this.businessPropertyService = businessPropertyService;
        this.garageService = garageService;
        this.houseService = houseService;
        this.landService = landService;
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

    @Override
    public BaseProperty savePropertyToDB(AddPropertyDTO addPropertyDTO) {
        addPropertyDTO.setOwnerCompany(userEntityService.getCurrentUser().getCompany());
        addPropertyDTO.setCreatedOn(LocalDateTime.now());
        addPropertyDTO.setUpdatedOn(LocalDateTime.now());

        if (addPropertyDTO.getPropertyType() == PropertyType.APARTMENT) {
            return apartmentService.saveApartment(addPropertyDTO);
        }
        if (addPropertyDTO.getPropertyType() == PropertyType.BUSINESS) {
            return businessPropertyService.saveBusinessProperty(addPropertyDTO);
        }
        if (addPropertyDTO.getPropertyType() == PropertyType.GARAGE) {
            return garageService.saveGarage(addPropertyDTO);
        }
        if (addPropertyDTO.getPropertyType() == PropertyType.HOUSE) {
            return houseService.saveHouse(addPropertyDTO);
        }
        if (addPropertyDTO.getPropertyType() == PropertyType.LAND) {
            return landService.saveLand(addPropertyDTO);
        }

        throw new RuntimeException("No such property type: " + addPropertyDTO.getPropertyType());
    }

    @Override
    public BaseProperty getPropertyByID(UUID id) {
        return basePropertyRepository.findById(id).orElseThrow(() -> new RuntimeException("Property not found"));
    }

    private PropertyDTO mapBasePropertyToPropertyDTO(BaseProperty baseProperty) {
        return modelMapper.map(baseProperty, PropertyDTO.class);
    }
}
