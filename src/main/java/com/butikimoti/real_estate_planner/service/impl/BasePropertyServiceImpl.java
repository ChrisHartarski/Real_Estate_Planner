package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.property.EditPropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.property.PropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.util.filter.PropertyFilter;
import com.butikimoti.real_estate_planner.model.entity.*;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.model.enums.PropertyType;
import com.butikimoti.real_estate_planner.repository.BasePropertyRepository;
import com.butikimoti.real_estate_planner.service.*;
import com.butikimoti.real_estate_planner.service.util.exceptions.ResourceNotFoundException;
import com.butikimoti.real_estate_planner.service.util.exceptions.UnauthorizedException;
import com.butikimoti.real_estate_planner.specifications.BasePropertySpecifications;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class BasePropertyServiceImpl implements BasePropertyService {
    private final BasePropertyRepository basePropertyRepository;
    private final UserEntityService userEntityService;
    private final PropertyPictureService propertyPictureService;
    private final CityService cityService;
    private final NeighbourhoodService neighbourhoodService;
    private final ModelMapper modelMapper;

    public BasePropertyServiceImpl(BasePropertyRepository basePropertyRepository, UserEntityService userEntityService, PropertyPictureService propertyPictureService, CityService cityService, NeighbourhoodService neighbourhoodService, ModelMapper modelMapper) {
        this.basePropertyRepository = basePropertyRepository;
        this.userEntityService = userEntityService;
        this.propertyPictureService = propertyPictureService;
        this.cityService = cityService;
        this.neighbourhoodService = neighbourhoodService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<PropertyDTO> getAllPropertiesByCompany(Pageable pageable, OfferType saleOrRent, boolean isArchived, PropertyFilter filter) {
        UserEntity currentUser = userEntityService.getCurrentUser();

        if (currentUser == null) {
            throw new UnauthorizedException("No logged in user");
        }

        Company ownerCompany = currentUser.getCompany();
        if (ownerCompany == null) {
            throw new RuntimeException("User does not have a company");
        }

        Specification<BaseProperty> specification = BasePropertySpecifications.propertiesPageFilters(ownerCompany, saleOrRent, isArchived, filter);
        Page<BaseProperty> properties = basePropertyRepository.findAll(specification, pageable);

        return properties.map(this::mapBasePropertyToPropertyDTO);
    }

    @Override
    public BaseProperty savePropertyToDB(BaseProperty property) {
        if (property == null) {
            throw new ResourceNotFoundException("Property not found");
        }

        property.setUpdatedOn(LocalDateTime.now());

        return basePropertyRepository.saveAndFlush(property);
    }

    @Override
    public BaseProperty saveNewPropertyToDB(AddPropertyDTO addPropertyDTO) {
        Set<PropertyType> propertyTypes = Set.of(PropertyType.values());
        if (!propertyTypes.contains(addPropertyDTO.getPropertyType())) {
            throw new ResourceNotFoundException("No such property type: " + addPropertyDTO.getPropertyType());
        }
        City city = cityService.getCity(addPropertyDTO.getCity());
        Neighbourhood neighbourhood = neighbourhoodService.getNeighbourhood(addPropertyDTO.getNeighbourhood());

        BaseProperty property = getBasePropertyFromDTO(addPropertyDTO);
        property.setCity(city);
        property.setNeighbourhood(neighbourhood);
        property.setOwnerCompany(getOwnerCompany());
        property.setCreatedOn(LocalDateTime.now());

        return savePropertyToDB(property);
    }

    @Override
    public BaseProperty editPropertyAndAddToDB(EditPropertyDTO editPropertyDTO) {
        BaseProperty property = basePropertyRepository.findById(editPropertyDTO.getId()).orElse(null);

        if (property == null) {
            throw new ResourceNotFoundException("Property not found");
        }

        applyEditPropertyDTOToProperty(property, editPropertyDTO);

        return savePropertyToDB(property);
    }

    @Override
    public BaseProperty getPropertyByID(UUID id) {
        return basePropertyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Property not found"));
    }

    @Override
    public void archiveProperty(UUID id) {
        BaseProperty property = getPropertyByID(id);

        if(property.isArchived()) {
            property.setArchived(false);
            property.setArchivedOn(null);
        } else {
            property.setArchived(true);
            property.setArchivedOn(LocalDateTime.now());
        }

        savePropertyToDB(property);
    }

    @Override
    public void deleteProperty(UUID id) throws IOException {
        deleteAllPicturesFromProperty(id);
        basePropertyRepository.deleteById(id);
    }

    @Override
    public void deletePicture(UUID id, UUID pictureId) throws IOException {
        try {
            BaseProperty property = basePropertyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Property not found"));
            PropertyPicture picture = propertyPictureService.getPicture(pictureId);

            propertyPictureService.deletePictureFromCloud(picture);
            property.getPictures().remove(picture);
            basePropertyRepository.saveAndFlush(property);
        } catch (ResourceNotFoundException e) {
            System.out.println("Property not found");
        }
    }

    @Override
    public List<String> getNeighbourhoodNamesFromFilter(PropertyFilter propertyFilter) {
        List<String> neighbourhoodNames = new ArrayList<>();

        if(propertyFilter.getNeighbourhoods() != null && !propertyFilter.getNeighbourhoods().isEmpty()) {
            neighbourhoodNames = propertyFilter.getNeighbourhoods()
                    .stream()
                    .map (id -> neighbourhoodService.getNeighbourhood(id).getName())
                    .toList();
        }

        return neighbourhoodNames;
    }

    private void deleteAllPicturesFromProperty(UUID propertyId) throws IOException {
        BaseProperty property = getPropertyByID(propertyId);
        List<PropertyPicture> pictures = new ArrayList<>(property.getPictures());

        property.getPictures().clear();
        basePropertyRepository.saveAndFlush(property);

        for (PropertyPicture picture : pictures) {
            CompletableFuture.runAsync(() -> {
                try {
                    propertyPictureService.deletePictureFromCloud(picture);
                } catch (IOException e) {
                    System.err.println("Failed to delete Cloudinary picture: " + picture.getId());
                    e.printStackTrace();
                }
            });
        }
    }

    private PropertyDTO mapBasePropertyToPropertyDTO(BaseProperty baseProperty) {
        return modelMapper.map(baseProperty, PropertyDTO.class);
    }

    private Company getOwnerCompany() {
        UserEntity currentUser = userEntityService.getCurrentUser();
        if (currentUser == null) {
            throw new UnauthorizedException("No logged in user");
        }

        Company ownerCompany = currentUser.getCompany();
        if (ownerCompany == null) {
            throw new RuntimeException("User does not have a company");
        }

        return ownerCompany;
    }

    private BaseProperty getBasePropertyFromDTO(AddPropertyDTO dto) {
        City city = cityService.getCity(dto.getCity());
        Neighbourhood neighbourhood = neighbourhoodService.getNeighbourhood(dto.getNeighbourhood());

        BaseProperty result =  switch (dto.getPropertyType()) {
            case APARTMENT -> modelMapper.map(dto, Apartment.class);
            case BUSINESS -> modelMapper.map(dto, BusinessProperty.class);
            case GARAGE -> modelMapper.map(dto, Garage.class);
            case HOUSE -> modelMapper.map(dto, House.class);
            case LAND -> modelMapper.map(dto, Land.class);
            default -> throw new ResourceNotFoundException("No such property type: " + dto.getPropertyType());
        };

        result.setCity(city);
        result.setNeighbourhood(neighbourhood);
        return result;
    }

    private void applyEditPropertyDTOToProperty(BaseProperty property, EditPropertyDTO editPropertyDTO) {
        Configuration configuration = modelMapper.getConfiguration();
        boolean isSkipNullEnabled = configuration.isSkipNullEnabled();

        try {
            configuration.setSkipNullEnabled(true);

            City city = cityService.getCity(editPropertyDTO.getCityId());
            property.setCity(city);

            Neighbourhood neighbourhood = neighbourhoodService.getNeighbourhood(editPropertyDTO.getNeighbourhoodId());
            property.setNeighbourhood(neighbourhood);

            modelMapper.map(editPropertyDTO, property);

        } finally {
            configuration.setSkipNullEnabled(isSkipNullEnabled);
        }

        property.setUpdatedOn(LocalDateTime.now());
    }
}
