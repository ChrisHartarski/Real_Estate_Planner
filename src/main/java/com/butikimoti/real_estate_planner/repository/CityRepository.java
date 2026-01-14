package com.butikimoti.real_estate_planner.repository;

import com.butikimoti.real_estate_planner.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {
    void deleteCityByName(String cityName);
}
