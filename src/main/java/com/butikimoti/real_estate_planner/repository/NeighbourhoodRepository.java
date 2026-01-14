package com.butikimoti.real_estate_planner.repository;

import com.butikimoti.real_estate_planner.model.entity.Neighbourhood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NeighbourhoodRepository extends JpaRepository<Neighbourhood, UUID> {
    List<Neighbourhood> findAllByCityName(String cityName);
    void deleteByNameAndCityName(String cityName, String name);
    Optional<Neighbourhood> findByNameAndCityName(String neighbourhoodName, String cityName);
}
