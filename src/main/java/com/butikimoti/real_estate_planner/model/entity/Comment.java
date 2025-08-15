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

    @Column(name = "user_first_name")
    private String userFirstName;

    @Column(name = "user_last_name")
    private String userLastName;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne
    private BaseProperty property;

    @Column(nullable = false)
    private String commentText;

    public Comment() {
    }

    public Comment(String userFirstName, String userLastName, LocalDateTime date, BaseProperty property, String commentText) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.date = date;
        this.property = property;
        this.commentText = commentText;
    }

    public Comment(UUID id, String userFirstName, String userLastName, LocalDateTime date, BaseProperty property, String commentText) {
        this(userFirstName, userLastName, date, property, commentText);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
