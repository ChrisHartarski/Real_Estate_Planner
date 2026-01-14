package com.butikimoti.real_estate_planner.controller;

import com.butikimoti.real_estate_planner.model.entity.Neighbourhood;
import com.butikimoti.real_estate_planner.service.CityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CitiesNeighbourhoodsController {
    private final CityService cityService;

    public CitiesNeighbourhoodsController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/neighbourhoods")
    @ResponseBody
    public List<String> getNeighbourhoods(@RequestParam String cityName) {
        return cityService.getCity(cityName)
                .getNeighbourhoods()
                .stream()
                .map(Neighbourhood::toString)
                .sorted()
                .toList();
    }
}
