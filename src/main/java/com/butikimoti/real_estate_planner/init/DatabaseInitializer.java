package com.butikimoti.real_estate_planner.init;

import com.butikimoti.real_estate_planner.model.dto.company.CompanyDTO;
import com.butikimoti.real_estate_planner.model.dto.userEntity.UserDTO;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.enums.UserRole;
import com.butikimoti.real_estate_planner.service.CompanyService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final UserEntityService userEntityService;
    private final CompanyService companyService;
    private static final CompanyDTO FIRST_COMPANY = new CompanyDTO("Магна Техника ЕООД", "Русе, ул. Солун 26", "+359893333595", "office@magna.bg");
    private static final CompanyDTO TEST_COMPANY = new CompanyDTO("Тестова компания", "Русе, ул. Солун 26", "+359893333595", "test@magna.bg");
    private static final UserDTO FIRST_ADMIN_USER = new UserDTO("c.hartarski@magna.bg", "Chris_12", "Chris_12", FIRST_COMPANY.getName(), "Кристофър", "Хъртарски", "+359893333595", UserRole.ADMIN);
    private static final UserDTO TEST_USER_1 = new UserDTO("user1@test.com", "User1_Pass", "User1_Pass", TEST_COMPANY.getName(), "User1First", "User1Last", "+359893333595");
    private static final UserDTO TEST_USER_2 = new UserDTO("user2@test.com", "User2_Pass", "User2_Pass", TEST_COMPANY.getName(), "User2First", "User2Last", "+359893333595");

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
            Company adminCompany = companyService.getCompany(FIRST_ADMIN_USER.getCompanyName());
            userEntityService.registerUser(FIRST_ADMIN_USER, adminCompany);
        }
        if (!userEntityService.userExists(TEST_USER_1.getEmail())) {
            Company user1Company = companyService.getCompany(TEST_USER_1.getCompanyName());
            userEntityService.registerUser(TEST_USER_1, user1Company);
        }
        if (!userEntityService.userExists(TEST_USER_2.getEmail())) {
            Company user2Company = companyService.getCompany(TEST_USER_2.getCompanyName());
            userEntityService.registerUser(TEST_USER_2, user2Company);
        }
    }
}
