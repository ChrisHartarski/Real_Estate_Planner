package com.butikimoti.real_estate_planner.model.dto.comment;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommentDTO {
    private UUID id;
    private String userFirstAndLastName;
    private LocalDateTime date;
    private String commentText;

    public CommentDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserFirstAndLastName() {
        return userFirstAndLastName;
    }

    public void setUserFirstAndLastName(String userFirstAndLastName) {
        this.userFirstAndLastName = userFirstAndLastName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
