package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.Apartment;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.enums.*;
import com.butikimoti.real_estate_planner.repository.ApartmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ApartmentServiceImplUnitTests {
    private ApartmentServiceImpl serviceToTest;
    private static final Company TEST_COMPANY = new Company("test_name", "test_company_address", "+359000000000", "test@email.com");
    private static final AddPropertyDTO TEST_APARTMENT_DTO = new AddPropertyDTO(PropertyType.APARTMENT, TEST_COMPANY, "test_address", 70000.00, 70, AreaUnit.SQUARE_METER, OfferType.SALE, "contact_name", "+359 893 333 595", "contact@mail.com", "test_description", LocalDateTime.now(), LocalDateTime.now(), ConstructionType.BRICK, 2020, 3, 4, 8, "test_facing", ApartmentType.THREE_ROOM, true, null, null, null, null, null, null, null, null);

    @Mock
    private ApartmentRepository apartmentRepository;

    @Captor
    private ArgumentCaptor<Apartment> captor;

    @BeforeEach
    public void setUp() {
        serviceToTest = new ApartmentServiceImpl(apartmentRepository, new ModelMapper());
    }

    @Test
    public void testSaveApartment_savesApartmentCorrectly() {
        serviceToTest.saveApartment(TEST_APARTMENT_DTO);

        verify(apartmentRepository).saveAndFlush(captor.capture());
        Apartment actual = captor.getValue();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(TEST_APARTMENT_DTO.getPropertyType(), actual.getPropertyType());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getOwnerCompany().getName(), actual.getOwnerCompany().getName());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getAddress(), actual.getAddress());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getPrice(), actual.getPrice());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getArea(), actual.getArea());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getAreaUnit(), actual.getAreaUnit());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getOfferType(), actual.getOfferType());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getContactName(), actual.getContactName());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getContactPhone(), actual.getContactPhone());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getContactEmail(), actual.getContactEmail());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getDescription(), actual.getDescription());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getCreatedOn(), actual.getCreatedOn());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getUpdatedOn(), actual.getUpdatedOn());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getConstructionType(), actual.getConstructionType());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getYear(), actual.getYear());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getRoomCount(), actual.getRoomCount());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getFloor(), actual.getFloor());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getBuildingFloors(), actual.getBuildingFloors());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getFacing(), actual.getFacing());
        Assertions.assertEquals(TEST_APARTMENT_DTO.getApartmentType(), actual.getApartmentType());
        Assertions.assertEquals(TEST_APARTMENT_DTO.isHasElevator(), actual.isHasElevator());
    }
}
