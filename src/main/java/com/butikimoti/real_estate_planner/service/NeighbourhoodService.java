package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.neighbourhood.NeighbourhoodDTO;
import com.butikimoti.real_estate_planner.model.entity.Neighbourhood;

import java.util.UUID;

public interface NeighbourhoodService {
    void addNeighbourhood(String neighbourhoodName, String cityName);
    void deleteNeighbourhood(UUID neighbourhoodID);
    void deleteNeighbourhood(String neighbourhoodName, String cityName);
    Neighbourhood getNeighbourhood(UUID neighbourhoodId);
    Neighbourhood getNeighbourhood(String neighbourhoodName, String cityName);
    boolean repoIsEmpty();
    NeighbourhoodDTO getNeighbourhoodDTO(Neighbourhood n);
}
