package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.entity.PropertyPicture;
import com.butikimoti.real_estate_planner.repository.PropertyPictureRepository;
import com.butikimoti.real_estate_planner.service.PropertyPictureService;
import com.butikimoti.real_estate_planner.service.util.CloudinaryService;
import com.butikimoti.real_estate_planner.service.util.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyPictureServiceImpl implements PropertyPictureService {
    private final PropertyPictureRepository propertyPictureRepository;
    private final CloudinaryService cloudinaryService;

    public PropertyPictureServiceImpl(PropertyPictureRepository propertyPictureRepository, CloudinaryService cloudinaryService) {
        this.propertyPictureRepository = propertyPictureRepository;
        this.cloudinaryService = cloudinaryService;
    }


    @Override
    public PropertyPicture getPicture(UUID id) {
        return propertyPictureRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Property picture not found"));
    }

    @Override
    public void deletePictureFromCloud(UUID id) throws IOException {
        Optional<PropertyPicture> pictureOptional = propertyPictureRepository.findById(id);

        if (pictureOptional.isEmpty()) {
            System.out.println("Property picture not found");
            return;
        }

        String publicID = pictureOptional.get().getPublicID();
        cloudinaryService.deletePicture(publicID);
    }
}
