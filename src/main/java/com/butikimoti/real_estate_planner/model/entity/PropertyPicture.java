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

    @Column(name = "public_Id", nullable = false)
    private String publicID;

    public PropertyPicture() {
        createdOn = LocalDateTime.now();
    }

    public PropertyPicture(String pictureLink, BaseProperty property, String publicID) {
        this();
        this.pictureLink = pictureLink;
        this.property = property;
        this.publicID = publicID;
    }

    public PropertyPicture(UUID id, String pictureLink, BaseProperty property, String publicID) {
        this(pictureLink, property, publicID);
        this.id = id;
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

    public String getPublicID() {
        return publicID;
    }

    public void setPublicID(String publicID) {
        this.publicID = publicID;
    }
}
