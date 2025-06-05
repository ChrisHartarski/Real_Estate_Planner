package com.butikimoti.real_estate_planner.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "property_pictures")
public class PropertyPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "picture_link", nullable = false, unique = true)
    private String pictureLink;

    @ManyToOne
    private BaseProperty property;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    public PropertyPicture() {
        createdOn = LocalDateTime.now();
    }

    public PropertyPicture(UUID id, String pictureLink, BaseProperty property) {
        this();
        this.id = id;
        this.pictureLink = pictureLink;
        this.property = property;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public BaseProperty getProperty() {
        return property;
    }

    public void setProperty(BaseProperty property) {
        this.property = property;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
