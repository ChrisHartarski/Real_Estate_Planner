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
    private static final RegisterCompanyDTO FIRST_COMPANY = new RegisterCompanyDTO("Магна Техника ЕООД", "Русе, ул. Солун 26", "+359893333595", "office@magna.bg");
    private static final RegisterCompanyDTO TEST_COMPANY = new RegisterCompanyDTO("Тестова компания", "Русе, ул. Солун 26", "+359893333595", "test@magna.bg");
    private static final RegisterUserDTO FIRST_ADMIN_USER = new RegisterUserDTO("c.hartarski@magna.bg", "Chris_12", "Chris_12", FIRST_COMPANY.getName(), "Кристофър", "Хъртарски", "+359893333595", UserRole.ADMIN);
    private static final RegisterUserDTO TEST_USER_1 = new RegisterUserDTO("user1@test.com", "User1_Pass", "User1_Pass", TEST_COMPANY.getName(), "User1First", "User1Last", "+359893333595");
    private static final RegisterUserDTO TEST_USER_2 = new RegisterUserDTO("user2@test.com", "User2_Pass", "User2_Pass", TEST_COMPANY.getName(), "User2First", "User2Last", "+359893333595");

    public DatabaseInitializer(UserEntityService userEntityService, CompanyService companyService) {
        this.userEntityService = userEntityService;
        this.companyService = companyService;
    }

    @Override
    public void run(String... args) throws Exception {
        //Add companies on first startup for test purposes
        if (!companyService.companyExists(FIRST_COMPANY.getName())) {
            companyService.registerCompany(FIRST_COMPANY);
        }
        if (!companyService.companyExists(TEST_COMPANY.getName())) {
            companyService.registerCompany(TEST_COMPANY);
        }

        //Add admin, company_admin and normal user on startup for test purposes
        if (!userEntityService.userExists(FIRST_ADMIN_USER.getEmail())) {
            userEntityService.registerUser(FIRST_ADMIN_USER);
        }
        if (!userEntityService.userExists(TEST_USER_1.getEmail())) {
            userEntityService.registerUser(TEST_USER_1);
        }
        if (!userEntityService.userExists(TEST_USER_2.getEmail())) {
            userEntityService.registerUser(TEST_USER_2);
        }
    }
}
