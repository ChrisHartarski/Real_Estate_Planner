package com.butikimoti.real_estate_planner.init;

import com.butikimoti.real_estate_planner.model.dto.company.RegisterCompanyDTO;
import com.butikimoti.real_estate_planner.model.dto.userEntity.RegisterUserDTO;
import com.butikimoti.real_estate_planner.model.enums.UserRole;
import com.butikimoti.real_estate_planner.service.CompanyService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final UserEntityService userEntityService;
    private final CompanyService companyService;
    private static final RegisterCompanyDTO FIRST_COMPANY_DATA = new RegisterCompanyDTO("Магна Техника ЕООД", "Русе, ул. Солун 26", "+359893333595", "office@magna.bg");
    private static final RegisterUserDTO FIRST_ADMIN_USER_DATA = new RegisterUserDTO("c.hartarski@magna.bg", "Chris_12", "Chris_12", FIRST_COMPANY_DATA.getName(), "Кристофър", "Хъртарски", "+359893333595", UserRole.ADMIN);

    public DatabaseInitializer(UserEntityService userEntityService, CompanyService companyService) {
        this.userEntityService = userEntityService;
        this.companyService = companyService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!companyService.companyExists(FIRST_COMPANY_DATA.getName())) {
            companyService.registerCompany(FIRST_COMPANY_DATA);
        }

        if (!userEntityService.userExists(FIRST_ADMIN_USER_DATA.getEmail())) {
            userEntityService.registerUser(FIRST_ADMIN_USER_DATA);
        }
    }
}
