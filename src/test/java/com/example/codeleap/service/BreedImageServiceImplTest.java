package com.example.codeleap.service;

import com.example.codeleap.entity.Breed;
import com.example.codeleap.exception.BreedNotFoundException;
import com.example.codeleap.exception.ImageNotFoundException;
import com.example.codeleap.repository.BreedRepository;
import com.example.codeleap.service.impl.BreedImageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BreedImageServiceImplTest {

    @Autowired
    private BreedImageServiceImpl service;
    @Autowired
    private BreedRepository breedRepository;

    @BeforeEach
    void setUp() {
        if (!breedRepository.existsById("abys")) {
            Breed breed = new Breed();
            breed.setId("abys");
            breed.setName("Abyssinian");
            breedRepository.save(breed);
        }
        // Clean up all breed-images directories before each test
        java.io.File baseDir = new java.io.File("breed-images");
        if (baseDir.exists() && baseDir.isDirectory()) {
            for (java.io.File breedDir : baseDir.listFiles()) {
                if (breedDir.isDirectory()) {
                    for (java.io.File file : breedDir.listFiles()) {
                        file.delete();
                    }
                }
            }
        }
    }

    @Test
    void uploadAndGetImage_success() {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "testdata".getBytes());
        String imageId = service.uploadImage("abys", file);
        assertNotNull(imageId);
        Resource resource = service.getImage("abys", imageId);
        assertNotNull(resource);
        assertEquals("test.jpg", resource.getFilename());
    }

    @Test
    void deleteImage_notFound_throws() {
        assertThrows(ImageNotFoundException.class, () -> service.deleteImage("abys", "notfound"));
    }

    @Test
    void getImage_notFound_throws() {
        assertThrows(ImageNotFoundException.class, () -> service.getImage("abys", "notfound"));
    }

    @Test
    void getRandomImageUrls_empty() {
        List<String> urls = service.getRandomImageUrls(5);
        assertNotNull(urls);
        assertEquals(0, urls.size());
    }

    @Test
    void getRandomImageUrls_withImages() {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "testdata".getBytes());
        service.uploadImage("abys", file);
        List<String> urls = service.getRandomImageUrls(1);
        assertNotNull(urls);
        assertEquals(1, urls.size());
        assertTrue(urls.get(0).contains("/api/breeds/abys/images/"));
    }

    @Test
    void uploadImage_breedNotFound_throws() {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "testdata".getBytes());
        assertThrows(BreedNotFoundException.class, () -> service.uploadImage("notexist", file));
    }
}
