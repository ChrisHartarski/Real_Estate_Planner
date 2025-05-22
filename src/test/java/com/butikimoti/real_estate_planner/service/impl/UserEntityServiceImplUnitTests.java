package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.userEntity.RegisterUserDTO;
import com.butikimoti.real_estate_planner.model.entity.Apartment;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.entity.House;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.model.enums.UserRole;
import com.butikimoti.real_estate_planner.repository.UserEntityRepository;
import com.butikimoti.real_estate_planner.service.CompanyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceImplUnitTests {
    private UserEntityServiceImpl serviceToTest;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Company TEST_COMPANY_EMPTY = new Company("Test Company", "Test Address", "+359000000000", "test@email.com");
    private static final Company TEST_COMPANY_FULL = new Company(List.of(new UserEntity(), new UserEntity()), "Test Company", "Test Address", "+359000000000", "test@email.com", List.of(new Apartment(), new House()));
    private static final UserEntity TEST_USER = new UserEntity("test@email.com", TEST_COMPANY_EMPTY, "MyStrongPassword_12", UserRole.COMPANY_ADMIN, "First name", "Last Name", "+359111111111");
    private static final RegisterUserDTO REGISTER_USER_DTO = new RegisterUserDTO(TEST_USER.getEmail(), TEST_USER.getPassword(), TEST_USER.getPassword(), TEST_USER.getCompany().getName(), TEST_USER.getFirstName(), TEST_USER.getLastName(), TEST_USER.getPhone());

    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private CompanyService companyService;

    @Captor
    private ArgumentCaptor<UserEntity> captor;

    @BeforeEach
    void setUp() {
        serviceToTest = new UserEntityServiceImpl(userEntityRepository, companyService, new ModelMapper(), passwordEncoder);
    }

    @Test
    void testUserExistsReturnsTrueIfUserExists() {
        when(userEntityRepository.existsByEmail(TEST_USER.getEmail())).thenReturn(true);

        boolean result = serviceToTest.userExists(TEST_USER.getEmail());

        Assertions.assertTrue(result);
    }

    @Test
    void testUserExistsReturnsFalseIfUserDoesNotExist() {
        when(userEntityRepository.existsByEmail(TEST_USER.getEmail())).thenReturn(false);

        boolean result = serviceToTest.userExists(TEST_USER.getEmail());

        Assertions.assertFalse(result);
    }

    @Test
    void testRegisterUserAddsUserWithCompanyAdminRoleInEmptyCompany() {
        when(userEntityRepository.existsByEmail(REGISTER_USER_DTO.getEmail())).thenReturn(false);
        when(companyService.getCompany(REGISTER_USER_DTO.getCompanyName())).thenReturn(TEST_COMPANY_EMPTY);

        serviceToTest.registerUser(REGISTER_USER_DTO);

        verify(userEntityRepository).saveAndFlush(captor.capture());
        UserEntity actual = captor.getValue();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(TEST_USER.getEmail(), actual.getEmail());
        Assertions.assertEquals(TEST_COMPANY_EMPTY.getName(), actual.getCompany().getName());
        Assertions.assertTrue(passwordEncoder.matches(TEST_USER.getPassword(), actual.getPassword()));
        Assertions.assertEquals(TEST_USER.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(TEST_USER.getLastName(), actual.getLastName());
        Assertions.assertEquals(TEST_USER.getPhone(), actual.getPhone());
        Assertions.assertEquals(UserRole.COMPANY_ADMIN, actual.getUserRole());
    }

    @Test
    void testRegisterUserAddsUserWithUserRoleInFullCompany() {
        when(userEntityRepository.existsByEmail(REGISTER_USER_DTO.getEmail())).thenReturn(false);
        when(companyService.getCompany(REGISTER_USER_DTO.getCompanyName())).thenReturn(TEST_COMPANY_FULL);

        serviceToTest.registerUser(REGISTER_USER_DTO);

        verify(userEntityRepository).saveAndFlush(captor.capture());
        UserEntity actual = captor.getValue();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(TEST_USER.getEmail(), actual.getEmail());
        Assertions.assertEquals(TEST_COMPANY_FULL.getName(), actual.getCompany().getName());
        Assertions.assertTrue(passwordEncoder.matches(TEST_USER.getPassword(), actual.getPassword()));
        Assertions.assertEquals(TEST_USER.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(TEST_USER.getLastName(), actual.getLastName());
        Assertions.assertEquals(TEST_USER.getPhone(), actual.getPhone());
        Assertions.assertEquals(UserRole.USER, actual.getUserRole());
    }

    @Test
    void testRegisterUserThrowsExceptionIfUserExists() {
        when(userEntityRepository.existsByEmail(REGISTER_USER_DTO.getEmail())).thenReturn(true);

        Assertions.assertThrows(RuntimeException.class, () -> serviceToTest.registerUser(REGISTER_USER_DTO));
    }

    @Test
    void testUserRepositoryIsEmptyReturnsTrueIfEmpty() {
        when(userEntityRepository.count()).thenReturn((long) 0);

        boolean result = serviceToTest.userRepositoryIsEmpty();

        Assertions.assertTrue(result);
    }

    @Test
    void testUserRepositoryIsEmptyReturnsFalseIfNotEmpty() {
        when(userEntityRepository.count()).thenReturn((long) 5);

        boolean result = serviceToTest.userRepositoryIsEmpty();

        Assertions.assertFalse(result);
    }

}
