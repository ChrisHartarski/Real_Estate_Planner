package com.butikimoti.real_estate_planner.model.dto.comment;

import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddCommentDTO {
    private String commentText;
    private String userFirstName;
    private String userLastName;
    private BaseProperty property;

    public AddCommentDTO() {
    }

    public AddCommentDTO(String commentText, String userFirstName, String userLastName, BaseProperty property) {
        this.commentText = commentText;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.property = property;
    }

    @NotNull(message = "{comment.notEmpty}")
    @Size(max = 1000, message = "{comment.length}")
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public BaseProperty getProperty() {
        return property;
    }

    public void setProperty(BaseProperty property) {
        this.property = property;
    }
}
