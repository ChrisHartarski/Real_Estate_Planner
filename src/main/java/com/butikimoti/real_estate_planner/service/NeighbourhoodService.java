package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.entity.Neighbourhood;

import java.util.UUID;

public interface NeighbourhoodService {
    void addNeighbourhood(String neighbourhoodName, String cityName);
    void deleteNeighbourhood(UUID neighbourhoodID);
    void deleteNeighbourhood(String neighbourhoodName, String cityName);
    Neighbourhood getNeighbourhood(UUID neighbourhoodID);
    Neighbourhood getNeighbourhood(String neighbourhoodName, String cityName);
}
