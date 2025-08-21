package com.butikimoti.real_estate_planner.init;

import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.service.CompanyService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final UserEntityService userEntityService;
    private final CompanyService companyService;

    public DatabaseInitializer(UserEntityService userEntityService, CompanyService companyService) {
        this.userEntityService = userEntityService;
        this.companyService = companyService;
    }

    @Override
    public void run(String... args) throws Exception {
        //Add companies on first startup for test purposes
        companyService.registerInitialCompanies();

        //Add admin, company_admin and normal user on startup for test purposes
        if (companyService.companyExists(System.getenv("ADMIN_COMPANY_NAME"))) {
            userEntityService.registerInitialAdminUser(companyService.getInitialAdminCompany());
        }

        if (companyService.companyExists(System.getenv("TEST_COMPANY_NAME"))) {
            userEntityService.registerInitialTestUsers(companyService.getTestCompany());
        }
    }
}
