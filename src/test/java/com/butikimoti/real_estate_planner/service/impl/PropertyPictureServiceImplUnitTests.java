package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.entity.Apartment;
import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.entity.PropertyPicture;
import com.butikimoti.real_estate_planner.repository.PropertyPictureRepository;
import com.butikimoti.real_estate_planner.service.util.CloudinaryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PropertyPictureServiceImplUnitTests {
    private PropertyPictureServiceImpl serviceToTest;
    private static final UUID PICTURE_ID = UUID.randomUUID();
    private static final PropertyPicture TEST_PICTURE = new PropertyPicture(PICTURE_ID, "test_link", new Apartment(), "test_publicID");

    @Mock
    private PropertyPictureRepository propertyPictureRepository;

    @Mock
    private CloudinaryService cloudinaryService;

    @BeforeEach
    public void setUp() {
        serviceToTest = new PropertyPictureServiceImpl(propertyPictureRepository, cloudinaryService);
    }

    @Test
    public void testGetPicture_ReturnsCorrectData() {
        when(propertyPictureRepository.findById(PICTURE_ID)).thenReturn(Optional.of(TEST_PICTURE));

        PropertyPicture actual = serviceToTest.getPicture(PICTURE_ID);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.getId(), TEST_PICTURE.getId());
        Assertions.assertEquals(actual.getProperty(), TEST_PICTURE.getProperty());
        Assertions.assertEquals(actual.getPictureLink(), TEST_PICTURE.getPictureLink());
        Assertions.assertEquals(actual.getPublicID(), TEST_PICTURE.getPublicID());
    }

    @Test
    public void testGetPicture_ReturnsExceptionIfNotFound() {
        when(propertyPictureRepository.findById(PICTURE_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> serviceToTest.getPicture(PICTURE_ID));
    }

    @Test
    public void testDeletePictureFromCloud_InvokesCloudinaryService() throws IOException {
        when(propertyPictureRepository.findById(PICTURE_ID)).thenReturn(Optional.of(TEST_PICTURE));

        serviceToTest.deletePictureFromCloud(PICTURE_ID);
        verify(cloudinaryService).deletePicture(TEST_PICTURE.getPublicID());
    }

    @Test
    public void testDeletePictureFromCloud_ThrowsExceptionIfNotFound() {
        when(propertyPictureRepository.findById(PICTURE_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> serviceToTest.deletePictureFromCloud(PICTURE_ID));
    }
}
