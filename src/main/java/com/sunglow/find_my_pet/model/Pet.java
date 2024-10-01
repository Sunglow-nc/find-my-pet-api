package com.sunglow.find_my_pet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Pet information")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    @Schema(hidden = true)
    private Long id;

    @NotBlank(message = "Pet name is required")
    @Size(min = 1, max = 50, message = "Pet name must be between 1 and 50 characters")
    @Column
    @Schema(description = "Name of the pet", example = "Cody", minLength = 1, maxLength = 50)
    private String name;

    @NotEmpty(message = "Colour is required")
    @Column
    @Schema(description = "Colour of the pet", example = "Brown")
    private String colour;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be non-negative")
    @Column
    @Schema(description = "Age of the pet", example = "3", minimum = "0")
    private Integer age;

    @NotNull(message = "Found status is required")
    @Column
    @Schema(description = "Whether the pet has been found", example = "false")
    private Boolean isFound;

    @DecimalMin(value = "-180.0", message = "Longitude must be at least -180.0")
    @DecimalMax(value = "180.0", message = "Longitude must be at most 180.0")
    @Column
    @Schema(description = "Longitude of the pet's last known location", example = "-2.2385", minimum = "-180", maximum = "180")
    private Double longitude;

    @DecimalMin(value = "-90.0", message = "Latitude must be at least -90.0")
    @DecimalMax(value = "90.0", message = "Latitude must be at most 90.0")
    @Column
    @Schema(description = "Latitude of the pet's last known location", example = "53.4719", minimum = "-90", maximum = "90")
    private Double latitude;

    @Pattern(regexp = "^https://.*\\.(jpg|jpeg|png|gif)$", message = "Image URL must be a valid HTTPS URL ending with a valid image extension (jpg, jpeg, png, or gif)")
    @Column
    @Schema(description = "URL of the pet's image", example = "https://example.com/pet-image.jpg")
    private String imageURL;

    @NotNull(message = "Lost date is required")
    @PastOrPresent(message = "Lost date cannot be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Date when the pet was lost", example = "2024-08-01")
    private LocalDate lostDate;

    @NotBlank(message = "Pet type is required")
    @Column
    @Schema(description = "Type of pet", example = "Dog")
    private String type;

    @OneToOne(mappedBy = "pet", fetch = FetchType.EAGER)
    @JsonBackReference
    @Schema(hidden = true)
    private Poster poster;

    @OneToOne(mappedBy = "pet", fetch = FetchType.EAGER)
    @JsonManagedReference
    @Schema(description = "Owner of this pet")
    private Owner owner;
}