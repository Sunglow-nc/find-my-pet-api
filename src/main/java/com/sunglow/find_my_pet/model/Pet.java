package com.sunglow.find_my_pet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;

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

    @Column
    private String name;

    @Column
    private String colour;

    @Column
    private Integer age;

    @Column
    private Boolean isFound;

    @Column
    private Double longitude;

    @Column
    private Double latitude;

    @Column
    private String imageURL;

    @Column
    private LocalDateTime lostDate;

    @OneToOne(mappedBy = "pet", fetch = FetchType.LAZY)
    private Poster poster;

    @OneToOne(mappedBy = "pet", fetch = FetchType.LAZY)
    @JsonBackReference
    private Owner owner;
}
