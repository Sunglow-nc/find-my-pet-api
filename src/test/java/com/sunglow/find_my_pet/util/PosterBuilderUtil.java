package com.sunglow.find_my_pet.util;

import com.sunglow.find_my_pet.model.Owner;
import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.model.Poster;

import java.time.LocalDate;
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
            .type("Dog")
            .imageURL("https://example.com/buddy.jpg")
            .lostDate(LocalDate.parse("2024-03-15"))
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
            .datePosted(LocalDate.parse("2024-04-06"))
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
            .type("Cat")
            .imageURL("https://example.com/luna.jpg")
            .lostDate(LocalDate.parse("2023-12-25"))
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
            .datePosted(LocalDate.parse("2024-01-01"))
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
            .type("Dog")
            .imageURL("https://example.com/max.jpg")
            .lostDate(LocalDate.parse("2024-05-01"))
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
            .datePosted(LocalDate.parse("2024-05-02"))
            .description(
                "Found a brown and white dog near the riverbank. Very playful and healthy.")
            .title("Found Dog: Max")
            .pet(pet3)
            .build();

        // Poster 4
        Pet pet4 = Pet.builder()
            .id(4L)
            .name("Age")
            .colour("Black")
            .age(6)
            .isFound(true)
            .longitude(-0.987654)
            .latitude(52.123456)
            .type("Dog")
            .imageURL("https://example.com/age.jpg")
            .lostDate(LocalDate.parse("2023-12-25"))
            .build();

        Owner owner4 = Owner.builder()
            .id(4L)
            .name("Leonard Petrisor")
            .contactNumber("+0987654321")
            .emailAddress("jane.smith@example.com")
            .pet(pet4)
            .build();

        pet4.setOwner(owner4);

        Poster poster4 = Poster.builder()
            .id(4L)
            .datePosted(LocalDate.parse("2024-10-31"))
            .description("Black labrador, very energetic found around Halloween time.")
            .title("Found dog: Age")
            .pet(pet4)
            .build();

        posters.add(poster1);
        posters.add(poster2);
        posters.add(poster3);
        posters.add(poster4);

        return posters;
    }
}