package com.example.codeleap.service;

import com.example.codeleap.dto.BreedDto;
import com.example.codeleap.entity.Breed;
import com.example.codeleap.exception.BreedNotFoundException;
import com.example.codeleap.repository.BreedRepository;
import com.example.codeleap.service.impl.BreedServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.Collections;
import java.util.List;

class BreedServiceImplTest {
    @Mock
    private BreedRepository breedRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private BreedServiceImpl breedService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBreeds_returnsList() {
        Mockito.when(breedRepository.findAll()).thenReturn(Collections.singletonList(new Breed()));
        Mockito.when(modelMapper.map(Mockito.any(Breed.class), Mockito.eq(BreedDto.class))).thenReturn(new BreedDto());
        List<BreedDto> result = breedService.getAllBreeds();
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void getBreedById_found() {
        Breed breed = new Breed();
        breed.setId("abys");
        Mockito.when(breedRepository.findById("abys")).thenReturn(Optional.of(breed));
        Mockito.when(modelMapper.map(breed, BreedDto.class)).thenReturn(new BreedDto());
        BreedDto dto = breedService.getBreedById("abys");
        Assertions.assertNotNull(dto);
    }

    @Test
    void getBreedById_notFound() {
        Mockito.when(breedRepository.findById("notfound")).thenReturn(Optional.empty());
        Assertions.assertThrows(BreedNotFoundException.class, () -> breedService.getBreedById("notfound"));
    }
}
