package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.company.CompanyDTO;
import com.butikimoti.real_estate_planner.model.entity.Apartment;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.entity.House;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.repository.CompanyRepository;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceImplUnitTests {
    private CompanyServiceImpl serviceToTest;
    private static final Company TEST_COMPANY = new Company("Test Company", "Test Address", "+359000000000", "test@email.com");
    private static final Company TEST_COMPANY_WITH_USERS_AND_PROPERTIES = new Company(List.of(new UserEntity(), new UserEntity()), "Test Company", "Test Address", "+359000000000", "test@email.com", List.of(new Apartment(), new House()));
    private static final CompanyDTO REGISTER_COMPANY_DTO = new CompanyDTO(TEST_COMPANY.getName(), TEST_COMPANY.getAddress(), TEST_COMPANY.getPhone(), TEST_COMPANY.getEmail());

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private UserEntityService userEntityService;

    @Captor
    private ArgumentCaptor<Company> companyCaptor;

    @BeforeEach
    void setUp() {
        serviceToTest = new CompanyServiceImpl(companyRepository, userEntityService, new ModelMapper());
    }

    @Test
    void testCompanyExistsReturnsTrueIfCompanyExists() {
        when(companyRepository.existsByName(TEST_COMPANY.getName())).thenReturn(true);

        boolean result = serviceToTest.companyExists(TEST_COMPANY.getName());

        Assertions.assertTrue(result);
    }

    @Test
    void testCompanyExistsReturnsFalseIfCompanyDoesNotExist() {
        when(companyRepository.existsByName(TEST_COMPANY.getName())).thenReturn(false);

        boolean result = serviceToTest.companyExists(TEST_COMPANY.getName());

        Assertions.assertFalse(result);
    }

    @Test
    void testRegisterCompanySendsCorrectDataToDB() {
        when(companyRepository.existsByName(REGISTER_COMPANY_DTO.getName())).thenReturn(false);

        serviceToTest.registerCompany(REGISTER_COMPANY_DTO);

        verify(companyRepository).saveAndFlush(companyCaptor.capture());
        Company actual = companyCaptor.getValue();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(TEST_COMPANY.getName(), actual.getName());
        Assertions.assertEquals(TEST_COMPANY.getAddress(), actual.getAddress());
        Assertions.assertEquals(TEST_COMPANY.getPhone(), actual.getPhone());
        Assertions.assertEquals(TEST_COMPANY.getEmail(), actual.getEmail());
        Assertions.assertEquals(TEST_COMPANY.getProperties(), actual.getProperties());
        Assertions.assertEquals(TEST_COMPANY.getUsers(), actual.getUsers());
    }

    @Test
    void testRegisterCompanyThrowsErrorIfCompanyExists() {
        when(companyRepository.existsByName(REGISTER_COMPANY_DTO.getName())).thenReturn(true);

        Assertions.assertThrows(RuntimeException.class, () -> serviceToTest.registerCompany(REGISTER_COMPANY_DTO));
    }

    @Test
    void testCompanyHasUsersReturnsTrueIfCompanyHasUsers() {
        when(companyRepository.existsByName(TEST_COMPANY_WITH_USERS_AND_PROPERTIES.getName())).thenReturn(true);
        when(companyRepository.findByName(TEST_COMPANY_WITH_USERS_AND_PROPERTIES.getName())).thenReturn(TEST_COMPANY_WITH_USERS_AND_PROPERTIES);

        boolean result = serviceToTest.companyHasUsers(TEST_COMPANY_WITH_USERS_AND_PROPERTIES.getName());

        Assertions.assertTrue(result);
    }

    @Test
    void testCompanyHasUsersReturnsFalseIfCompanyDoesNotHaveUsers() {
        when(companyRepository.existsByName(TEST_COMPANY.getName())).thenReturn(true);
        when(companyRepository.findByName(TEST_COMPANY.getName())).thenReturn(TEST_COMPANY);

        boolean result = serviceToTest.companyHasUsers(TEST_COMPANY.getName());

        Assertions.assertFalse(result);
    }

    @Test
    void testCompanyHasUsersReturnsFalseIfCompanyDoesNotExist() {
        when(companyRepository.existsByName(TEST_COMPANY.getName())).thenReturn(false);

        boolean result = serviceToTest.companyHasUsers(TEST_COMPANY.getName());

        Assertions.assertFalse(result);
    }

    @Test
    void testGetCompanyReturnsCorrectCompany() {
        when(companyRepository.findByName(TEST_COMPANY.getName())).thenReturn(TEST_COMPANY);

        Company actual = serviceToTest.getCompany(TEST_COMPANY.getName());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(TEST_COMPANY.getName(), actual.getName());
        Assertions.assertEquals(TEST_COMPANY.getAddress(), actual.getAddress());
        Assertions.assertEquals(TEST_COMPANY.getPhone(), actual.getPhone());
        Assertions.assertEquals(TEST_COMPANY.getEmail(), actual.getEmail());
        Assertions.assertEquals(TEST_COMPANY.getProperties(), actual.getProperties());
        Assertions.assertEquals(TEST_COMPANY.getUsers(), actual.getUsers());
    }

}
