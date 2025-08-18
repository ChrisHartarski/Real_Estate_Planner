package com.butikimoti.real_estate_planner.repository;

import com.butikimoti.real_estate_planner.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID>,
        JpaSpecificationExecutor<Company> {
    boolean existsByName(String name);
    Company findByName(String name);
}
