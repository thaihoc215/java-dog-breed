package com.example.codeleap.service.impl;

import com.example.codeleap.dto.BreedDto;
import com.example.codeleap.entity.Breed;
import com.example.codeleap.exception.BreedNotFoundException;
import com.example.codeleap.repository.BreedRepository;
import com.example.codeleap.service.BreedService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BreedServiceImpl implements BreedService {

    @Override
    public BreedDto saveBreed(BreedDto breedDto) {
        // Map DTO to entity
        Breed breed = modelMapper.map(breedDto, Breed.class);
        // Save entity
        Breed saved = breedRepository.save(breed);
        // Map back to DTO
        return modelMapper.map(saved, BreedDto.class);
    }

    private final BreedRepository breedRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<BreedDto> getAllBreeds() {
        return breedRepository.findAll().stream()
                .map(breed -> modelMapper.map(breed, BreedDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BreedDto getBreedById(String id) {
        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new BreedNotFoundException(id));
        return modelMapper.map(breed, BreedDto.class);
    }

    @Override
    public void deleteBreedById(String id) {
        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new BreedNotFoundException(id));
        breedRepository.delete(breed);
    }
}
