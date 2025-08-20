package com.butikimoti.real_estate_planner.service.impl;

import com.butikimoti.real_estate_planner.model.dto.comment.AddCommentDTO;
import com.butikimoti.real_estate_planner.model.entity.Comment;
import com.butikimoti.real_estate_planner.repository.CommentRepository;
import com.butikimoti.real_estate_planner.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Comment addComment(AddCommentDTO addCommentDTO) {
        Comment comment = modelMapper.map(addCommentDTO, Comment.class);
        comment.setDate(LocalDateTime.now());

        return commentRepository.saveAndFlush(comment);
    }
}
