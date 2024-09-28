package com.sunglow.find_my_pet.util;

import com.sunglow.find_my_pet.model.Owner;
import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.model.Poster;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PosterBuilderUtil {

    public static List<Poster> buildSamplePosters() {
        List<Poster> posters = new ArrayList<>();

        // Poster 1
        Pet pet1 = Pet.builder()
            .id(1L)
            .name("Buddy")
            .colour("Golden")
            .age(3)
            .isFound(false)
            .longitude(-1.234567)
            .latitude(53.456789)
            .imageURL("https://example.com/buddy.jpg")
            .lostDate(LocalDateTime.of(2024, 3, 15, 14, 30))
            .build();

        Owner owner1 = Owner.builder()
            .id(1L)
            .name("John Doe")
            .contactNumber("+1234567890")
            .emailAddress("john.doe@example.com")
            .pet(pet1)
            .build();

        pet1.setOwner(owner1);

        Poster poster1 = Poster.builder()
            .id(1L)
            .datePosted(LocalDateTime.of(2024, 4, 6, 10, 30))
            .description("Golden retriever, very friendly, lost near the park.")
            .title("Missing Dog: Buddy")
            .pet(pet1)
            .build();

        // Poster 2
        Pet pet2 = Pet.builder()
            .id(2L)
            .name("Luna")
            .colour("Black")
            .age(2)
            .isFound(false)
            .longitude(-0.987654)
            .latitude(52.123456)
            .imageURL("https://example.com/luna.jpg")
            .lostDate(LocalDateTime.of(2023, 12, 25, 12, 00))
            .build();

        Owner owner2 = Owner.builder()
            .id(2L)
            .name("Jane Smith")
            .contactNumber("+0987654321")
            .emailAddress("jane.smith@example.com")
            .pet(pet2)
            .build();

        pet2.setOwner(owner2);

        Poster poster2 = Poster.builder()
            .id(2L)
            .datePosted(LocalDateTime.of(2024, 1, 1, 8, 45))
            .description("Black cat with a white spot on the chest, lost on New Year's Eve.")
            .title("Lost Cat: Luna")
            .pet(pet2)
            .build();

        // Poster 3
        Pet pet3 = Pet.builder()
            .id(3L)
            .name("Max")
            .colour("Brown and White")
            .age(5)
            .isFound(true)
            .longitude(-2.345678)
            .latitude(54.123456)
            .imageURL("https://example.com/max.jpg")
            .lostDate(LocalDateTime.of(2024, 5, 1, 9, 00))
            .build();

        Owner owner3 = Owner.builder()
            .id(3L)
            .name("Sam Wilson")
            .contactNumber("+1122334455")
            .emailAddress("sam.wilson@example.com")
            .pet(pet3)
            .build();

        pet3.setOwner(owner3);

        Poster poster3 = Poster.builder()
            .id(3L)
            .datePosted(LocalDateTime.of(2024, 5, 2, 15, 00))
            .description("Found a brown and white dog near the riverbank. Very playful and healthy.")
            .title("Found Dog: Max")
            .pet(pet3)
            .build();

        posters.add(poster1);
        posters.add(poster2);
        posters.add(poster3);

        return posters;
    }
}