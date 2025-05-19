package com.example.codeleap.repository;

import com.example.codeleap.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, String> {
}
