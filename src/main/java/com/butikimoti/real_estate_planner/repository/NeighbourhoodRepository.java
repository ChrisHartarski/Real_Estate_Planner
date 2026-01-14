package com.butikimoti.real_estate_planner.repository;

import com.butikimoti.real_estate_planner.model.entity.Neighbourhood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NeighbourhoodRepository extends JpaRepository<Neighbourhood, UUID> {
}
