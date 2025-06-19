package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.HasPropertyType;
import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.property.EditPropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.property.PropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.*;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.model.enums.PropertyType;
import com.butikimoti.real_estate_planner.repository.BasePropertyRepository;
import com.butikimoti.real_estate_planner.service.BasePropertyService;
import com.butikimoti.real_estate_planner.service.PropertyPictureService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import com.butikimoti.real_estate_planner.specifications.BasePropertySpecifications;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
public class BasePropertyServiceImpl implements BasePropertyService {
    private final BasePropertyRepository basePropertyRepository;
    private final UserEntityService userEntityService;
    private final PropertyPictureService propertyPictureService;
    private final ModelMapper modelMapper;

    public BasePropertyServiceImpl(BasePropertyRepository basePropertyRepository, UserEntityService userEntityService, PropertyPictureService propertyPictureService, ModelMapper modelMapper) {
        this.basePropertyRepository = basePropertyRepository;
        this.userEntityService = userEntityService;
        this.propertyPictureService = propertyPictureService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<PropertyDTO> getAllPropertiesByCompany(Pageable pageable, OfferType saleOrRent, PropertyType propertyType, String city, String neighbourhood, String address, Double minPrice, Double maxPrice) {
        UserEntity currentUser = userEntityService.getCurrentUser();

        if (currentUser == null) {
            throw new RuntimeException("No logged in user");
        }

        Company ownerCompany = currentUser.getCompany();
        if (ownerCompany == null) {
            throw new RuntimeException("User does not have a company");
        }

        Specification<BaseProperty> specification = BasePropertySpecifications.propertiesPageFilters(ownerCompany, saleOrRent, propertyType, city, neighbourhood, address, minPrice, maxPrice);
        Page<BaseProperty> properties = basePropertyRepository.findAll(specification, pageable);

        return properties.map(this::mapBasePropertyToPropertyDTO);
    }

    @Override
    public BaseProperty savePropertyToDB(BaseProperty property) {
        if (property == null) {
            throw new RuntimeException("Property not found");
        }

        property.setUpdatedOn(LocalDateTime.now());

        return basePropertyRepository.saveAndFlush(property);
    }

    @Override
    public BaseProperty saveNewPropertyToDB(AddPropertyDTO addPropertyDTO) {
        Set<PropertyType> propertyTypes = Set.of(PropertyType.values());
        if (!propertyTypes.contains(addPropertyDTO.getPropertyType())) {
            throw new RuntimeException("No such property type: " + addPropertyDTO.getPropertyType());
        }

        BaseProperty property = getBasePropertyFromDTO(addPropertyDTO);

        Company ownerCompany = getOwnerCompany(addPropertyDTO);
        property.setOwnerCompany(ownerCompany);

        property.setCreatedOn(LocalDateTime.now());

        return savePropertyToDB(property);
    }

    @Override
    public BaseProperty editPropertyAndAddToDB(EditPropertyDTO editPropertyDTO) {
        BaseProperty property = basePropertyRepository.findById(editPropertyDTO.getId()).orElse(null);

        if (property == null) {
            throw new RuntimeException("Property not found");
        }

        applyEditPropertyDTOToProperty(property, editPropertyDTO);

        return savePropertyToDB(property);
    }

    @Override
    public BaseProperty getPropertyByID(UUID id) {
        return basePropertyRepository.findById(id).orElseThrow(() -> new RuntimeException("Property not found"));
    }

    @Override
    public void deleteProperty(UUID id) throws IOException {
        deleteAllPicturesFromProperty(id);
        basePropertyRepository.deleteById(id);
    }

    @Override
    public void deletePicture(UUID id, UUID pictureId) throws IOException {
        BaseProperty property = basePropertyRepository.findById(id).orElseThrow(() -> new RuntimeException("Property not found"));
        PropertyPicture picture = propertyPictureService.getPicture(pictureId);

        propertyPictureService.deletePictureFromCloud(pictureId);
        property.getPictures().remove(picture);
        basePropertyRepository.saveAndFlush(property);
    }

    private void deleteAllPicturesFromProperty(UUID propertyId) throws IOException {
        BaseProperty property = getPropertyByID(propertyId);
        while (!property.getPictures().isEmpty()) {
            deletePicture(property.getId(), property.getPictures().getFirst().getId());
        }
    }

    private PropertyDTO mapBasePropertyToPropertyDTO(BaseProperty baseProperty) {
        return modelMapper.map(baseProperty, PropertyDTO.class);
    }

    private Company getOwnerCompany(AddPropertyDTO addPropertyDTO) {
        UserEntity currentUser = userEntityService.getCurrentUser();
        if (currentUser == null) {
            throw new RuntimeException("No logged in user");
        }

        Company ownerCompany = currentUser.getCompany();
        if (ownerCompany == null) {
            throw new RuntimeException("User does not have a company");
        }
        return ownerCompany;
    }

    private BaseProperty getBasePropertyFromDTO(HasPropertyType dto) {
        return  switch (dto.getPropertyType()) {
            case APARTMENT -> modelMapper.map(dto, Apartment.class);
            case BUSINESS -> modelMapper.map(dto, BusinessProperty.class);
            case GARAGE -> modelMapper.map(dto, Garage.class);
            case HOUSE -> modelMapper.map(dto, House.class);
            case LAND -> modelMapper.map(dto, Land.class);
            default -> throw new RuntimeException("No such property type: " + dto.getPropertyType());
        };
    }

    private void applyEditPropertyDTOToProperty(BaseProperty property, EditPropertyDTO editPropertyDTO) {
        Configuration configuration = modelMapper.getConfiguration();
        boolean isSkipNullEnabled = configuration.isSkipNullEnabled();

        try {
            configuration.setSkipNullEnabled(true);
            modelMapper.map(editPropertyDTO, property);
        } finally {
            configuration.setSkipNullEnabled(isSkipNullEnabled);
        }

        property.setUpdatedOn(LocalDateTime.now());
    }
}
