package com.butikimoti.real_estate_planner.repository;

import com.butikimoti.real_estate_planner.model.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GarageRepository extends JpaRepository<Garage, UUID> {
}
