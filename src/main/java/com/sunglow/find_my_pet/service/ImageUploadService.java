package com.sunglow.find_my_pet.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageUploadService {

    private final Cloudinary cloudinary;

    @Autowired
    public ImageUploadService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IOException("File is empty");
        }

        Map<String, Object> params = ObjectUtils.asMap(
                "use_filename", false,
                "unique_filename", true,
                "overwrite", false
        );

        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

            if (uploadResult == null || !uploadResult.containsKey("secure_url") || uploadResult.get("secure_url") == null) {
                throw new IOException("Failed to retrieve secure URL from Cloudinary response");
            }
            return (String) uploadResult.get("secure_url");
        } catch (RuntimeException e) {
            throw new IOException("Cloudinary upload failed: " + e.getMessage(), e);
        }
    }
}

