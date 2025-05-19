# Codeleap Dog Breeds API


## Approach: Step-by-Step Resolution

This project implements a RESTful API for managing cat breeds and their images using Java 21, Spring Boot, Spring Data JPA, and H2 in-memory database. Below is a step-by-step outline of how the requirements were addressed:

### 1. Entity Relationship Design
- **Breed**: Represents a dog breed. Each breed can have multiple images (one-to-many relationship).
- **BreedImage**: Represents an image belonging to a breed. Each image references a single breed (many-to-one relationship). No image can belong to more than one breed.
- **Reference Image**: The `reference_image_id` field in `Breed` is a shortcut to one main image for the breed, stored as a string ID. This is not a relationship, but a direct reference for convenience.

### 2. Implementing the Entities
- Defined `Breed` and `BreedImage` entities with the correct JPA annotations to enforce the relationships.
- Ensured that `BreedImage` has a `@ManyToOne` relationship to `Breed` (each image belongs to one breed).
- Kept `reference_image_id` as a `String` in `Breed` to store the ID of the main image, matching the requirement that a breed can reference many images, but each image belongs to only one breed.

### 3. DTO and Mapping
- Created `BreedDto` to expose breed data, including `reference_image_id` as a string.
- Configured ModelMapper to map the `referenceImage` entity (if present) to the `reference_image_id` field in the DTO.


### 4. API Design and Implementation
- Implemented endpoints for CRUD operations on breeds and images, including uploading, downloading, and deleting images.
- Ensured that image upload associates the image with the correct breed, and that only valid references are allowed.
- Added endpoints for retrieving a breed's images and the reference image.
- The following REST API endpoints are implemented:

  - **GET /api/breeds**  
    Retrieve all breeds.

  - **GET /api/breeds/{id}**  
    Retrieve a specific breed by ID.

  - **POST /api/breeds/{id}/images**  
    Upload an image for a specific breed.

  - **DELETE /api/breeds/{id}/images/{imageId}**  
    Delete an image by image ID for a specific breed.

  - **GET /api/breeds/{id}/images/{imageId}**  
    Download an image file for a specific breed.

  - **GET /api/breeds/{id}/images/{imageId}/display**  
    Display an image in the browser for a specific breed.

  - **GET /api/breeds/images/random?count=20**  
    Get a random list of image URLs (default count is 20).

These endpoints cover CRUD operations for breeds and their images, as well as random image retrieval.

### 5. OpenAPI Documentation
- Updated `openapi.yaml` to document all endpoints and schemas, including the `reference_image_id` field and the relationships between breeds and images.
- Ensured the OpenAPI schema matches the actual API responses and entity structure.

### 6. Testing and Validation
- Wrote and ran tests to verify that optimistic locking and all business rules are enforced.
- Validated that all requirements are met and that the API behaves as expected.

## Project Information

- **Tech Stack**: Java 21, Spring Boot, Spring Data JPA, H2, ModelMapper, Maven.

- **How to Run**:
  - Option 1: Use the Maven wrapper to run directly:
    ```sh
    ./mvnw spring-boot:run
    ```
    The API will be available at `http://localhost:8080/api`.
  - Option 2: Build a runnable JAR and run it:
    ```sh
    ./mvnw clean package
    java -jar target/codeleap-0.0.1-SNAPSHOT.jar
    ```
    The API will be available at `http://localhost:8080/api`.

- **API Documentation**: See `src/main/resources/openapi.yaml` for the OpenAPI spec.
- **Testing**: Run `./mvnw test` to execute all tests.