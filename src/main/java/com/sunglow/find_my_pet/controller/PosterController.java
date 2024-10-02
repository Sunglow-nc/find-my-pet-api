package com.sunglow.find_my_pet.controller;

import com.sunglow.find_my_pet.exception.ItemNotFoundException;
import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.service.ImageUploadService;
import com.sunglow.find_my_pet.service.PosterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CacheConfig(cacheNames = "postersCache")
@RequestMapping("/api/v1")
@Tag(name = "Poster", description = "Poster management APIs with caching")
public class PosterController {

    @Autowired
    PosterService posterService;

    @Autowired
    ImageUploadService imageUploadService;

    @Operation(summary = "Get all posters", description = "Retrieve a list of all posters (cached)")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of posters",
        content = @Content(schema = @Schema(implementation = Poster.class)))

    @GetMapping("/posters")
    @Cacheable
    public ResponseEntity<List<Poster>> getAllPosters() {
        return new ResponseEntity<List<Poster>>(posterService.getAllPosters(), HttpStatus.OK);
    }

    @Operation(summary = "Get posters by pet colour", description = "Retrieve a list of posters by pet colour (cached)")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of posters",
        content = @Content(schema = @Schema(implementation = Poster.class)))
    @GetMapping("/posters/colour/{colour}")
    @Cacheable(key = "#colour")
    public ResponseEntity<List<Poster>> getPostersByPetColour(
        @Parameter(description = "Colour of the pet") @PathVariable String colour) {
        List<Poster> posters = posterService.getPostersByPetColour(colour);
        return new ResponseEntity<>(posters, HttpStatus.OK);
    }

    @Operation(summary = "Get posters by pet type", description = "Retrieve a list of posters by pet type (cached)")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of posters",
        content = @Content(schema = @Schema(implementation = Poster.class)))
    @GetMapping("/posters/type/{type}")
    @Cacheable(key = "#type")
    public ResponseEntity<List<Poster>> getPostersByPetType(
        @Parameter(description = "Type of the pet") @PathVariable String type) {
        List<Poster> posters = posterService.getPostersByPetType(type);
        return new ResponseEntity<>(posters, HttpStatus.OK);
    }

    @Operation(summary = "Get a poster by ID", description = "Retrieve a poster by its ID (cached)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Poster found",
            content = @Content(schema = @Schema(implementation = Poster.class))),
        @ApiResponse(responseCode = "404", description = "Poster not found")
    })
    @GetMapping("/posters/id/{id}")
    @Cacheable(key = "#id")
    public ResponseEntity<Poster> getPosterById(
        @Parameter(description = "ID of the poster to retrieve") @PathVariable Long id) {
        return new ResponseEntity<Poster>(posterService.getPosterById(id), HttpStatus.OK);
    }

    @Operation(summary = "Create a new poster", description = "Create a new poster with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Poster created successfully"),
        @ApiResponse(responseCode = "404", description = "Related entity not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/posters")
    @CacheEvict(value = "postersCache", allEntries = true)
    public ResponseEntity<?> postPoster(
        @Parameter(description = "Poster object to be created", required = true)
        @RequestBody Poster poster) {
        try {
            Poster savedPoster = posterService.insertPoster(poster);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Location", "/api/v1/posters/" + savedPoster.getId().toString());

            return new ResponseEntity<>(savedPoster, httpHeaders, HttpStatus.CREATED);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(
                "An error occurred while creating the poster: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a poster", description = "Update an existing poster by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Poster updated successfully"),
        @ApiResponse(responseCode = "404", description = "Poster not found")
    })
    @PutMapping("/posters/{id}")
    @CacheEvict(value = "postersCache", allEntries = true)
    public ResponseEntity<Poster> updatePosterById(
        @Parameter(description = "ID of the poster to update") @PathVariable Long id,
        @Parameter(description = "Updated poster object", required = true) @RequestBody Poster poster) {
        Optional<Poster> updatePoster = posterService.updatePoster(id, poster);

        if (updatePoster.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("posters", "/api/v1/posters" + updatePoster.get().getId().toString());

        return new ResponseEntity<>(updatePoster.get(), httpHeaders, HttpStatus.OK);
    }

    @Operation(summary = "Delete a poster", description = "Delete a poster by its ID")
    @ApiResponse(responseCode = "204", description = "Poster deleted successfully")
    @DeleteMapping("/posters/{id}")
    @CacheEvict(value = "postersCache", allEntries = true)
    public ResponseEntity<Void> deletePosterById(
        @Parameter(description = "ID of the poster to delete") @PathVariable Long id) {
        posterService.deletePosterById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/posters/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = imageUploadService.uploadImage(file);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Image upload failed: " + e.getMessage());
        }
    }
}
