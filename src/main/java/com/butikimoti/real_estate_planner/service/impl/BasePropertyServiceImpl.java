package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.repository.BasePropertyRepository;
import com.butikimoti.real_estate_planner.service.BasePropertyService;

import java.util.List;

public class BasePropertyServiceImpl implements BasePropertyService {
    private final BasePropertyRepository basePropertyRepository;

    public BasePropertyServiceImpl(BasePropertyRepository basePropertyRepository) {
        this.basePropertyRepository = basePropertyRepository;
    }

    @Override
    public List<BaseProperty> getAllProperties() {
        return basePropertyRepository.findAll();
    }
}
