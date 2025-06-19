package com.butikimoti.real_estate_planner.model.dto.comment;

import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddCommentDTO {
    private String commentText;
    private UserEntity user;
    private BaseProperty property;

    public AddCommentDTO() {
    }

    @NotNull(message = "{comment.notEmpty}")
    @Size(max = 1000, message = "{comment.length}")
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public BaseProperty getProperty() {
        return property;
    }

    public void setProperty(BaseProperty property) {
        this.property = property;
    }
}
