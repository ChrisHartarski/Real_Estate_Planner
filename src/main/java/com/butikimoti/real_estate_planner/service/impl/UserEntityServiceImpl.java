package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.userEntity.RegisterUserDTO;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.repository.UserEntityRepository;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserEntityServiceImpl implements UserEntityService {
    private final UserEntityRepository userEntityRepository;
    private final ModelMapper modelMapper;

    public UserEntityServiceImpl(UserEntityRepository userEntityRepository, ModelMapper modelMapper) {
        this.userEntityRepository = userEntityRepository;
        this.modelMapper = modelMapper;
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
        userEntityRepository.saveAndFlush(user);
    }
}
