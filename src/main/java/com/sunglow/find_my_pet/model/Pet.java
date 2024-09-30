package com.sunglow.find_my_pet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Pet name is required")
    @Size(min = 1, max = 50, message = "Pet name must be between 1 and 50 characters")
    @Column
    private String name;

    @NotEmpty(message = "Colour is required")
    @Column
    private String colour;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be non-negative")
    @Column
    private Integer age;

    @NotNull(message = "Found status is required")
    @Column
    private Boolean isFound;

    @DecimalMin(value = "-180.0", message = "Longitude must be at least -180.0")
    @DecimalMax(value = "180.0", message = "Longitude must be at most 180.0")
    @Column
    private Double longitude;

    @DecimalMin(value = "-90.0", message = "Latitude must be at least -90.0")
    @DecimalMax(value = "90.0", message = "Latitude must be at most 90.0")
    @Column
    private Double latitude;

    @Pattern(regexp = "^https://.*\\.(jpg|jpeg|png|gif)$", message = "Image URL must be a valid HTTPS URL ending with a valid image extension (jpg, jpeg, png, or gif)")
    @Column
    private String imageURL;

    @NotNull(message = "Lost date is required")
    @PastOrPresent(message = "Lost date cannot be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lostDate;

    @NotBlank(message = "Pet type is required")
    @Column
    private String type;

    @OneToOne(mappedBy = "pet", fetch = FetchType.EAGER)
    @JsonBackReference
    private Poster poster;

    @OneToOne(mappedBy = "pet", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Owner owner;
}