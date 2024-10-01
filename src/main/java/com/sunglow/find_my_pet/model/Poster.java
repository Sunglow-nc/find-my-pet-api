package com.sunglow.find_my_pet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Schema(description = "Poster information for a lost pet")
public class Poster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    @Schema(hidden = true)
    private Long id;

    @NotNull(message = "Date posted is required")
    @PastOrPresent(message = "Date posted cannot be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Date when the poster was created", example = "2024-09-01")
    private LocalDate datePosted;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Schema(description = "Description of the lost pet", example = "Brown Labrador and friendly", maxLength = 1000)
    private String description;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    @Schema(description = "Title of the poster", example = "Lost Dog - Brown Labrador", minLength = 5, maxLength = 100)
    private String title;

    @NotNull(message = "Pet is required")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;
}