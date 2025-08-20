package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.company.CompanyDTO;
import com.butikimoti.real_estate_planner.model.dto.util.CloudinaryImageInfoDTO;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.entity.Logo;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.model.enums.UserRole;
import com.butikimoti.real_estate_planner.repository.CompanyRepository;
import com.butikimoti.real_estate_planner.service.CompanyService;
import com.butikimoti.real_estate_planner.service.LogoService;
import com.butikimoti.real_estate_planner.service.UserEntityService;
import com.butikimoti.real_estate_planner.service.util.CloudinaryService;
import com.butikimoti.real_estate_planner.service.util.exceptions.UnauthorizedException;
import com.butikimoti.real_estate_planner.specifications.CompanySpecifications;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final UserEntityService userEntityService;
    private final LogoService logoService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    private static final CompanyDTO FIRST_COMPANY = new CompanyDTO(System.getenv("ADMIN_COMPANY_NAME"), System.getenv("ADMIN_COMPANY_ADDRESS"), System.getenv("ADMIN_COMPANY_PHONE"), System.getenv("ADMIN_COMPANY_EMAIL"));
    private static final CompanyDTO TEST_COMPANY = new CompanyDTO(System.getenv("TEST_COMPANY_NAME"), System.getenv("TEST_COMPANY_ADDRESS"), System.getenv("TEST_COMPANY_PHONE"), System.getenv("TEST_COMPANY_EMAIL"));

    public CompanyServiceImpl(CompanyRepository companyRepository, UserEntityService userEntityService, LogoService logoService, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.userEntityService = userEntityService;
        this.logoService = logoService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean companyExists(String companyName) {
        return companyRepository.existsByName(companyName);
    }

    @Override
    public void registerCompany(CompanyDTO companyDTO) {
        UserEntity currentUser = userEntityService.getCurrentUser();
        if (!currentUser.getUserRole().equals(UserRole.ADMIN)) {
            return;
        }

        registerNewCompany(companyDTO);
    }

    @Override
    public void registerInitialCompanies() {
        if (!companyExists(FIRST_COMPANY.getName())) {
            registerNewCompany(FIRST_COMPANY);
        }

        if (!companyExists(TEST_COMPANY.getName())) {
            registerNewCompany(TEST_COMPANY);
        }
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
    public Company getInitialAdminCompany() {
        return getCompany(System.getenv("ADMIN_COMPANY_NAME"));
    }

    @Override
    public Company getTestCompany() {
        return getCompany(System.getenv("TEST_COMPANY_NAME"));
    }

    @Override
    public Page<CompanyDTO> getAllCompanies(Pageable pageable, String name, String email) {
        UserEntity currentUser = userEntityService.getCurrentUser();

        if (currentUser == null) {
            throw new UnauthorizedException("No logged in user");
        }

        if (!currentUser.getUserRole().equals(UserRole.ADMIN)) {
            throw new UnauthorizedException("Current user is not admin");
        }

        Specification<Company> specification = CompanySpecifications.companiesPageFilters(name, email);
        Page<Company> companies = companyRepository.findAll(specification, pageable);

        return companies.map(company -> modelMapper.map(company, CompanyDTO.class));
    }

    @Override
    public CompanyDTO getCompanyDTO(UUID id) {
        return modelMapper.map(getCompany(id), CompanyDTO.class);
    }

    @Override
    public void addLogo(UUID id, MultipartFile file) throws IOException {
        if (!authorizedUserCheck(id)) {
            return;
        }

        Company company = getCompany(id);

        CloudinaryImageInfoDTO cloudinaryImageInfoDTO = cloudinaryService.uploadImage(file);
        String imageUrl = cloudinaryImageInfoDTO.getImageUrl();
        String imagePublicId = cloudinaryImageInfoDTO.getPublicId();

        Logo logo = new Logo(imageUrl, company, imagePublicId);
        logoService.saveLogoToDB(logo);
        company.setLogo(logo);
        companyRepository.saveAndFlush(company);
    }

    @Override
    public void deleteLogo(UUID id) throws IOException {
        if (!authorizedUserCheck(id)) {
            return;
        }

        Company company = getCompany(id);

        if (company.getLogo() == null) {
            return;
        }

        cloudinaryService.deletePicture(company.getLogo().getPublicId());
        company.setLogo(null);
        companyRepository.saveAndFlush(company);
    }

    @Override
    public void replaceLogo(UUID id, MultipartFile file) throws IOException {
        if (!authorizedUserCheck(id)) {
            return;
        }

        deleteLogo(id);
        addLogo(id, file);
    }

    @Override
    public void deleteCompany(UUID id) throws IOException {
        if (!authorizedUserCheck(id)) {
            return;
        }

        if (companyRepository.existsById(id)) {
            deleteLogo(id);
            companyRepository.deleteById(id);
        }
    }

    @Override
    public Company editAndSaveCompanyToDB(CompanyDTO editCompanyDTO) {
        Company company = getCompany(editCompanyDTO.getId());
        applyEditCompanyDTOToCompany(company, editCompanyDTO);

        return companyRepository.saveAndFlush(company);
    }

    private Company getCompany(UUID id) {
        Company company = companyRepository.findById(id).orElse(null);

        if (company == null) {
            throw new RuntimeException("Company not found");
        }

        return company;
    }

    private void applyEditCompanyDTOToCompany(Company company, CompanyDTO editCompanyDTO) {
        Configuration configuration = modelMapper.getConfiguration();
        boolean isSkipNullEnabled = configuration.isSkipNullEnabled();

        try {
            configuration.setSkipNullEnabled(true);
            modelMapper.map(editCompanyDTO, company);
        } finally {
            configuration.setSkipNullEnabled(isSkipNullEnabled);
        }
    }

    private boolean authorizedUserCheck(UUID id) {
        UserEntity currentUser = userEntityService.getCurrentUser();

        if (currentUser.getUserRole().equals(UserRole.ADMIN)) {
            return true;
        }

        if (currentUser.getUserRole().equals(UserRole.COMPANY_ADMIN)
                && currentUser.getCompany().getId().equals(id)) {
            return true;
        }

        return false;
    }

    private void registerNewCompany(CompanyDTO companyDTO) {
        if(companyExists(companyDTO.getName())) {
            throw new RuntimeException("Company " + companyDTO.getName() + " already exists");
        }

        Company company = modelMapper.map(companyDTO, Company.class);
        company.setRegisteredOn(LocalDateTime.now());
        companyRepository.saveAndFlush(company);
    }
}
