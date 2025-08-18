package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.userEntity.UserDTO;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.model.enums.UserRole;
import com.butikimoti.real_estate_planner.repository.UserEntityRepository;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import com.butikimoti.real_estate_planner.specifications.UserEntitySpecifications;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserEntityServiceImpl implements UserEntityService {
    private final UserEntityRepository userEntityRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private static final UserDTO FIRST_ADMIN_USER = new UserDTO(System.getenv("ADMIN_USER_EMAIL"), System.getenv("ADMIN_USER_PASS"), System.getenv("ADMIN_USER_PASS"), System.getenv("ADMIN_COMPANY_NAME"), System.getenv("ADMIN_FIRST_NAME"), System.getenv("ADMIN_LAST_NAME"), System.getenv("ADMIN_PHONE"), UserRole.ADMIN);
    private static final UserDTO TEST_USER_1 = new UserDTO(System.getenv("TEST_USER1_EMAIL"), System.getenv("TEST_USER1_PASS"), System.getenv("TEST_USER1_PASS"), System.getenv("TEST_COMPANY_NAME"), System.getenv("TEST_USER1_FIRST_NAME"), System.getenv("TEST_USER1_LAST_NAME"), System.getenv("TEST_USER1_PHONE"), UserRole.COMPANY_ADMIN);
    private static final UserDTO TEST_USER_2 = new UserDTO(System.getenv("TEST_USER2_EMAIL"), System.getenv("TEST_USER2_PASS"), System.getenv("TEST_USER2_PASS"), System.getenv("TEST_COMPANY_NAME"), System.getenv("TEST_USER2_FIRST_NAME"), System.getenv("TEST_USER2_LAST_NAME"), System.getenv("TEST_USER2_PHONE"), UserRole.USER);


    public UserEntityServiceImpl(UserEntityRepository userEntityRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean userExists(String email) {
        return userEntityRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void registerUser(UserDTO userDTO, Company company) {
        if (!getCurrentUser().getUserRole().equals(UserRole.ADMIN)) {
            return;
        }

        registerNewUser(userDTO, company);
    }

    @Override
    public void registerInitialAdminUser(Company company) {
        if (!userExists(FIRST_ADMIN_USER.getEmail())) {
            registerNewUser(FIRST_ADMIN_USER, company);
        }

    }

    @Override
    public void registerInitialTestUsers(Company company) {
        if (!userExists(TEST_USER_1.getEmail())) {
            registerNewUser(TEST_USER_1, company);
        }

        if (!userExists(TEST_USER_2.getEmail())) {
            registerNewUser(TEST_USER_2, company);
        }
    }

    @Override
    public UserEntity getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userEntityRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("No active user"));
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable, String userFirstLastName, String userEmail, String userCompanyName, UserRole userRole) {
        UserEntity user = getCurrentUser();
        if (user == null) {
            throw new RuntimeException("No active user");
        }
        if (user.getUserRole() != UserRole.ADMIN) {
            throw new RuntimeException("User is not admin");
        }

        Specification<UserEntity> specification = UserEntitySpecifications.usersPageFilters(userFirstLastName, userEmail, userCompanyName, userRole);
        Page<UserEntity> users = userEntityRepository.findAll(specification, pageable);

        return users.map(userEntity -> modelMapper.map(userEntity, UserDTO.class));
    }

    @Override
    public UserEntity getUser(UUID id) {
        UserEntity currentUser = getCurrentUser();
        if (!currentUser.getId().equals(id) || currentUser.getUserRole() != UserRole.ADMIN) {
            throw new RuntimeException("Unauthorized user");
        }

        return userEntityRepository.findById(id).orElseThrow(() -> new RuntimeException("No such user"));
    }

    @Override
    public UserDTO getUserDTO(UUID id) {

        return modelMapper.map(getUser(id), UserDTO.class);
    }

    @Override
    public void deleteUser(UUID id) {
        UserEntity currentUser = getCurrentUser();
        if (currentUser.getUserRole() != UserRole.ADMIN) {
            throw new RuntimeException("Current user not admin");
        }

        userEntityRepository.deleteById(id);
    }

    @Override
    public UserEntity editAndSaveUserToDB(UserDTO userDTO) {
        UserEntity currentUser = getCurrentUser();
        if (currentUser.getUserRole() != UserRole.ADMIN) {
            throw new RuntimeException("Current user not admin");
        }

        UserEntity user = getUser(userDTO.getId());
        applyEditUserDTOToUser(user, userDTO);

        return userEntityRepository.saveAndFlush(user);
    }

    private void encodePassAndSaveUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntityRepository.saveAndFlush(user);
    }

    private void setUserRole(UserEntity user) {
        if (user.getUserRole() == UserRole.ADMIN) {
            return;
        }

        if (user.getUserRole() == UserRole.COMPANY_ADMIN) {
            return;
        }

        if (user.getCompany().getUsers().isEmpty()) {
            user.setUserRole(UserRole.COMPANY_ADMIN);
        } else {
            user.setUserRole(UserRole.USER);
        }
    }

    private void registerNewUser(UserDTO userDTO, Company company) {
        if (userExists(userDTO.getEmail())) {
            throw new RuntimeException("User with email " + userDTO.getEmail() + " already exists");
        }

        UserEntity user = modelMapper.map(userDTO, UserEntity.class);

        user.setCompany(company);
        setUserRole(user);
        user.setRegisteredOn(LocalDateTime.now());

        encodePassAndSaveUser(user);
    }

    private void applyEditUserDTOToUser(UserEntity user, UserDTO userDTO) {
        Configuration configuration = modelMapper.getConfiguration();
        boolean isSkipNullEnabled = configuration.isSkipNullEnabled();

        try {
            configuration.setSkipNullEnabled(true);
            modelMapper.map(userDTO, user);
        } finally {
            configuration.setSkipNullEnabled(isSkipNullEnabled);
        }
    }
}
