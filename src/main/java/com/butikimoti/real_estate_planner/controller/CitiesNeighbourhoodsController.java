package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.model.dto.neighbourhood.NeighbourhoodDTO;
import com.butikimoti.real_estate_planner.service.CityService;
import com.butikimoti.real_estate_planner.service.NeighbourhoodService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

@Controller
public class CitiesNeighbourhoodsController {
    private final CityService cityService;
    private final NeighbourhoodService neighbourhoodService;

    public CitiesNeighbourhoodsController(CityService cityService, NeighbourhoodService neighbourhoodService) {
        this.cityService = cityService;
        this.neighbourhoodService = neighbourhoodService;
    }

    @GetMapping("/neighbourhoods")
    @ResponseBody
    public List<NeighbourhoodDTO> getNeighbourhoods(@RequestParam UUID cityId) {
        return cityService.getCity(cityId)
                .getNeighbourhoods()
                .stream()
                .map(neighbourhoodService::getNeighbourhoodDTO)
                .toList();
    }
}
