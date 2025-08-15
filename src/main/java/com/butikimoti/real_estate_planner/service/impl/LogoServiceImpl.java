package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.repository.LogoRepository;
import com.butikimoti.real_estate_planner.service.LogoService;
import com.butikimoti.real_estate_planner.service.util.CloudinaryService;
import org.springframework.stereotype.Service;

@Service
public class LogoServiceImpl implements LogoService {
    private final LogoRepository logoRepository;
    private final CloudinaryService cloudinaryService;

    public LogoServiceImpl(LogoRepository logoRepository, CloudinaryService cloudinaryService) {
        this.logoRepository = logoRepository;
        this.cloudinaryService = cloudinaryService;
    }
}
