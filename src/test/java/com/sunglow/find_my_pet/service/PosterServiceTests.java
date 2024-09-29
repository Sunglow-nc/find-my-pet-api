package com.sunglow.find_my_pet.service;

import com.sunglow.find_my_pet.model.Owner;
import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.repository.OwnerManagerRepository;
import com.sunglow.find_my_pet.repository.PosterManagerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PosterServiceTests {
    @Mock
    private PosterManagerRepository posterRepository;
    @Mock
    private OwnerManagerRepository ownerRepository;

    @InjectMocks
    private PosterServiceImpl posterService;
    private Pet pet1;
    private Owner owner1;
    private Poster poster1;
    private Pet pet2;
    private Owner owner2;
    private Poster poster2;
    List<Poster> posters;

    @BeforeEach
    public void setup() {
        pet1 = Pet.builder()
                .id(1L)
                .name("Whiskers")
                .colour("Grey")
                .age(3)
                .isFound(false)
                .longitude(-0.1278)
                .latitude(51.5074)
                .imageURL("https://example.com/whiskers.jpg")
                .lostDate(LocalDateTime.parse("2024-09-02T22:00"))
                .build();
        owner1 = Owner.builder()
                .id(1L)
                .name("Sarah Thompson")
                .contactNumber("020 7946 0958")
                .emailAddress("sarah.t@gmail.com")
                .pet(pet1)
                .build();
        pet1.setOwner(owner1);

        poster1 = Poster.builder()
                .datePosted(LocalDateTime.parse("2024-09-26T10:15"))
                .description(
                        "Gray tabby cat with green eyes. Wearing a blue collar with a bell.")
                .title("Missing Tabby Cat - Please Help")
                .pet(pet1)
                .build();

        pet2 = Pet.builder()
                .name("Mochi")
                .colour("Cream")
                .age(2)
                .isFound(false)
                .longitude(-2.2426)
                .latitude(53.4808)
                .imageURL("https://example.com/mochi.jpg")
                .lostDate(LocalDateTime.parse("2024-08-27T08:30"))
                .build();

        owner2 = Owner.builder()
                .name("Kevin Lee")
                .contactNumber("0161 496 0735")
                .emailAddress("kevin.l@geemail.com")
                .pet(pet2)
                .build();
        pet2.setOwner(owner2);

        poster2 = Poster.builder()
                .datePosted(LocalDateTime.parse("2024-09-27T13:45"))
                .description("Siamese cat with blue eyes. Very vocal and responds to 'Mochi'.")
                .title("Lost Siamese Cat - Reward Offered")
                .pet(pet2)
                .build();

        posters = new ArrayList<>();
        when(posterRepository.findAll()).thenReturn(posters);
    }

    @Nested
    class GetAllPosters{
        @Test
        @DisplayName("Should return an empty list when no posters exist")
        void noPostersExist(){
            when(posterRepository.findAll()).thenReturn(posters);
            List<Poster> actual = posterService.getAllPosters();
            assertThat(actual).isEmpty();
        }

        @Test
        @DisplayName("Should return a list with null field when null Model-related field")
        void nullField() {
            Poster posterWithoutPetInfo = Poster.builder()
                    .datePosted(LocalDateTime.parse("2024-09-26T10:15"))
                    .description(
                            "Gray tabby cat with green eyes. Wearing a blue collar with a bell.")
                    .title("Missing Tabby Cat - Please Help")
                    .build();
            posters.add(posterWithoutPetInfo);
            when(posterRepository.findAll()).thenReturn(posters);
            List<Poster> actual = posterService.getAllPosters();
            assertThat(actual.getFirst().getPet()).isNull();
        }

        @Test
        @DisplayName("Should return a list with one item when only one poster exists")
        void singleItemList(){
            posters.add(poster1);
            when(posterRepository.findAll()).thenReturn(posters);
            List<Poster> actual = posterService.getAllPosters();

            assertThat(actual).hasSize(1);
            assertThat(actual.getFirst()).isEqualTo(poster1);
            assertThat(actual.getFirst().getPet()).isEqualTo(pet1);
            assertThat(actual.getFirst().getPet().getOwner()).isEqualTo(owner1);
            assertThat(actual.getFirst().getTitle()).isEqualTo("Missing Tabby Cat - Please Help");
            assertThat(actual.getFirst().getDescription()).isEqualTo("Gray tabby cat with green eyes. Wearing a blue collar with a bell.");
            assertThat(actual.getFirst().getDatePosted()).isEqualTo(LocalDateTime.parse("2024-09-26T10:15"));
        }

        @Test
        @DisplayName("Should return a list with multiple items when two posters exist")
        void multipleItemsList(){
            posters.add(poster1);
            posters.add(poster2);
            when(posterRepository.findAll()).thenReturn(posters);
            List<Poster> actual = posterService.getAllPosters();
            assertThat(actual).hasSize(2);

            assertThat(actual.getFirst()).isEqualTo(poster1);
            assertThat(actual.getFirst().getPet()).isEqualTo(pet1);
            assertThat(actual.getFirst().getPet().getOwner()).isEqualTo(owner1);
            assertThat(actual.getFirst().getTitle()).isEqualTo("Missing Tabby Cat - Please Help");
            assertThat(actual.getFirst().getDescription()).isEqualTo("Gray tabby cat with green eyes. Wearing a blue collar with a bell.");
            assertThat(actual.getFirst().getDatePosted()).isEqualTo(LocalDateTime.parse("2024-09-26T10:15"));


            assertThat(actual.get(1)).isEqualTo(poster2);
            assertThat(actual.get(1).getPet()).isEqualTo(pet2);
            assertThat(actual.get(1).getPet().getOwner()).isEqualTo(owner2);
            assertThat(actual.get(1).getTitle()).isEqualTo("Lost Siamese Cat - Reward Offered");
            assertThat(actual.get(1).getDescription()).isEqualTo("Siamese cat with blue eyes. Very vocal and responds to 'Mochi'.");
            assertThat(actual.get(1).getDatePosted()).isEqualTo(LocalDateTime.parse("2024-09-27T13:45"));
        }

    }


}
