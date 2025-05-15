package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.repository.CompanyRepository;
import com.butikimoti.real_estate_planner.service.CompanyService;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
}
