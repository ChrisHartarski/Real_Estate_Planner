package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.userEntity.RegisterUserDTO;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;

public interface UserEntityService {
    boolean userExists(String email);
    void register(RegisterUserDTO registerUserDTO);
}
