package com.butikimoti.real_estate_planner.service.util;

import com.butikimoti.real_estate_planner.model.dto.util.CloudinaryImageInfoDTO;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", System.getenv("CLOUDINARY_CLOUD_NAME"),
                "api_key", System.getenv("CLOUDINARY_API_KEY"),
                "api_secret", System.getenv("CLOUDINARY_API_SECRET"),
                "secure", true
        ));
    }

    public List<CloudinaryImageInfoDTO> uploadImages(List<MultipartFile> files) {
//        previous version before async:
//
//        List<CloudinaryImageInfoDTO> result = new ArrayList<>();
//
//        for (MultipartFile file : files) {
//            result.add(uploadImage(file));
//        }
//
//        return result;

        List<CompletableFuture<CloudinaryImageInfoDTO>> futures =
                files.stream()
                        .map(this::uploadImageAsync)
                        .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }

    @Async
    public CompletableFuture<CloudinaryImageInfoDTO> uploadImageAsync(MultipartFile file) {
        try {
            return CompletableFuture.completedFuture(uploadImage(file));
        } catch (IOException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @SuppressWarnings("unchecked")
    public CloudinaryImageInfoDTO uploadImage(MultipartFile file) throws IOException {
        Map<String, Object> uploadResult = cloudinary
                        .uploader()
                        .upload(file.getBytes(), ObjectUtils.asMap(
                                "transformation", new Transformation()
                                        .width(1200).height(1200).crop("limit")
                                        .quality("auto")
                                        .fetchFormat("auto")
                        ));
        String imageUrl = uploadResult.get("secure_url").toString();
        String imagePublicID = uploadResult.get("public_id").toString();

        return new CloudinaryImageInfoDTO(imageUrl, imagePublicID);
    }

    public void deletePicture(String publicID) throws IOException {
        this.cloudinary.uploader().destroy(publicID, ObjectUtils.emptyMap());
    }
}
