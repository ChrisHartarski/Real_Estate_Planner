package com.butikimoti.real_estate_planner.model.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "logos")
public class Logo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "picture_link", nullable = false, unique = true)
    private String pictureLink;

    @OneToOne(mappedBy = "logo")
    private Company company;

    @Column(name = "public_id", nullable = false)
    private String publicId;

    public Logo() {
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
}
