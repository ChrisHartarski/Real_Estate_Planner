package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.BusinessProperty;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.enums.*;
import com.butikimoti.real_estate_planner.repository.BusinessPropertyRepository;
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
public class BusinessPropertyServiceImplUnitTests {
    private BusinessPropertyServiceImpl serviceToTest;
    private static final Company TEST_COMPANY = new Company("test_name", "test_company_address", "+359000000000", "test@email.com");
    private static final AddPropertyDTO TEST_BUSINESS_PROPERTY_DTO = new AddPropertyDTO(PropertyType.BUSINESS, TEST_COMPANY, "test_address", 70000.00, 70, AreaUnit.SQUARE_METER, OfferType.RENT, "contact_name", "+359000000000", "contact@mail.com", "description", LocalDateTime.now(), LocalDateTime.now(), ConstructionType.BRICK, 2000, 2, 2, 5, null, null, false, null, null, null, null, null, null, null, BusinessPropertyType.OFFICE);

    @Mock
    private BusinessPropertyRepository businessPropertyRepository;

    @Captor
    private ArgumentCaptor<BusinessProperty> captor;

    @BeforeEach
    public void setUp() {
        serviceToTest = new BusinessPropertyServiceImpl(businessPropertyRepository, new ModelMapper());
    }

    @Test
    public void testSaveBusinessProperty_savesPropertyCorrectly() {
        serviceToTest.saveBusinessProperty(TEST_BUSINESS_PROPERTY_DTO);

        verify(businessPropertyRepository).saveAndFlush(captor.capture());
        BusinessProperty actual = captor.getValue();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.getPropertyType(), TEST_BUSINESS_PROPERTY_DTO.getPropertyType());
        Assertions.assertEquals(actual.getOwnerCompany().getName(), TEST_BUSINESS_PROPERTY_DTO.getOwnerCompany().getName());
        Assertions.assertEquals(actual.getAddress(), TEST_BUSINESS_PROPERTY_DTO.getAddress());
        Assertions.assertEquals(actual.getPrice(), TEST_BUSINESS_PROPERTY_DTO.getPrice());
        Assertions.assertEquals(actual.getArea(), TEST_BUSINESS_PROPERTY_DTO.getArea());
        Assertions.assertEquals(actual.getAreaUnit(), TEST_BUSINESS_PROPERTY_DTO.getAreaUnit());
        Assertions.assertEquals(actual.getOfferType(), TEST_BUSINESS_PROPERTY_DTO.getOfferType());
        Assertions.assertEquals(actual.getContactName(), TEST_BUSINESS_PROPERTY_DTO.getContactName());
        Assertions.assertEquals(actual.getContactPhone(), TEST_BUSINESS_PROPERTY_DTO.getContactPhone());
        Assertions.assertEquals(actual.getContactEmail(), TEST_BUSINESS_PROPERTY_DTO.getContactEmail());
        Assertions.assertEquals(actual.getDescription(), TEST_BUSINESS_PROPERTY_DTO.getDescription());
        Assertions.assertEquals(actual.getCreatedOn(), TEST_BUSINESS_PROPERTY_DTO.getCreatedOn());
        Assertions.assertEquals(actual.getUpdatedOn(), TEST_BUSINESS_PROPERTY_DTO.getUpdatedOn());
        Assertions.assertEquals(actual.getConstructionType(), TEST_BUSINESS_PROPERTY_DTO.getConstructionType());
        Assertions.assertEquals(actual.getYear(), TEST_BUSINESS_PROPERTY_DTO.getYear());
        Assertions.assertEquals(actual.getRoomCount(), TEST_BUSINESS_PROPERTY_DTO.getRoomCount());
        Assertions.assertEquals(actual.getBusinessPropertyType(), TEST_BUSINESS_PROPERTY_DTO.getBusinessPropertyType());
    }
}
