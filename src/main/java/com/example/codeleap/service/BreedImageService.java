package com.example.codeleap.service;

import com.example.codeleap.exception.BreedNotFoundException;
import com.example.codeleap.exception.ImageNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BreedImageService {

    /**
     * Uploads an image for the specified breed. The uploaded image is saved and set
     * as the reference image for the breed.
     *
     * @param breedId the ID of the breed
     * @param file    the image file to upload
     * @return the ID of the uploaded image
     * @throws BreedNotFoundException if the breed does not exist
     * @throws RuntimeException       if the file cannot be stored
     */
    String uploadImage(String breedId, MultipartFile file);

    /**
     * Deletes an image by its ID for the specified breed.
     *
     * @param breedId the ID of the breed
     * @param imageId the ID of the image to delete
     * @throws ImageNotFoundException if the image does not exist or does not belong
     *                                to the breed
     */
    void deleteImage(String breedId, String imageId);

    /**
     * Retrieves an image resource by its ID for the specified breed.
     *
     * @param breedId the ID of the breed
     * @param imageId the ID of the image to retrieve
     * @return the image as a Spring Resource
     * @throws ImageNotFoundException if the image does not exist or does not belong
     *                                to the breed
     */
    Resource getImage(String breedId, String imageId);

    /**
     * Retrieves a list of random image display URLs.
     *
     * @param count the number of random image URLs to return
     * @return a list of image display URLs
     */
    List<String> getRandomImageUrls(int count);
}
