package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.userEntity.UserDTO;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceImplUnitTests {
    private UserEntityServiceImpl serviceToTest;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Company TEST_COMPANY_EMPTY = new Company("Test Company", "Test Address", "+359000000000", "test@email.com");
    private static final Company TEST_COMPANY_FULL = new Company(List.of(new UserEntity(), new UserEntity()), "Test Company", "Test Address", "+359000000000", "test@email.com", List.of(new Apartment(), new House()));
    private static final UserEntity TEST_USER = new UserEntity("test@email.com", TEST_COMPANY_EMPTY, "MyStrongPassword_12", UserRole.USER, "First name", "Last Name", "+359111111111");
    private static final UserEntity ADMIN_USER = new UserEntity("test@email.com", TEST_COMPANY_EMPTY, "MyStrongPassword_12", UserRole.ADMIN, "First name", "Last Name", "+359111111111");
    private static final UserDTO REGISTER_USER_DTO = new UserDTO(TEST_USER.getEmail(), TEST_USER.getPassword(), TEST_USER.getPassword(), TEST_USER.getCompany().getName(), TEST_USER.getFirstName(), TEST_USER.getLastName(), TEST_USER.getPhone());
    private static final UserDTO REGISTER_USER_DTO_ADMIN = new UserDTO(TEST_USER.getEmail(), TEST_USER.getPassword(), TEST_USER.getPassword(), TEST_USER.getCompany().getName(), TEST_USER.getFirstName(), TEST_USER.getLastName(), TEST_USER.getPhone(), UserRole.ADMIN);
    private static final UserDTO REGISTER_USER_DTO_COMPANY_ADMIN = new UserDTO(TEST_USER.getEmail(), TEST_USER.getPassword(), TEST_USER.getPassword(), TEST_USER.getCompany().getName(), TEST_USER.getFirstName(), TEST_USER.getLastName(), TEST_USER.getPhone(), UserRole.COMPANY_ADMIN);

    @Mock
    private UserEntityRepository userEntityRepository;

    @Captor
    private ArgumentCaptor<UserEntity> captor;

    @BeforeEach
    void setUp() {
        serviceToTest = new UserEntityServiceImpl(userEntityRepository, new ModelMapper(), passwordEncoder);
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
        createSecurityContextWithAdminUser();
        when(userEntityRepository.findByEmail(ADMIN_USER.getEmail())).thenReturn(Optional.of(ADMIN_USER));

        when(userEntityRepository.existsByEmail(REGISTER_USER_DTO.getEmail())).thenReturn(false);

        serviceToTest.registerUser(REGISTER_USER_DTO, TEST_COMPANY_EMPTY);

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
        createSecurityContextWithAdminUser();
        when(userEntityRepository.findByEmail(ADMIN_USER.getEmail())).thenReturn(Optional.of(ADMIN_USER));

        when(userEntityRepository.existsByEmail(REGISTER_USER_DTO.getEmail())).thenReturn(false);

        serviceToTest.registerUser(REGISTER_USER_DTO, TEST_COMPANY_FULL);

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
        createSecurityContextWithAdminUser();
        when(userEntityRepository.findByEmail(ADMIN_USER.getEmail())).thenReturn(Optional.of(ADMIN_USER));

        when(userEntityRepository.existsByEmail(REGISTER_USER_DTO.getEmail())).thenReturn(true);

        Assertions.assertThrows(RuntimeException.class, () -> serviceToTest.registerUser(REGISTER_USER_DTO, TEST_COMPANY_EMPTY));
    }

    @Test
    void testRegisterUserAddsUserWithAdminRoleWhenUserRoleInArguments() {
        createSecurityContextWithAdminUser();
        when(userEntityRepository.findByEmail(ADMIN_USER.getEmail())).thenReturn(Optional.of(ADMIN_USER));

        when(userEntityRepository.existsByEmail(REGISTER_USER_DTO.getEmail())).thenReturn(false);

        serviceToTest.registerUser(REGISTER_USER_DTO_ADMIN, TEST_COMPANY_FULL);

        verify(userEntityRepository).saveAndFlush(captor.capture());
        UserEntity actual = captor.getValue();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(TEST_USER.getEmail(), actual.getEmail());
        Assertions.assertEquals(TEST_COMPANY_EMPTY.getName(), actual.getCompany().getName());
        Assertions.assertTrue(passwordEncoder.matches(TEST_USER.getPassword(), actual.getPassword()));
        Assertions.assertEquals(TEST_USER.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(TEST_USER.getLastName(), actual.getLastName());
        Assertions.assertEquals(TEST_USER.getPhone(), actual.getPhone());
        Assertions.assertEquals(UserRole.ADMIN, actual.getUserRole());
    }

    @Test
    void testRegisterUserAddsUserWithCompanyAdminRoleWhenUserRoleInArguments() {
        createSecurityContextWithAdminUser();
        when(userEntityRepository.findByEmail(ADMIN_USER.getEmail())).thenReturn(Optional.of(ADMIN_USER));

        when(userEntityRepository.existsByEmail(REGISTER_USER_DTO.getEmail())).thenReturn(false);

        serviceToTest.registerUser(REGISTER_USER_DTO_COMPANY_ADMIN, TEST_COMPANY_FULL);

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
    void testRegisterUserDoesNotRegisterUserWhenCurrentUserNotAdmin() {
        createSecurityContextWithUserRole();
        when(userEntityRepository.findByEmail(TEST_USER.getEmail())).thenReturn(Optional.of(TEST_USER));

        serviceToTest.registerUser(REGISTER_USER_DTO, TEST_COMPANY_FULL);
        verify(userEntityRepository, never()).saveAndFlush(any());
    }

    private void createSecurityContextWithAdminUser() {
        UserDetails userDetails = User
                .withUsername(ADMIN_USER.getEmail())
                .password(ADMIN_USER.getPassword())
                .roles("ADMIN")
                .build();

        createSecurityContext(userDetails);
    }

    private void createSecurityContextWithUserRole() {
        UserDetails userDetails = User
                .withUsername(TEST_USER.getEmail())
                .password(TEST_USER.getPassword())
                .roles("USER")
                .build();

        createSecurityContext(userDetails);
    }

    private void createSecurityContext(UserDetails userDetails) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}
