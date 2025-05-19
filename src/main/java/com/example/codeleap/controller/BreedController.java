package com.example.codeleap.controller;

import com.example.codeleap.dto.BreedDto;
import com.example.codeleap.service.BreedService;
import com.example.codeleap.service.BreedImageService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/breeds")
@RequiredArgsConstructor
public class BreedController {
    private final BreedService breedService;
    private final BreedImageService breedImageService;

    private static final Logger logger = LoggerFactory.getLogger(BreedController.class);

    @GetMapping
    public List<BreedDto> getAllBreeds() {
        logger.info("GET /api/breeds - getAllBreeds called");
        return breedService.getAllBreeds();
    }

    @GetMapping("/{id}")
    public BreedDto getBreed(@PathVariable String id) {
        logger.info("GET /api/breeds/{} - getBreed called", id);
        return breedService.getBreedById(id);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<ImageResponse> uploadImage(@PathVariable String id,
            @RequestParam("file") MultipartFile file) {
        logger.info("POST /api/breeds/{}/images - uploadImage called", id);
        if (file == null || file.isEmpty()) {
            logger.warn("Upload image failed: file is empty for breedId={}", id);
            return ResponseEntity.badRequest().body(new ImageResponse(null, id, "File must not be empty"));
        }
        String imageId = breedImageService.uploadImage(id, file);
        logger.info("Image uploaded: breedId={}, imageId={}", id, imageId);
        return ResponseEntity.ok(new ImageResponse(imageId, id, "Upload successful"));
    }

    @DeleteMapping("/{id}/images/{imageId}")
    public ResponseEntity<ImageResponse> deleteImage(@PathVariable String id, @PathVariable String imageId) {
        logger.info("DELETE /api/breeds/{}/images/{} - deleteImage called", id, imageId);
        breedImageService.deleteImage(id, imageId);
        logger.info("Image deleted: breedId={}, imageId={}", id, imageId);
        return ResponseEntity.ok(new ImageResponse(imageId, id, "Delete successful"));
    }

    @GetMapping("/{id}/images/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable String id, @PathVariable String imageId) {
        logger.info("GET /api/breeds/{}/images/{} - getImage called", id, imageId);
        Resource file = breedImageService.getImage(id, imageId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }

    @GetMapping("/{id}/images/{imageId}/display")
    public ResponseEntity<Resource> displayImage(@PathVariable String id, @PathVariable String imageId) {
        logger.info("GET /api/breeds/{}/images/{}/display - displayImage called", id, imageId);
        Resource file = breedImageService.getImage(id, imageId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }

    @GetMapping("/images/random")
    public ResponseEntity<List<ImageUrlResponse>> getRandomImages(@RequestParam(defaultValue = "20") int count) {
        logger.info("GET /api/breeds/images/random - getRandomImages called, count={}", count);
        if (count < 1) {
            logger.warn("Random images request with invalid count: {}", count);
            return ResponseEntity.badRequest().body(List.of());
        }
        List<String> urls = breedImageService.getRandomImageUrls(count);
        List<ImageUrlResponse> response = urls.stream().map(url -> {
            String[] parts = url.split("/");
            String breedId = parts.length > 4 ? parts[3] : null;
            String imageId = parts.length > 6 ? parts[5] : null;
            return new ImageUrlResponse(url, breedId, imageId);
        }).toList();
        logger.info("Returning {} random image URLs", response.size());
        return ResponseEntity.ok(response);
    }

    public static record ImageResponse(String imageId, String breedId, String message) {
    }

    public static record ImageUrlResponse(String url, String breedId, String imageId) {
    }

    // Get breed by query parameters id and name
    @GetMapping("/search")
    public ResponseEntity<BreedDto> getBreedByIdAndName(@RequestParam String id, @RequestParam String name) {
        logger.info("GET /api/breeds/search?id={}&name={} - getBreedByIdAndName called", id, name);
        BreedDto breed = breedService.getBreedById(id);
        if (breed != null && name.equalsIgnoreCase(breed.getName())) {
            logger.info("Breed found for id={} and name={}", id, name);
            return ResponseEntity.ok(breed);
        } else {
            logger.warn("Breed not found for id={} and name={}", id, name);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<BreedDto> createBreed(@RequestBody BreedDto breedDto) {
        logger.info("POST /api/breeds - createBreed called with id={}, name={}", breedDto.getId(), breedDto.getName());
        BreedDto saved = breedService.saveBreed(breedDto);
        logger.info("Breed created: id={}, name={}", saved.getId(), saved.getName());
        return ResponseEntity.ok(saved);
    }

    // Create a new breed using form-data (fields as individual form fields)
    @PostMapping(path = "/form", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BreedDto> createBreedForm(
            @RequestParam String id,
            @RequestParam String name,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String life_span,
            @RequestParam(required = false) String temperament) {
        logger.info("POST /api/breeds/form - createBreedForm called with id={}, name={}", id, name);
        BreedDto breedDto = new BreedDto();
        breedDto.setId(id);
        breedDto.setName(name);
        breedDto.setOrigin(origin);
        breedDto.setDescription(description);
        breedDto.setLife_span(life_span);
        breedDto.setTemperament(temperament);
        BreedDto saved = breedService.saveBreed(breedDto);
        logger.info("Breed created via form: id={}, name={}", saved.getId(), saved.getName());
        return ResponseEntity.ok(saved);
    }

    // @PostMapping("/{id}/images")
    // public ResponseEntity<ImageResponse> uploadImage(@PathVariable String id,
    // @RequestParam("file") MultipartFile file) {
    // logger.info("POST /api/breeds/{}/images - uploadImage called", id);
    // if (file == null || file.isEmpty()) {
    // logger.warn("Upload image failed: file is empty for breedId={}", id);
    // return ResponseEntity.badRequest().body(new ImageResponse(null, id, "File
    // must not be empty"));
    // }
    // String imageId = breedImageService.uploadImage(id, file);
    // logger.info("Image uploaded: breedId={}, imageId={}", id, imageId);
    // return ResponseEntity.ok(new ImageResponse(imageId, id, "Upload
    // successful"));
    // }

    // Create a new breed using raw text (plain text body, e.g. just the name or id)
    @PostMapping(path = "/raw", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<BreedDto> createBreedRaw(@RequestBody String body) {
        logger.info("POST /api/breeds/raw - createBreedRaw called");
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        BreedDto breedDto;
        try {
            breedDto = mapper.readValue(body, BreedDto.class);
            logger.info("Parsed JSON for breed: name={}, id={}", breedDto.getName(), breedDto.getId());
        } catch (Exception e) {
            logger.warn("Failed to parse JSON, treating as name only. Body: {}", body);
            breedDto = new BreedDto();
            breedDto.setName(body.trim());
        }
        if (breedDto.getId() == null || breedDto.getId().isEmpty()) {
            breedDto.setId(java.util.UUID.randomUUID().toString());
        }
        BreedDto saved = breedService.saveBreed(breedDto);
        logger.info("Breed created via raw: id={}, name={}", saved.getId(), saved.getName());
        return ResponseEntity.ok(saved);
    }

    // Update the name and weight of a breed by id
    @PutMapping("/{id}")
    public ResponseEntity<BreedDto> updateBreed(@PathVariable String id, @RequestBody BreedDto updateDto) {
        logger.info("PUT /api/breeds/{} - updateBreed called with name={}, weight={}", id, updateDto.getName(), updateDto.getWeight());
        BreedDto breed = breedService.getBreedById(id);
        if (breed == null) {
            logger.warn("Breed not found for id={} when updating", id);
            return ResponseEntity.notFound().build();
        }
        if (updateDto.getName() != null) {
            breed.setName(updateDto.getName());
        }
        if (updateDto.getWeight() != null) {
            breed.setWeight(updateDto.getWeight());
        }
        BreedDto updated = breedService.saveBreed(breed);
        logger.info("Breed updated: id={}, name={}, weight={}", id, updated.getName(), updated.getWeight());
        return ResponseEntity.ok(updated);
    }

    // Delete a breed by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBreed(@PathVariable String id) {
        logger.info("DELETE /api/breeds/{} - deleteBreed called", id);
        BreedDto breed = breedService.getBreedById(id);
        if (breed == null) {
            logger.warn("Breed not found for id={} when deleting", id);
            return ResponseEntity.notFound().build();
        }
        breedService.deleteBreedById(id);
        logger.info("Breed deleted: id={}", id);
        return ResponseEntity.noContent().build();
    }
    
}
