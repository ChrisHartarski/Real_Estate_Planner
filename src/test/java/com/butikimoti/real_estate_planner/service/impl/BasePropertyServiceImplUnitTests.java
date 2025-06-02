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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasePropertyServiceImplUnitTests {
    private BasePropertyService serviceToTest;
    private static final UUID TEST_COMPANY_ID = UUID.randomUUID();
    private static final UUID TEST_USER_ID = UUID.randomUUID();
    private static final UUID TEST_APARTMENT_ID = UUID.randomUUID();
    private static final UUID TEST_HOUSE_ID = UUID.randomUUID();
    private static final UUID TEST_LAND_ID = UUID.randomUUID();
    private static final Company TEST_COMPANY = new Company(TEST_COMPANY_ID, new ArrayList<>(), "Test Company", "Test Address", "+359000000000", "test@email.com", new ArrayList<>());
    private static final UserEntity TEST_USER = new UserEntity(TEST_USER_ID, "user@mail.com", TEST_COMPANY, "password", UserRole.COMPANY_ADMIN, "name", "last_name", "+359 000 000 000");
    private static final Apartment TEST_APARTMENT = new Apartment(TEST_APARTMENT_ID, PropertyType.APARTMENT, TEST_COMPANY, "test_address", 70000.00, 70, AreaUnit.SQUARE_METER, OfferType.SALE, "contact_name", "+359 000 000 000", "contact@mail.com", "description", LocalDateTime.now(), LocalDateTime.now(), ApartmentType.THREE_ROOM, 3, ConstructionType.BRICK, 2000, 3, 8, true, "facing");
    private static final House TEST_HOUSE = new House(TEST_HOUSE_ID, PropertyType.HOUSE, TEST_COMPANY, "test_address", 45000.00, 120, AreaUnit.SQUARE_METER, OfferType.SALE, "contact_name_2", "+359 111 111 111", "contact2@mail.com", "description2", LocalDateTime.now(), LocalDateTime.now(), HouseType.HOUSE, ConstructionType.JOIST, 1982, 500, AreaUnit.SQUARE_METER, 2, "additional_structures");
    private static final Land TEST_LAND = new Land(TEST_LAND_ID, PropertyType.LAND, TEST_COMPANY, "test_address3", 120000.00, 100, AreaUnit.DECARE, OfferType.SALE, "contact_name_3", "+359 333 333 333", "contact3@mail.com", "description3", LocalDateTime.now(), LocalDateTime.now(), LandType.ARABLE_LAND);

    @Mock
    private BasePropertyRepository basePropertyRepository;

    @Mock
    private UserEntityService userEntityService;

    @Mock
    private ApartmentService apartmentService;

    @Mock
    private BusinessPropertyService businessPropertyService;

    @Mock
    private GarageService garageService;

    @Mock
    private HouseService houseService;

    @Mock
    private LandService landService;

    @Captor
    private ArgumentCaptor<AddPropertyDTO> captor;

    @BeforeEach
    void setUp() {
        serviceToTest = new BasePropertyServiceImpl(basePropertyRepository, userEntityService, apartmentService, businessPropertyService, garageService, houseService, landService, new ModelMapper());
    }

    @Test
    public void testGetAllPropertiesByCompany_returnsCorrectData() {
        List<BaseProperty> properties = List.of(TEST_APARTMENT, TEST_HOUSE, TEST_LAND);
        Pageable pageable = PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "updatedOn"));

        when(userEntityService.getCurrentUser()).thenReturn(TEST_USER);
        when(basePropertyRepository.findByOwnerCompanyIdAndOfferType(TEST_COMPANY.getId(), pageable, OfferType.SALE)).thenReturn(new PageImpl<>(properties, pageable, 20));

        PagedModel<PropertyDTO> result = serviceToTest.getAllPropertiesByCompany(pageable, OfferType.SALE);
        List<PropertyDTO> resultList = result.getContent();

        PropertyDTO apartmentDTO = resultList.stream().filter(propertyDTO -> propertyDTO.getPropertyType() == PropertyType.APARTMENT).findFirst().orElse(null);
        PropertyDTO houseDTO = resultList.stream().filter(propertyDTO -> propertyDTO.getPropertyType() == PropertyType.HOUSE).findFirst().orElse(null);
        PropertyDTO landDTO = resultList.stream().filter(propertyDTO -> propertyDTO.getPropertyType() == PropertyType.LAND).findFirst().orElse(null);

        Assertions.assertNotNull(apartmentDTO);
        Assertions.assertNotNull(houseDTO);
        Assertions.assertNotNull(landDTO);

        Assertions.assertEquals(TEST_APARTMENT.getId(), apartmentDTO.getId());
        Assertions.assertEquals(TEST_APARTMENT.getApartmentType(), apartmentDTO.getApartmentType());
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

        Assertions.assertEquals(TEST_HOUSE.getId(), houseDTO.getId());
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

        Assertions.assertEquals(TEST_LAND.getId(), landDTO.getId());
        Assertions.assertEquals(TEST_LAND.getAddress(), landDTO.getAddress());
        Assertions.assertEquals(TEST_LAND.getDescription(), landDTO.getDescription());
        Assertions.assertEquals(TEST_LAND.getContactName(), landDTO.getContactName());
        Assertions.assertEquals(TEST_LAND.getContactEmail(), landDTO.getContactEmail());
        Assertions.assertEquals(TEST_LAND.getContactPhone(), landDTO.getContactPhone());
        Assertions.assertEquals(TEST_LAND.getLandType(), landDTO.getLandType());
    }

    @Test
    public void testSavePropertyToDB_apartmentInvokesMethodInApartmentService() {
        AddPropertyDTO propertyDTO = new AddPropertyDTO();
        propertyDTO.setPropertyType(PropertyType.APARTMENT);

        when(userEntityService.getCurrentUser()).thenReturn(TEST_USER);

        serviceToTest.savePropertyToDB(propertyDTO);

        verify(apartmentService).saveApartment(propertyDTO);
        verify(businessPropertyService, never()).saveBusinessProperty(propertyDTO);
        verify(houseService, never()).saveHouse(propertyDTO);
        verify(landService, never()).saveLand(propertyDTO);
        verify(garageService, never()).saveGarage(propertyDTO);
    }

    @Test
    public void testSavePropertyToDB_businessPropertyInvokesMethodInBusinessPropertyService() {
        AddPropertyDTO propertyDTO = new AddPropertyDTO();
        propertyDTO.setPropertyType(PropertyType.BUSINESS);

        when(userEntityService.getCurrentUser()).thenReturn(TEST_USER);

        serviceToTest.savePropertyToDB(propertyDTO);

        verify(apartmentService, never()).saveApartment(propertyDTO);
        verify(businessPropertyService).saveBusinessProperty(propertyDTO);
        verify(houseService, never()).saveHouse(propertyDTO);
        verify(landService, never()).saveLand(propertyDTO);
        verify(garageService, never()).saveGarage(propertyDTO);
    }

    @Test
    public void testSavePropertyToDB_houseInvokesMethodInHouseService() {
        AddPropertyDTO propertyDTO = new AddPropertyDTO();
        propertyDTO.setPropertyType(PropertyType.HOUSE);

        when(userEntityService.getCurrentUser()).thenReturn(TEST_USER);

        serviceToTest.savePropertyToDB(propertyDTO);

        verify(apartmentService, never()).saveApartment(propertyDTO);
        verify(businessPropertyService, never()).saveBusinessProperty(propertyDTO);
        verify(houseService).saveHouse(propertyDTO);
        verify(landService, never()).saveLand(propertyDTO);
        verify(garageService, never()).saveGarage(propertyDTO);
    }

    @Test
    public void testSavePropertyToDB_landInvokesMethodInLandService() {
        AddPropertyDTO propertyDTO = new AddPropertyDTO();
        propertyDTO.setPropertyType(PropertyType.LAND);

        when(userEntityService.getCurrentUser()).thenReturn(TEST_USER);

        serviceToTest.savePropertyToDB(propertyDTO);

        verify(apartmentService, never()).saveApartment(propertyDTO);
        verify(businessPropertyService, never()).saveBusinessProperty(propertyDTO);
        verify(houseService, never()).saveHouse(propertyDTO);
        verify(landService).saveLand(propertyDTO);
        verify(garageService, never()).saveGarage(propertyDTO);
    }

    @Test
    public void testSavePropertyToDB_garageInvokesMethodInGarageService() {
        AddPropertyDTO propertyDTO = new AddPropertyDTO();
        propertyDTO.setPropertyType(PropertyType.GARAGE);

        when(userEntityService.getCurrentUser()).thenReturn(TEST_USER);

        serviceToTest.savePropertyToDB(propertyDTO);

        verify(apartmentService, never()).saveApartment(propertyDTO);
        verify(businessPropertyService, never()).saveBusinessProperty(propertyDTO);
        verify(houseService, never()).saveHouse(propertyDTO);
        verify(landService, never()).saveLand(propertyDTO);
        verify(garageService).saveGarage(propertyDTO);
    }

    @Test
    public void testSavePropertyToDB_throwsExceptionIfPropertyTypeIsNullOrNotCorrect() {
        AddPropertyDTO propertyDTO = new AddPropertyDTO();

        when(userEntityService.getCurrentUser()).thenReturn(TEST_USER);

        Assertions.assertThrows(RuntimeException.class, () -> serviceToTest.savePropertyToDB(propertyDTO));
    }

    @Test
    public void testSavePropertyToDB_setsCreatedOnUpdatedOnAndCompanyInDTO() {
        AddPropertyDTO propertyDTO = new AddPropertyDTO(PropertyType.APARTMENT, null, "test_address", 70000.00, 70, AreaUnit.SQUARE_METER, OfferType.SALE, "contact_name", "+359 893 333 595", "contact@mail.com", "test_description", null, null, ConstructionType.BRICK, 2020, 3, 4, 8, "test_facing", ApartmentType.THREE_ROOM, true, null, null, null, null, null, null, null, null);

        when(userEntityService.getCurrentUser()).thenReturn(TEST_USER);

        serviceToTest.savePropertyToDB(propertyDTO);

        verify(apartmentService).saveApartment(captor.capture());
        AddPropertyDTO actual = captor.getValue();

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(propertyDTO.getPropertyType(), actual.getPropertyType());
        Assertions.assertEquals(TEST_USER.getCompany().getName(), actual.getOwnerCompany().getName());
        Assertions.assertEquals(propertyDTO.getAddress(), actual.getAddress());
        Assertions.assertEquals(propertyDTO.getPrice(), actual.getPrice());
        Assertions.assertEquals(propertyDTO.getArea(), actual.getArea());
        Assertions.assertEquals(propertyDTO.getAreaUnit(), actual.getAreaUnit());
        Assertions.assertEquals(propertyDTO.getOfferType(), actual.getOfferType());
        Assertions.assertEquals(propertyDTO.getContactName(), actual.getContactName());
        Assertions.assertEquals(propertyDTO.getContactPhone(), actual.getContactPhone());
        Assertions.assertEquals(propertyDTO.getContactEmail(), actual.getContactEmail());
        Assertions.assertEquals(propertyDTO.getDescription(), actual.getDescription());
        Assertions.assertNotNull(actual.getCreatedOn());
        Assertions.assertNotNull(actual.getUpdatedOn());
        Assertions.assertEquals(propertyDTO.getConstructionType(), actual.getConstructionType());
        Assertions.assertEquals(propertyDTO.getYear(), actual.getYear());
        Assertions.assertEquals(propertyDTO.getRoomCount(), actual.getRoomCount());
        Assertions.assertEquals(propertyDTO.getFloor(), actual.getFloor());
        Assertions.assertEquals(propertyDTO.getBuildingFloors(), actual.getBuildingFloors());
        Assertions.assertEquals(propertyDTO.getFacing(), actual.getFacing());
        Assertions.assertEquals(propertyDTO.getApartmentType(), actual.getApartmentType());
        Assertions.assertEquals(propertyDTO.isHasElevator(), actual.isHasElevator());
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
