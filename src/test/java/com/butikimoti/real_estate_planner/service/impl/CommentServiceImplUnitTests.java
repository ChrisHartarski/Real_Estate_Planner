package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.comment.AddCommentDTO;
import com.butikimoti.real_estate_planner.model.entity.Apartment;
import com.butikimoti.real_estate_planner.model.entity.Comment;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.model.enums.*;
import com.butikimoti.real_estate_planner.repository.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplUnitTests {
    private CommentServiceImpl serviceToTest;
    private static final UUID TEST_COMPANY_ID = UUID.randomUUID();
    private static final UUID TEST_USER_ID = UUID.randomUUID();
    private static final UUID TEST_APARTMENT_ID = UUID.randomUUID();
    private static final Company TEST_COMPANY = new Company(TEST_COMPANY_ID, new ArrayList<>(), "Test Company", "Test Address", "+359000000000", "test@email.com", new ArrayList<>());
    private static final UserEntity TEST_USER = new UserEntity(TEST_USER_ID, "user@mail.com", TEST_COMPANY, "password", UserRole.COMPANY_ADMIN, "name", "last_name", "+359 000 000 000");
    private static final Apartment TEST_APARTMENT = new Apartment(TEST_APARTMENT_ID, PropertyType.APARTMENT, TEST_COMPANY, "test_city", "test_neighbourhood", "test_address", 70000.00, 70, AreaUnit.SQUARE_METER, OfferType.SALE, "contact_name", "+359 000 000 000", "contact@mail.com", "description", LocalDateTime.now(), LocalDateTime.now(), ApartmentType.THREE_ROOM, 3, ConstructionType.BRICK, 2000, 3, 8, HeatingType.GAS, true, "facing");
    private static final AddCommentDTO TEST_ADD_COMMENT_DTO = new AddCommentDTO("comment_text", TEST_USER, TEST_APARTMENT);


    @Mock
    private CommentRepository commentRepository;

    @Captor
    private ArgumentCaptor<Comment> captor;

    @BeforeEach
    public void setUp() {
        serviceToTest = new CommentServiceImpl(commentRepository, new ModelMapper());
    }

    @Test
    public void testAddComment_addsCommentCorrectly() {
        serviceToTest.addComment(TEST_ADD_COMMENT_DTO);

        verify(commentRepository).saveAndFlush(captor.capture());
        Comment actual = captor.getValue();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(TEST_ADD_COMMENT_DTO.getCommentText(), actual.getCommentText());
        Assertions.assertEquals(TEST_ADD_COMMENT_DTO.getProperty(), actual.getProperty());
        Assertions.assertEquals(TEST_ADD_COMMENT_DTO.getUser(), actual.getUser());
        Assertions.assertNotNull(actual.getDate());
    }
}
