package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.company.CompanyDTO;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.repository.CompanyRepository;
import com.butikimoti.real_estate_planner.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
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
}
