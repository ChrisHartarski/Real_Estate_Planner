package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.property.AddPropertyDTO;
import com.butikimoti.real_estate_planner.model.entity.BusinessProperty;
import com.butikimoti.real_estate_planner.repository.BusinessPropertyRepository;
import com.butikimoti.real_estate_planner.service.BusinessPropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BusinessPropertyServiceImpl implements BusinessPropertyService {
    private final BusinessPropertyRepository businessPropertyRepository;
    private final ModelMapper modelMapper;

    public BusinessPropertyServiceImpl(BusinessPropertyRepository businessPropertyRepository, ModelMapper modelMapper) {
        this.businessPropertyRepository = businessPropertyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BusinessProperty saveBusinessProperty(AddPropertyDTO addPropertyDTO) {
        BusinessProperty businessProperty = modelMapper.map(addPropertyDTO, BusinessProperty.class);
        return businessPropertyRepository.saveAndFlush(businessProperty);
    }

    @Override
    public BusinessProperty updateBusinessProperty(BusinessProperty businessProperty) {
        return businessPropertyRepository.saveAndFlush(businessProperty);
    }
}
