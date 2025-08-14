package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.userEntity.UserDTO;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;

public interface UserEntityService {
    boolean userExists(String email);
    void registerUser(UserDTO userDTO, Company company);
    UserEntity getCurrentUser();
}
