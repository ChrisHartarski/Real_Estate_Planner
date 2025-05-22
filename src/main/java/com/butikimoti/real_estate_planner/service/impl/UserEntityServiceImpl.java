package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.userEntity.RegisterUserDTO;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.model.enums.UserRole;
import com.butikimoti.real_estate_planner.repository.UserEntityRepository;
import com.butikimoti.real_estate_planner.service.CompanyService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserEntityServiceImpl implements UserEntityService {
    private final UserEntityRepository userEntityRepository;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserEntityServiceImpl(UserEntityRepository userEntityRepository, CompanyService companyService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.companyService = companyService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean userExists(String email) {
        return userEntityRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void registerUser(RegisterUserDTO registerUserDTO) {
        if (userExists(registerUserDTO.getEmail())) {
            throw new RuntimeException("User with email " + registerUserDTO.getEmail() + " already exists");
        }

        UserEntity user = modelMapper.map(registerUserDTO, UserEntity.class);

        setCompany(user, registerUserDTO.getCompanyName());
        setUserRole(user);

        encodePassAndSaveUser(user);
    }

    @Override
    public UserEntity getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userEntityRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("No active user"));
    }

    private void encodePassAndSaveUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntityRepository.saveAndFlush(user);
    }

    private void setCompany(UserEntity user, String companyName) {
        user.setCompany(companyService.getCompany(companyName));
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
