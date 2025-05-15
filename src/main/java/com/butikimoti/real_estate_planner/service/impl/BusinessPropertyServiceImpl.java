package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.repository.BusinessPropertyRepository;
import com.butikimoti.real_estate_planner.service.BusinessPropertyService;
import org.springframework.stereotype.Service;

@Service
public class BusinessPropertyServiceImpl implements BusinessPropertyService {
    private final BusinessPropertyRepository businessPropertyRepository;

    public BusinessPropertyServiceImpl(BusinessPropertyRepository businessPropertyRepository) {
        this.businessPropertyRepository = businessPropertyRepository;
    }
}
