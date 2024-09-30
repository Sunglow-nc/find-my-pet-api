package com.sunglow.find_my_pet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lostDate;

    @Column
    private String type;

    @OneToOne(mappedBy = "pet", fetch = FetchType.EAGER)
    @JsonBackReference
    private Poster poster;

    @OneToOne(mappedBy = "pet", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Owner owner;
}
