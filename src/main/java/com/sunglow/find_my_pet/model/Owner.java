package com.sunglow.find_my_pet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(description = "Owner of a pet")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    @Schema(hidden = true)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column
    @Schema(description = "Name of the owner", example = "John Doe", minLength = 2, maxLength = 100)
    private String name;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^(\\+44\\s?|0)(?:\\d{2}\\s?\\d{4}\\s?\\d{4}|\\d{3}\\s?\\d{3}\\s?\\d{4}|\\d{4}\\s?\\d{3}\\s?\\d{3})$",
        message = "Invalid UK phone number. Please enter a valid UK mobile or landline number.")
    @Column
    @Schema(description = "Contact number of the owner (UK format)", example = "03330504368")
    private String contactNumber;

    @NotBlank(message = "Email address is required")
    @Email(message = "Invalid email address")
    @Column
    @Schema(description = "Email address of the owner", example = "hello@northcoders.com")
    private String emailAddress;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    @JsonBackReference
    @Schema(hidden = true)
    private Pet pet;
}