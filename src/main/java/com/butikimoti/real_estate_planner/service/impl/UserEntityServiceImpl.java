package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.userEntity.RegisterUserDTO;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.repository.UserEntityRepository;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public void registerUser(RegisterUserDTO registerUserDTO) {
        if (userExists(registerUserDTO.getEmail())) {
            throw new RuntimeException("User with email " + registerUserDTO.getEmail() + " already exists");
        }

        UserEntity user = modelMapper.map(registerUserDTO, UserEntity.class);
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
}
