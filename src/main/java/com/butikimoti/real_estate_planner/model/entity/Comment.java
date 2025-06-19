package com.butikimoti.real_estate_planner.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private UserEntity user;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne
    private BaseProperty property;

    @Column(nullable = false)
    private String commentText;

    public Comment() {
    }

    public Comment(UserEntity user, LocalDateTime date, BaseProperty property, String commentText) {
        this.user = user;
        this.date = date;
        this.property = property;
        this.commentText = commentText;
    }

    public Comment(UUID id, UserEntity user, LocalDateTime date, BaseProperty property, String commentText) {
        this(user, date, property, commentText);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BaseProperty getProperty() {
        return property;
    }

    public void setProperty(BaseProperty property) {
        this.property = property;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String comment) {
        this.commentText = comment;
    }
}
