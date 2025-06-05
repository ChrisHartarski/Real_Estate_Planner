package com.butikimoti.real_estate_planner.model.entity;

import jakarta.persistence.*;

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

}
