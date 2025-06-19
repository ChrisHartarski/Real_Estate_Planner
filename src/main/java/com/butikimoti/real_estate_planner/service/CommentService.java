package com.butikimoti.real_estate_planner.service;

import com.butikimoti.real_estate_planner.model.dto.comment.AddCommentDTO;
import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.entity.Comment;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;

public interface CommentService {
    Comment addComment(AddCommentDTO addCommentDTO);
}
