package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.property.PropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.*;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.model.enums.PropertyType;
import com.butikimoti.real_estate_planner.repository.BasePropertyRepository;
import com.butikimoti.real_estate_planner.service.*;
import com.butikimoti.real_estate_planner.specifications.BasePropertySpecifications;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
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
    private final PropertyPictureService propertyPictureService;
    private final ModelMapper modelMapper;

    public BasePropertyServiceImpl(BasePropertyRepository basePropertyRepository, UserEntityService userEntityService, ApartmentService apartmentService, BusinessPropertyService businessPropertyService, GarageService garageService, HouseService houseService, LandService landService, PropertyPictureService propertyPictureService, ModelMapper modelMapper) {
        this.basePropertyRepository = basePropertyRepository;
        this.userEntityService = userEntityService;
        this.apartmentService = apartmentService;
        this.businessPropertyService = businessPropertyService;
        this.garageService = garageService;
        this.houseService = houseService;
        this.landService = landService;
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

        Specification<BaseProperty> specification = BasePropertySpecifications.withFilters(ownerCompany, saleOrRent, propertyType, city, neighbourhood, address, minPrice, maxPrice);
        Page<BaseProperty> properties = basePropertyRepository.findAll(specification, pageable);

        return properties.map(this::mapBasePropertyToPropertyDTO);
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

    @Override
    public BaseProperty updateProperty(BaseProperty property) {
        if (property.getClass() == Apartment.class) {
            return apartmentService.updateApartment((Apartment) property);
        }
        if (property.getClass() == BusinessProperty.class) {
            return businessPropertyService.updateBusinessProperty((BusinessProperty) property);
        }
        if (property.getClass() == Garage.class) {
            return garageService.updateGarage((Garage) property);
        }
        if (property.getClass() == House.class) {
            return houseService.updateHouse((House) property);
        }
        if (property.getClass() == Land.class) {
            return landService.updateLand((Land) property);
        }

        throw new RuntimeException("No such property type: " + property.getPropertyType());
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
}
