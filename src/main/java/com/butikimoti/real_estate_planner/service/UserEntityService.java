package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.userEntity.UserDTO;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.model.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserEntityService {
    boolean userExists(String email);
    void registerUser(UserDTO userDTO, Company company);
    void registerInitialAdminUser(Company company);
    void registerInitialTestUsers(Company company);
    UserEntity getCurrentUser();
    Page<UserDTO> getAllUsers(Pageable pageable, String userFirstLastName, String userEmail, String userCompanyName, UserRole userRole);
}
