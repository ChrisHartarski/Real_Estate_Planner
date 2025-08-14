package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.company.CompanyDTO;
import com.butikimoti.real_estate_planner.model.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CompanyService {
    boolean companyExists(String companyName);
    void registerCompany(CompanyDTO companyDTO);
    boolean companyHasUsers(String companyName);
    Company getCompany(String companyName);
    Page<CompanyDTO> getAllCompanies(Pageable pageable, String name, String email);
}
