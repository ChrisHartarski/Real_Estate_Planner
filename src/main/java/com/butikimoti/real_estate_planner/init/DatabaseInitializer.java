package com.butikimoti.real_estate_planner.init;

import com.butikimoti.real_estate_planner.model.dto.util.CityDataDTO;
import com.butikimoti.real_estate_planner.service.CityService;
import com.butikimoti.real_estate_planner.service.CompanyService;
import com.butikimoti.real_estate_planner.service.NeighbourhoodService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final UserEntityService userEntityService;
    private final CompanyService companyService;
    private final CityService cityService;
    private final NeighbourhoodService neighbourhoodService;
    private final Gson gson;

    private static final String CITIES_DATA_PATH = "src/main/resources/init-data/cities-neighbourhoods-init-data.json";

    public DatabaseInitializer(UserEntityService userEntityService, CompanyService companyService, CityService cityService, NeighbourhoodService neighbourhoodService, Gson gson) {
        this.userEntityService = userEntityService;
        this.companyService = companyService;
        this.cityService = cityService;
        this.neighbourhoodService = neighbourhoodService;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
        //Add initial cities
        addInitialCities();

        //Add initial neighbourhoods
        addInitialNeighbourhoods();

        //Add companies on first startup for test purposes
        registerInitialCompanies();

        //Add admin, company_admin and normal user on startup for test purposes
        registerInitialUsers();
    }

    private void addInitialCities() throws IOException {
        if (!cityService.repoIsEmpty()) {
            return;
        }

        Set<String> cityNames = getCityNamesData();
        cityNames
                .forEach(cityService::addCity);
    }

    private void addInitialNeighbourhoods() throws IOException {
        if (!neighbourhoodService.repoIsEmpty()) {
            return;
        }

        getFullCityData()
                .forEach(dto -> {
                    neighbourhoodService.addNeighbourhood(dto.getNeighbourhood(), dto.getCity());
                });
    }

    private void registerInitialCompanies() {
        companyService.registerInitialCompanies();
    }

    private void registerInitialUsers() {
        if (companyService.companyExists(System.getenv("ADMIN_COMPANY_NAME"))) {
            userEntityService.registerInitialAdminUser(companyService.getInitialAdminCompany());
        }

        if (companyService.companyExists(System.getenv("TEST_COMPANY_NAME"))) {
            userEntityService.registerInitialTestUsers(companyService.getTestCompany());
        }
    }

    private List<CityDataDTO> getFullCityData() throws IOException {
        return Arrays.stream(gson.fromJson(Files.readString(Path.of(CITIES_DATA_PATH)), CityDataDTO[].class))
                .toList();
    }

    private Set<String> getCityNamesData() throws IOException {
        return getFullCityData()
                .stream()
                .map(CityDataDTO::getCity)
                .collect(Collectors.toSet());
    }
}
