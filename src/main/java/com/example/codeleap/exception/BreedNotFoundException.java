package com.example.codeleap.exception;

public class BreedNotFoundException extends RuntimeException {
    public BreedNotFoundException(String id) {
        super("Breed not found with id: " + id);
    }
}
