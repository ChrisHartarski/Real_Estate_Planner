package com.butikimoti.real_estate_planner.model.dto.util;

public class CloudinaryImageInfoDTO {
    private String imageUrl;
    private String publicId;

    public CloudinaryImageInfoDTO() {
    }

    public CloudinaryImageInfoDTO(String imageUrl, String publicId) {
        this.imageUrl = imageUrl;
        this.publicId = publicId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
}
