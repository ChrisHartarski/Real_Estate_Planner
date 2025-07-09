package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.dto.property.PropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.*;
import com.butikimoti.real_estate_planner.model.enums.*;
import com.butikimoti.real_estate_planner.repository.BasePropertyRepository;
import com.butikimoti.real_estate_planner.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasePropertyServiceImplUnitTests {
    private BasePropertyService serviceToTest;
    private final ModelMapper modelMapper = new ModelMapper();
    private static final UUID TEST_COMPANY_ID = UUID.randomUUID();
    private static final UUID TEST_USER_ID = UUID.randomUUID();
    private static final UUID TEST_APARTMENT_ID = UUID.randomUUID();
    private static final UUID TEST_HOUSE_ID = UUID.randomUUID();
    private static final UUID TEST_LAND_ID = UUID.randomUUID();
    private static final Company TEST_COMPANY = new Company(TEST_COMPANY_ID, new ArrayList<>(), "Test Company", "Test Address", "+359000000000", "test@email.com", new ArrayList<>());
    private static final UserEntity TEST_USER = new UserEntity(TEST_USER_ID, "user@mail.com", TEST_COMPANY, "password", UserRole.COMPANY_ADMIN, "name", "last_name", "+359 000 000 000");
    private static final Apartment TEST_APARTMENT = new Apartment(TEST_APARTMENT_ID, PropertyType.APARTMENT, TEST_COMPANY, "test_city", "test_neighbourhood", "test_address", 70000.00, 70, AreaUnit.SQUARE_METER, OfferType.SALE, "contact_name", "+359 000 000 000", "contact@mail.com", "description", LocalDateTime.now(), LocalDateTime.now(), ApartmentType.THREE_ROOM, 3, ConstructionType.BRICK, 2000, 3, 8, HeatingType.GAS, true, "facing");
    private static final House TEST_HOUSE = new House(TEST_HOUSE_ID, PropertyType.HOUSE, TEST_COMPANY, "test_city", "test_neighbourhood", "test_address", 45000.00, 120, AreaUnit.SQUARE_METER, OfferType.SALE, "contact_name_2", "+359 111 111 111", "contact2@mail.com", "description2", LocalDateTime.now(), LocalDateTime.now(), HouseType.HOUSE, ConstructionType.JOIST, 1982, HeatingType.HARD_FUEL, 500, AreaUnit.SQUARE_METER, 2, "additional_structures");
    private static final Land TEST_LAND = new Land(TEST_LAND_ID, PropertyType.LAND, TEST_COMPANY, "test_city", "test_neighbourhood", "test_address3", 120000.00, 100, AreaUnit.DECARE, OfferType.SALE, "contact_name_3", "+359 333 333 333", "contact3@mail.com", "description3", LocalDateTime.now(), LocalDateTime.now(), LandType.ARABLE_LAND);

    @Mock
    private BasePropertyRepository basePropertyRepository;

    @Mock
    private UserEntityService userEntityService;

    @Mock
    private PropertyPictureService propertyPictureService;

    @Captor
    private ArgumentCaptor<BaseProperty> captor;

    @BeforeEach
    void setUp() {
        serviceToTest = new BasePropertyServiceImpl(basePropertyRepository, userEntityService, propertyPictureService, new ModelMapper());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetAllPropertiesByCompany_returnsCorrectData() {
        List<BaseProperty> properties = List.of(TEST_APARTMENT, TEST_HOUSE, TEST_LAND);
        Pageable pageable = PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "updatedOn"));

        when(userEntityService.getCurrentUser()).thenReturn(TEST_USER);
        when(basePropertyRepository.findAll(
                (Specification<BaseProperty>) any(),
                eq(pageable)))
                .thenReturn(new PageImpl<>(properties, pageable, 20));

        Page<PropertyDTO> result = serviceToTest.getAllPropertiesByCompany(pageable, OfferType.SALE, null, null, null, null, null, null);
        List<PropertyDTO> resultList = result.getContent();

        PropertyDTO apartmentDTO = resultList.stream().filter(propertyDTO -> propertyDTO.getPropertyType() == PropertyType.APARTMENT).findFirst().orElse(null);
        PropertyDTO houseDTO = resultList.stream().filter(propertyDTO -> propertyDTO.getPropertyType() == PropertyType.HOUSE).findFirst().orElse(null);
        PropertyDTO landDTO = resultList.stream().filter(propertyDTO -> propertyDTO.getPropertyType() == PropertyType.LAND).findFirst().orElse(null);

        Assertions.assertNotNull(apartmentDTO);
        Assertions.assertNotNull(houseDTO);
        Assertions.assertNotNull(landDTO);

        Assertions.assertEquals(TEST_APARTMENT.getId(), apartmentDTO.getId());
        Assertions.assertEquals(TEST_APARTMENT.getApartmentType(), apartmentDTO.getApartmentType());
        Assertions.assertEquals(TEST_APARTMENT.getCity(), apartmentDTO.getCity());
        Assertions.assertEquals(TEST_APARTMENT.getNeighbourhood(), apartmentDTO.getNeighbourhood());
        Assertions.assertEquals(TEST_APARTMENT.getAddress(), apartmentDTO.getAddress());
        Assertions.assertEquals(TEST_APARTMENT.getDescription(), apartmentDTO.getDescription());
        Assertions.assertEquals(TEST_APARTMENT.getContactName(), apartmentDTO.getContactName());
        Assertions.assertEquals(TEST_APARTMENT.getContactEmail(), apartmentDTO.getContactEmail());
        Assertions.assertEquals(TEST_APARTMENT.getContactPhone(), apartmentDTO.getContactPhone());
        Assertions.assertEquals(TEST_APARTMENT.getFloor(), apartmentDTO.getFloor());
        Assertions.assertEquals(TEST_APARTMENT.getBuildingFloors(), apartmentDTO.getBuildingFloors());
        Assertions.assertEquals(TEST_APARTMENT.getFacing(), apartmentDTO.getFacing());
        Assertions.assertEquals(TEST_APARTMENT.getYear(), apartmentDTO.getYear());
        Assertions.assertEquals(TEST_APARTMENT.getConstructionType(), apartmentDTO.getConstructionType());
        Assertions.assertEquals(TEST_APARTMENT.getHeatingType(), apartmentDTO.getHeatingType());

        Assertions.assertEquals(TEST_HOUSE.getId(), houseDTO.getId());
        Assertions.assertEquals(TEST_HOUSE.getCity(), houseDTO.getCity());
        Assertions.assertEquals(TEST_HOUSE.getNeighbourhood(), houseDTO.getNeighbourhood());
        Assertions.assertEquals(TEST_HOUSE.getAddress(), houseDTO.getAddress());
        Assertions.assertEquals(TEST_HOUSE.getDescription(), houseDTO.getDescription());
        Assertions.assertEquals(TEST_HOUSE.getContactName(), houseDTO.getContactName());
        Assertions.assertEquals(TEST_HOUSE.getContactEmail(), houseDTO.getContactEmail());
        Assertions.assertEquals(TEST_HOUSE.getContactPhone(), houseDTO.getContactPhone());
        Assertions.assertEquals(TEST_HOUSE.getHouseType(), houseDTO.getHouseType());
        Assertions.assertEquals(TEST_HOUSE.getYardArea(), houseDTO.getYardArea());
        Assertions.assertEquals(TEST_HOUSE.getYardAreaUnit(), houseDTO.getYardAreaUnit());
        Assertions.assertEquals(TEST_HOUSE.getFloorsCount(), houseDTO.getFloorsCount());
        Assertions.assertEquals(TEST_HOUSE.getConstructionType(), houseDTO.getConstructionType());
        Assertions.assertEquals(TEST_HOUSE.getAdditionalStructures(), houseDTO.getAdditionalStructures());
        Assertions.assertEquals(TEST_HOUSE.getHeatingType(), houseDTO.getHeatingType());

        Assertions.assertEquals(TEST_LAND.getId(), landDTO.getId());
        Assertions.assertEquals(TEST_LAND.getCity(), landDTO.getCity());
        Assertions.assertEquals(TEST_LAND.getNeighbourhood(), landDTO.getNeighbourhood());
        Assertions.assertEquals(TEST_LAND.getAddress(), landDTO.getAddress());
        Assertions.assertEquals(TEST_LAND.getDescription(), landDTO.getDescription());
        Assertions.assertEquals(TEST_LAND.getContactName(), landDTO.getContactName());
        Assertions.assertEquals(TEST_LAND.getContactEmail(), landDTO.getContactEmail());
        Assertions.assertEquals(TEST_LAND.getContactPhone(), landDTO.getContactPhone());
        Assertions.assertEquals(TEST_LAND.getLandType(), landDTO.getLandType());
    }

    @Test
    public void testSaveNewPropertyToDB_savesApartmentCorrectly() {
        AddPropertyDTO addPropertyDTO = new AddPropertyDTO();
        addPropertyDTO.setPropertyType(PropertyType.APARTMENT);
        addPropertyDTO.setAddress("address");
        addPropertyDTO.setOfferType(OfferType.SALE);

        when(userEntityService.getCurrentUser()).thenReturn(TEST_USER);

        serviceToTest.saveNewPropertyToDB(addPropertyDTO);

        verify(basePropertyRepository).saveAndFlush(captor.capture());
        Assertions.assertEquals(captor.getValue().getClass(), Apartment.class);
        Apartment actual = (Apartment) captor.getValue();
        Apartment expected = modelMapper.map(addPropertyDTO, Apartment.class);
        Assertions.assertEquals(actual.getPropertyType(), expected.getPropertyType());
        Assertions.assertEquals(actual.getAddress(), expected.getAddress());
        Assertions.assertEquals(actual.getOfferType(), expected.getOfferType());
    }

    @Test
    public void testSavePropertyToDB_savesBusinessNewPropertyCorrectly() {
        AddPropertyDTO addPropertyDTO = new AddPropertyDTO();
        addPropertyDTO.setPropertyType(PropertyType.BUSINESS);
        addPropertyDTO.setAddress("address");
        addPropertyDTO.setOfferType(OfferType.SALE);

        when(userEntityService.getCurrentUser()).thenReturn(TEST_USER);

        serviceToTest.saveNewPropertyToDB(addPropertyDTO);

        verify(basePropertyRepository).saveAndFlush(captor.capture());
        Assertions.assertEquals(captor.getValue().getClass(), BusinessProperty.class);
        BusinessProperty actual = (BusinessProperty) captor.getValue();
        BusinessProperty expected = modelMapper.map(addPropertyDTO, BusinessProperty.class);
        Assertions.assertEquals(actual.getPropertyType(), expected.getPropertyType());
        Assertions.assertEquals(actual.getAddress(), expected.getAddress());
        Assertions.assertEquals(actual.getOfferType(), expected.getOfferType());
    }

    @Test
    public void testSaveNewPropertyToDB_savesHouseCorrectly() {
        AddPropertyDTO addPropertyDTO = new AddPropertyDTO();
        addPropertyDTO.setPropertyType(PropertyType.HOUSE);
        addPropertyDTO.setAddress("address");
        addPropertyDTO.setOfferType(OfferType.SALE);

        when(userEntityService.getCurrentUser()).thenReturn(TEST_USER);

        serviceToTest.saveNewPropertyToDB(addPropertyDTO);

        verify(basePropertyRepository).saveAndFlush(captor.capture());
        Assertions.assertEquals(captor.getValue().getClass(), House.class);
        House actual = (House) captor.getValue();
        House expected = modelMapper.map(addPropertyDTO, House.class);
        Assertions.assertEquals(actual.getPropertyType(), expected.getPropertyType());
        Assertions.assertEquals(actual.getAddress(), expected.getAddress());
        Assertions.assertEquals(actual.getOfferType(), expected.getOfferType());
    }

    @Test
    public void testSaveNewPropertyToDB_savesLandCorrectly() {
        AddPropertyDTO addPropertyDTO = new AddPropertyDTO();
        addPropertyDTO.setPropertyType(PropertyType.LAND);
        addPropertyDTO.setAddress("address");
        addPropertyDTO.setOfferType(OfferType.SALE);

        when(userEntityService.getCurrentUser()).thenReturn(TEST_USER);

        serviceToTest.saveNewPropertyToDB(addPropertyDTO);

        verify(basePropertyRepository).saveAndFlush(captor.capture());
        Assertions.assertEquals(captor.getValue().getClass(), Land.class);
        Land actual = (Land) captor.getValue();
        Land expected = modelMapper.map(addPropertyDTO, Land.class);
        Assertions.assertEquals(actual.getPropertyType(), expected.getPropertyType());
        Assertions.assertEquals(actual.getAddress(), expected.getAddress());
        Assertions.assertEquals(actual.getOfferType(), expected.getOfferType());
    }

    @Test
    public void testSaveNewPropertyToDB_savesGarageCorrectly() {
        AddPropertyDTO addPropertyDTO = new AddPropertyDTO();
        addPropertyDTO.setPropertyType(PropertyType.GARAGE);
        addPropertyDTO.setAddress("address");
        addPropertyDTO.setOfferType(OfferType.SALE);

        when(userEntityService.getCurrentUser()).thenReturn(TEST_USER);

        serviceToTest.saveNewPropertyToDB(addPropertyDTO);

        verify(basePropertyRepository).saveAndFlush(captor.capture());
        Assertions.assertEquals(captor.getValue().getClass(), Garage.class);
        Garage actual = (Garage) captor.getValue();
        Garage expected = modelMapper.map(addPropertyDTO, Garage.class);
        Assertions.assertEquals(actual.getPropertyType(), expected.getPropertyType());
        Assertions.assertEquals(actual.getAddress(), expected.getAddress());
        Assertions.assertEquals(actual.getOfferType(), expected.getOfferType());
    }

    @Test
    public void testSavePropertyToDB_throwsExceptionIfNewPropertyTypeIsNullOrNotCorrect() {
        AddPropertyDTO propertyDTO = new AddPropertyDTO();

        Assertions.assertThrows(RuntimeException.class, () -> serviceToTest.saveNewPropertyToDB(propertyDTO));
    }

    @Test
    public void testSaveNewPropertyToDB_setsCreatedOnUpdatedOnAndCompanyInDTO() {
        when(userEntityService.getCurrentUser()).thenReturn(TEST_USER);

        AddPropertyDTO addPropertyDTO = new AddPropertyDTO(PropertyType.APARTMENT, null, "test_city", "test_neighbourhood", "test_address", 70000.00, 70, AreaUnit.SQUARE_METER, OfferType.SALE, "contact_name", "+359 893 333 595", "contact@mail.com", "test_description", null, null, ConstructionType.BRICK, 2020, 3, 4, 8, "test_facing", HeatingType.GAS, ApartmentType.THREE_ROOM, true, null, null, null, null, null, null, null, null);
        serviceToTest.saveNewPropertyToDB(addPropertyDTO);

        verify(basePropertyRepository).saveAndFlush((Apartment) captor.capture());
        Apartment actual = (Apartment) captor.getValue();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(addPropertyDTO.getPropertyType(), actual.getPropertyType());
        Assertions.assertEquals(TEST_USER.getCompany().getName(), actual.getOwnerCompany().getName());
        Assertions.assertEquals(addPropertyDTO.getCity(), actual.getCity());
        Assertions.assertEquals(addPropertyDTO.getNeighbourhood(), actual.getNeighbourhood());
        Assertions.assertEquals(addPropertyDTO.getAddress(), actual.getAddress());
        Assertions.assertEquals(addPropertyDTO.getPrice(), actual.getPrice());
        Assertions.assertEquals(addPropertyDTO.getArea(), actual.getArea());
        Assertions.assertEquals(addPropertyDTO.getAreaUnit(), actual.getAreaUnit());
        Assertions.assertEquals(addPropertyDTO.getOfferType(), actual.getOfferType());
        Assertions.assertEquals(addPropertyDTO.getContactName(), actual.getContactName());
        Assertions.assertEquals(addPropertyDTO.getContactPhone(), actual.getContactPhone());
        Assertions.assertEquals(addPropertyDTO.getContactEmail(), actual.getContactEmail());
        Assertions.assertEquals(addPropertyDTO.getDescription(), actual.getDescription());
        Assertions.assertNotNull(actual.getCreatedOn());
        Assertions.assertNotNull(actual.getUpdatedOn());
        Assertions.assertEquals(addPropertyDTO.getConstructionType(), actual.getConstructionType());
        Assertions.assertEquals(addPropertyDTO.getYear(), actual.getYear());
        Assertions.assertEquals(addPropertyDTO.getRoomCount(), actual.getRoomCount());
        Assertions.assertEquals(addPropertyDTO.getFloor(), actual.getFloor());
        Assertions.assertEquals(addPropertyDTO.getBuildingFloors(), actual.getBuildingFloors());
        Assertions.assertEquals(addPropertyDTO.getFacing(), actual.getFacing());
        Assertions.assertEquals(addPropertyDTO.getApartmentType(), actual.getApartmentType());
        Assertions.assertEquals(addPropertyDTO.isHasElevator(), actual.isHasElevator());
        Assertions.assertEquals(addPropertyDTO.getHeatingType(), actual.getHeatingType());
    }

    @Test
    public void testGetPropertyById_returnsPropertyIfFound() {
        when(basePropertyRepository.findById(TEST_HOUSE_ID)).thenReturn(Optional.of(TEST_HOUSE));

        BaseProperty actual = serviceToTest.getPropertyByID(TEST_HOUSE_ID);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(TEST_HOUSE.getClass(), actual.getClass());
        Assertions.assertEquals(TEST_HOUSE.getId(), actual.getId());
    }

    @Test
    public void testGetPropertyById_throwsExceptionIfPropertyNotFound() {
        when(basePropertyRepository.findById(TEST_HOUSE_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> serviceToTest.getPropertyByID(TEST_HOUSE_ID));
    }
}
