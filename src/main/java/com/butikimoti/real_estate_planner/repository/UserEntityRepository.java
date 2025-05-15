package com.butikimoti.real_estate_planner.repository;

import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);
}
