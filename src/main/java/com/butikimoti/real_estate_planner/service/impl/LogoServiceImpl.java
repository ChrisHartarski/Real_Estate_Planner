package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.entity.Logo;
import com.butikimoti.real_estate_planner.repository.LogoRepository;
import com.butikimoti.real_estate_planner.service.LogoService;
import com.butikimoti.real_estate_planner.service.util.CloudinaryService;
import org.springframework.stereotype.Service;

@Service
public class LogoServiceImpl implements LogoService {
    private final LogoRepository logoRepository;

    public LogoServiceImpl(LogoRepository logoRepository) {
        this.logoRepository = logoRepository;
    }

    @Override
    public void saveLogoToDB(Logo logo) {
        logoRepository.saveAndFlush(logo);
    }
}
