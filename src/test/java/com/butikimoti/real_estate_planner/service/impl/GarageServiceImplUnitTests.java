package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.entity.Garage;
import com.butikimoti.real_estate_planner.model.enums.*;
import com.butikimoti.real_estate_planner.repository.GarageRepository;
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
public class GarageServiceImplUnitTests {
    private GarageServiceImpl serviceToTest;
    private static final Company TEST_COMPANY = new Company("test_name", "test_company_address", "+359000000000", "test@email.com");
    private static final AddPropertyDTO TEST_GARAGE_DTO = new AddPropertyDTO(PropertyType.GARAGE, TEST_COMPANY, "test_address", 25000.00, 25, AreaUnit.SQUARE_METER, OfferType.SALE, "contact_name", "+359 000 000 000", "contact@mail.com", "test_description", LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null, null, null, false, null, null, null, null, null, GarageType.GARAGE, null, null);

    @Mock
    private GarageRepository garageRepository;

    @Captor
    private ArgumentCaptor<Garage> captor;

    @BeforeEach
    public void setUp() {
        serviceToTest = new GarageServiceImpl(garageRepository, new ModelMapper());
    }

    @Test
    public void saveGarage_sendsCorrectDataToDB() {
        serviceToTest.saveGarage(TEST_GARAGE_DTO);

        verify(garageRepository).saveAndFlush(captor.capture());
        Garage actual = captor.getValue();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(TEST_GARAGE_DTO.getPropertyType(), actual.getPropertyType());
        Assertions.assertEquals(TEST_GARAGE_DTO.getOwnerCompany().getName(), actual.getOwnerCompany().getName());
        Assertions.assertEquals(TEST_GARAGE_DTO.getAddress(), actual.getAddress());
        Assertions.assertEquals(TEST_GARAGE_DTO.getPrice(), actual.getPrice());
        Assertions.assertEquals(TEST_GARAGE_DTO.getArea(), actual.getArea());
        Assertions.assertEquals(TEST_GARAGE_DTO.getAreaUnit(), actual.getAreaUnit());
        Assertions.assertEquals(TEST_GARAGE_DTO.getOfferType(), actual.getOfferType());
        Assertions.assertEquals(TEST_GARAGE_DTO.getContactName(), actual.getContactName());
        Assertions.assertEquals(TEST_GARAGE_DTO.getContactPhone(), actual.getContactPhone());
        Assertions.assertEquals(TEST_GARAGE_DTO.getContactEmail(), actual.getContactEmail());
        Assertions.assertEquals(TEST_GARAGE_DTO.getDescription(), actual.getDescription());
        Assertions.assertEquals(TEST_GARAGE_DTO.getCreatedOn(), actual.getCreatedOn());
        Assertions.assertEquals(TEST_GARAGE_DTO.getUpdatedOn(), actual.getUpdatedOn());
        Assertions.assertEquals(TEST_GARAGE_DTO.getGarageType(), actual.getGarageType());
    }
}
