package com.example.codeleap.service;

import com.example.codeleap.dto.BreedDto;
import java.util.List;

public interface BreedService {

    /**
     * Creates or updates a breed from a BreedDto.
     * 
     * @param breedDto the breed data
     * @return the saved breed as BreedDto
     */
    BreedDto saveBreed(BreedDto breedDto);

    /**
     * Retrieves all breeds.
     *
     * @return a list of all breeds
     */
    List<BreedDto> getAllBreeds();

    /**
     * Retrieves a breed by its ID.
     *
     * @param id the ID of the breed
     * @return the breed with the specified ID
     */
    BreedDto getBreedById(String id);

    /**
     * Deletes the breed with the specified ID.
     *
     * @param id the ID of the breed to delete
     */
    void deleteBreedById(String id);
}
