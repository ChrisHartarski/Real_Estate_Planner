package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.entity.Land;
import com.butikimoti.real_estate_planner.model.enums.*;
import com.butikimoti.real_estate_planner.repository.LandRepository;
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
public class LandServiceImplUnitTests {
    private LandServiceImpl serviceToTest;
    private static final Company TEST_COMPANY = new Company("test_name", "test_company_address", "+359000000000", "test@email.com");
    private static final AddPropertyDTO TEST_LAND_DTO = new AddPropertyDTO(PropertyType.LAND, TEST_COMPANY, "test_address", 150000.00, 100, AreaUnit.DECARE, OfferType.SALE, "contact_name", "+359 000 000 000", "contact@mail.com", "test_description", LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, null, null, null, false, null, null, null, null, null, null, LandType.ARABLE_LAND, null);

    @Mock
    private LandRepository landRepository;

    @Captor
    private ArgumentCaptor<Land> captor;

    @BeforeEach
    public void setUp() {
        serviceToTest = new LandServiceImpl(landRepository, new ModelMapper());
    }

    @Test
    public void testSaveLand_sendsCorrectDataToDB() {
        serviceToTest.saveLand(TEST_LAND_DTO);

        verify(landRepository).saveAndFlush(captor.capture());
        Land actual = captor.getValue();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(TEST_LAND_DTO.getPropertyType(), actual.getPropertyType());
        Assertions.assertEquals(TEST_LAND_DTO.getOwnerCompany().getName(), actual.getOwnerCompany().getName());
        Assertions.assertEquals(TEST_LAND_DTO.getAddress(), actual.getAddress());
        Assertions.assertEquals(TEST_LAND_DTO.getPrice(), actual.getPrice());
        Assertions.assertEquals(TEST_LAND_DTO.getArea(), actual.getArea());
        Assertions.assertEquals(TEST_LAND_DTO.getAreaUnit(), actual.getAreaUnit());
        Assertions.assertEquals(TEST_LAND_DTO.getOfferType(), actual.getOfferType());
        Assertions.assertEquals(TEST_LAND_DTO.getContactName(), actual.getContactName());
        Assertions.assertEquals(TEST_LAND_DTO.getContactPhone(), actual.getContactPhone());
        Assertions.assertEquals(TEST_LAND_DTO.getContactEmail(), actual.getContactEmail());
        Assertions.assertEquals(TEST_LAND_DTO.getDescription(), actual.getDescription());
        Assertions.assertEquals(TEST_LAND_DTO.getCreatedOn(), actual.getCreatedOn());
        Assertions.assertEquals(TEST_LAND_DTO.getUpdatedOn(), actual.getUpdatedOn());
        Assertions.assertEquals(TEST_LAND_DTO.getLandType(), actual.getLandType());
    }
}
