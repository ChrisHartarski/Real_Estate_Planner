package com.butikimoti.real_estate_planner.repository;

import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BasePropertyRepository extends JpaRepository<BaseProperty, UUID> {
    Page<BaseProperty> findByOwnerCompanyId(UUID ownerCompanyId, Pageable pageable);
}
