package com.example.codeleap.service;

import com.example.codeleap.dto.BreedDto;
import com.example.codeleap.entity.Breed;
import com.example.codeleap.repository.BreedRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BreedImportService implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(BreedImportService.class);
    private final RestTemplate restTemplate;
    private final BreedRepository breedRepository;
    private final ModelMapper modelMapper;

    /**
     * Imports breed data from an external API (thecatapi.com) and saves it to the
     * database.
     * This method is executed at application startup.
     *
     * @param args application arguments (not used)
     */
    @Override
    public void run(ApplicationArguments args) {
        String url = "https://api.thecatapi.com/v1/breeds";
        try {
            BreedDto[] breedDtos = restTemplate.getForObject(url, BreedDto[].class);
            if (breedDtos != null) {
                Arrays.stream(breedDtos).forEach(dto -> {
                    Breed breed = modelMapper.map(dto, Breed.class);
                    breedRepository.save(breed);
                });
                logger.info("Imported {} breeds", Optional.of(breedDtos.length));
            }
        } catch (Exception e) {
            logger.error("Failed to import breeds", e);
        }
    }
}
