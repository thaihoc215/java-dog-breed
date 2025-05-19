package com.example.codeleap.controller;

import com.example.codeleap.dto.BreedDto;
import com.example.codeleap.service.BreedService;
import com.example.codeleap.service.BreedImageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Collections;
import java.util.List;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BreedController.class)
class BreedControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BreedService breedService;
    @MockBean
    private BreedImageService breedImageService;

    @Test
    void getAllBreeds_returnsOk() throws Exception {
        Mockito.when(breedService.getAllBreeds()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/breeds"))
                .andExpect(status().isOk());
    }

    @Test
    void getBreed_returnsOk() throws Exception {
        Mockito.when(breedService.getBreedById("abys")).thenReturn(new BreedDto());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/breeds/abys"))
                .andExpect(status().isOk());
    }

    @Test
    void uploadImage_returnsOk() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE,
                "test".getBytes());
        Mockito.when(breedImageService.uploadImage(Mockito.eq("abys"), Mockito.any())).thenReturn("imgid");
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/breeds/abys/images").file(file))
                .andExpect(status().isOk());
    }

    @Test
    void deleteImage_returnsOk() throws Exception {
        Mockito.doNothing().when(breedImageService).deleteImage("abys", "imgid");
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/breeds/abys/images/imgid"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imageId").value("imgid"))
                .andExpect(jsonPath("$.message").value("Delete successful"));
    }

    @Test
    void getImage_returnsOk() throws Exception {
        Resource resource = new ByteArrayResource("test image".getBytes()) {
            @Override
            public String getFilename() {
                return "test.jpg";
            }
        };
        Mockito.when(breedImageService.getImage("abys", "imgid")).thenReturn(resource);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/breeds/abys/images/imgid"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"test.jpg\""))
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
    }

    @Test
    void displayImage_returnsOk() throws Exception {
        Resource resource = new ByteArrayResource("test image".getBytes());
        Mockito.when(breedImageService.getImage("abys", "imgid")).thenReturn(resource);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/breeds/abys/images/imgid/display"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
    }

    @Test
    void getRandomImages_returnsOk() throws Exception {
        List<String> urls = List.of("/api/breeds/abys/images/imgid/display");
        Mockito.when(breedImageService.getRandomImageUrls(1)).thenReturn(urls);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/breeds/images/random?count=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].url").value("/api/breeds/abys/images/imgid/display"));
    }

    @Test
    void getRandomImages_invalidCount_returnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/breeds/images/random?count=0"))
                .andExpect(status().isBadRequest());
    }
}
