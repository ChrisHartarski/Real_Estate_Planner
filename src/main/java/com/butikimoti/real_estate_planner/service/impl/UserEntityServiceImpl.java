package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.userEntity.RegisterUserDTO;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.model.enums.UserRole;
import com.butikimoti.real_estate_planner.repository.UserEntityRepository;
import com.butikimoti.real_estate_planner.service.CompanyService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import org.modelmapper.ModelMapper;
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
    public boolean userRepositoryIsEmpty() {
        return userEntityRepository.count() == 0;
    }

    private void encodePassAndSaveUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntityRepository.saveAndFlush(user);
    }

    private void setCompany(UserEntity user, String companyName) {
        user.setCompany(companyService.getCompany(companyName));
    }

    private void setUserRole(UserEntity user) {
        if (user.getCompany().getUsers().isEmpty()) {
            user.setUserRole(UserRole.COMPANY_ADMIN);
        } else {
            user.setUserRole(UserRole.USER);
        }
    }
}
