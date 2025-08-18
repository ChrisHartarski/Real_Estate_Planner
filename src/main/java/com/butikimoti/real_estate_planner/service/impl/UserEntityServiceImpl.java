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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserEntityServiceImpl implements UserEntityService {
    private final UserEntityRepository userEntityRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

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
        if (userExists(userDTO.getEmail())) {
            throw new RuntimeException("User with email " + userDTO.getEmail() + " already exists");
        }

        UserEntity user = modelMapper.map(userDTO, UserEntity.class);

        user.setCompany(company);
        setUserRole(user);
        user.setRegisteredOn(LocalDateTime.now());

        encodePassAndSaveUser(user);
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
}
