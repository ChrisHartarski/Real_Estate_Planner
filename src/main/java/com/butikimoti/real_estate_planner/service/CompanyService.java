package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.company.CompanyDTO;
import com.butikimoti.real_estate_planner.model.entity.Company;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


public interface CompanyService {
    boolean companyExists(String companyName);
    void registerCompany(CompanyDTO companyDTO);
    boolean companyHasUsers(String companyName);
    Company getCompany(String companyName);
    Page<CompanyDTO> getAllCompanies(Pageable pageable, String name, String email);
    CompanyDTO getCompanyDTO(UUID id);
    void addLogo(UUID id, MultipartFile file) throws IOException;
    void deleteLogo(UUID id) throws IOException;
    void replaceLogo(UUID id, MultipartFile file) throws IOException;
    void deleteCompany(UUID id) throws IOException;
    Company editAndSaveCompanyToDB(@Valid CompanyDTO company);
}
