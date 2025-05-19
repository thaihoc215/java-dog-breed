package com.example.codeleap.controller;

import com.example.codeleap.dto.BreedDto;
import com.example.codeleap.service.BreedImageService;
import com.example.codeleap.service.BreedService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/secure/breeds")
@RequiredArgsConstructor
class BreedWithAuthController {
    private final BreedService breedService;
    private final BreedImageService breedImageService;

    private static final Logger logger = LoggerFactory.getLogger(BreedController.class);

    @GetMapping
    public List<BreedDto> getAllBreeds() {
        logger.info("GET /api/breeds - getAllBreeds called");
        return breedService.getAllBreeds();
    }
}
