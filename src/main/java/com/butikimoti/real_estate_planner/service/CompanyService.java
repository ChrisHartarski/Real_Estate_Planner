package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.company.RegisterCompanyDTO;
import com.butikimoti.real_estate_planner.model.entity.Company;

public interface CompanyService {
    boolean companyExists(String companyName);
    void registerCompany(RegisterCompanyDTO registerCompanyDTO);
    boolean companyHasUsers(String companyName);
    Company getCompany(String companyName);
}
