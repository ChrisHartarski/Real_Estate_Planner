package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.company.CompanyDTO;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.model.enums.UserRole;
import com.butikimoti.real_estate_planner.repository.CompanyRepository;
import com.butikimoti.real_estate_planner.service.CompanyService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import com.butikimoti.real_estate_planner.specifications.CompanySpecifications;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final UserEntityService userEntityService;
    private final ModelMapper modelMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, UserEntityService userEntityService, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.userEntityService = userEntityService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean companyExists(String companyName) {
        return companyRepository.existsByName(companyName);
    }

    @Override
    public void registerCompany(CompanyDTO companyDTO) {
        if(companyExists(companyDTO.getName())) {
            throw new RuntimeException("Company " + companyDTO.getName() + " already exists");
        }

        Company company = modelMapper.map(companyDTO, Company.class);
        company.setRegisteredOn(LocalDateTime.now());
        companyRepository.saveAndFlush(company);
    }

    @Override
    public boolean companyHasUsers(String companyName) {
        if(!companyRepository.existsByName(companyName)) {
            return false;
        }

        return !companyRepository.findByName(companyName).getUsers().isEmpty();
    }

    @Override
    public Company getCompany(String companyName) {
        return companyRepository.findByName(companyName);
    }

    @Override
    public Page<CompanyDTO> getAllCompanies(Pageable pageable, String name, String email) {
        UserEntity currentUser = userEntityService.getCurrentUser();

        if (currentUser == null) {
            throw new RuntimeException("No logged in user");
        }

        if (!currentUser.getUserRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("Current user is not admin");
        }

        Specification<Company> specification = CompanySpecifications.companiesPageFilters(name, email);
        Page<Company> companies = companyRepository.findAll(specification, pageable);

        return companies.map(company -> modelMapper.map(company, CompanyDTO.class));
    }
}
