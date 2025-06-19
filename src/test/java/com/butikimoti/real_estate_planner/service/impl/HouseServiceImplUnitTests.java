package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.entity.House;
import com.butikimoti.real_estate_planner.model.enums.*;
import com.butikimoti.real_estate_planner.repository.HouseRepository;
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
public class HouseServiceImplUnitTests {
    private HouseServiceImpl serviceToTest;
    private static final Company TEST_COMPANY = new Company("test_name", "test_company_address", "+359000000000", "test@email.com");
    private static final AddPropertyDTO TEST_HOUSE_DTO = new AddPropertyDTO(PropertyType.HOUSE, TEST_COMPANY, "test_city", "test_neighbourhood", "test_address", 45000.00, 120, AreaUnit.SQUARE_METER, OfferType.SALE, "contact_name", "+359000000000", "contact@mail.com", "test_description", LocalDateTime.now(), LocalDateTime.now(), ConstructionType.JOIST, 1984, 3, null, null, null, HeatingType.HARD_FUEL, null, false, HouseType.HOUSE, 500, AreaUnit.SQUARE_METER, 2, "additional_structures", null, null, null);

    @Mock
    private HouseRepository houseRepository;

    @Captor
    private ArgumentCaptor<House> captor;

    @BeforeEach
    public void setUp() {
        serviceToTest = new HouseServiceImpl(houseRepository, new ModelMapper());
    }

    @Test
    public void testSaveHouse_sendsCorrectDataToDB() {
        serviceToTest.saveHouse(TEST_HOUSE_DTO);

        verify(houseRepository).saveAndFlush(captor.capture());
        House actual = captor.getValue();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(TEST_HOUSE_DTO.getPropertyType(), actual.getPropertyType());
        Assertions.assertEquals(TEST_HOUSE_DTO.getOwnerCompany().getName(), actual.getOwnerCompany().getName());
        Assertions.assertEquals(TEST_HOUSE_DTO.getCity(), actual.getCity());
        Assertions.assertEquals(TEST_HOUSE_DTO.getNeighbourhood(), actual.getNeighbourhood());
        Assertions.assertEquals(TEST_HOUSE_DTO.getAddress(), actual.getAddress());
        Assertions.assertEquals(TEST_HOUSE_DTO.getPrice(), actual.getPrice());
        Assertions.assertEquals(TEST_HOUSE_DTO.getArea(), actual.getArea());
        Assertions.assertEquals(TEST_HOUSE_DTO.getAreaUnit(), actual.getAreaUnit());
        Assertions.assertEquals(TEST_HOUSE_DTO.getOfferType(), actual.getOfferType());
        Assertions.assertEquals(TEST_HOUSE_DTO.getContactName(), actual.getContactName());
        Assertions.assertEquals(TEST_HOUSE_DTO.getContactPhone(), actual.getContactPhone());
        Assertions.assertEquals(TEST_HOUSE_DTO.getContactEmail(), actual.getContactEmail());
        Assertions.assertEquals(TEST_HOUSE_DTO.getDescription(), actual.getDescription());
        Assertions.assertEquals(TEST_HOUSE_DTO.getCreatedOn(), actual.getCreatedOn());
        Assertions.assertEquals(TEST_HOUSE_DTO.getUpdatedOn(), actual.getUpdatedOn());
        Assertions.assertEquals(TEST_HOUSE_DTO.getConstructionType(), actual.getConstructionType());
        Assertions.assertEquals(TEST_HOUSE_DTO.getYear(), actual.getYear());
        Assertions.assertEquals(TEST_HOUSE_DTO.getFloorsCount(), actual.getFloorsCount());
        Assertions.assertEquals(TEST_HOUSE_DTO.getYardArea(), actual.getYardArea());
        Assertions.assertEquals(TEST_HOUSE_DTO.getYardAreaUnit(), actual.getYardAreaUnit());
        Assertions.assertEquals(TEST_HOUSE_DTO.getAdditionalStructures(), actual.getAdditionalStructures());
        Assertions.assertEquals(TEST_HOUSE_DTO.getHouseType(), actual.getHouseType());
        Assertions.assertEquals(TEST_HOUSE_DTO.getHeatingType(), actual.getHeatingType());
    }
}
