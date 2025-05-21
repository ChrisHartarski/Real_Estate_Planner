package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.userEntity.RegisterUserDTO;

public interface UserEntityService {
    boolean userExists(String email);
    void registerUser(RegisterUserDTO registerUserDTO);
    boolean userRepositoryIsEmpty();
}
