package com.butikimoti.real_estate_planner.repository;

import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BasePropertyRepository extends JpaRepository<BaseProperty, UUID> {
}
