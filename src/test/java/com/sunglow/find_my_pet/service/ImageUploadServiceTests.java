package com.sunglow.find_my_pet.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

public class ImageUploadServiceTests {
    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @InjectMocks
    private ImageUploadService imageUploadService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        when(cloudinary.uploader()).thenReturn(uploader);
    }

    @Test
    void testUploadImage_EmptyFile() throws IOException{
        MockMultipartFile file = new MockMultipartFile("image", "originalFileName.jpg", "image/jpeg", new byte[0]);
        IOException thrownException = assertThrows(IOException.class, () -> {
            imageUploadService.uploadImage(file);
        });
        assertEquals("File is empty", thrownException.getMessage());
    }

    @Test
    void testUploadImage_CloudinaryResponseIssue() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "originalFileName.jpg", "image/jpeg", "file content".getBytes());
        when(cloudinary.uploader().upload(any(byte[].class), anyMap())).thenReturn(new HashMap<>());

        IOException thrownException = assertThrows(IOException.class, () -> {
            imageUploadService.uploadImage(file);
        });
        assertEquals("Failed to retrieve secure URL from Cloudinary response", thrownException.getMessage());
    }

    @Test
    void testUploadImage_OtherException() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "originalFileName.jpg", "image/jpeg", "file content".getBytes());
        when(cloudinary.uploader().upload(any(byte[].class), anyMap())).thenThrow(new RuntimeException("Network error"));

        IOException thrownException = assertThrows(IOException.class, () -> {
            imageUploadService.uploadImage(file);
        });

        assertEquals("Cloudinary upload failed: Network error", thrownException.getMessage());
    }



    @Test
    void testUploadImage_Success() throws IOException{
        MockMultipartFile file = new MockMultipartFile("image", "originalFileName.jpg", "image/jpeg", "image content".getBytes());
        Map<String, Object> uploadResult = new HashMap<>();
        uploadResult.put("secure_url", "http://image-url.com/image.jpg");

        when(uploader.upload(any(byte[].class), anyMap())).thenReturn(uploadResult);
        String actual = imageUploadService.uploadImage(file);

        assertEquals("http://image-url.com/image.jpg", actual);
        verify(uploader, times(1)).upload(any(byte[].class), anyMap());
    }
}
