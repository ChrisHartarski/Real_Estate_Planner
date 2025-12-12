package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.entity.PropertyPicture;

import java.io.IOException;
import java.util.UUID;

public interface PropertyPictureService {
    PropertyPicture getPicture(UUID id);
    void deletePictureFromCloud(PropertyPicture picture) throws IOException;
}
