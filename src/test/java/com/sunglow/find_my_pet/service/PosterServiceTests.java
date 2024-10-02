package com.sunglow.find_my_pet.service;

import com.sunglow.find_my_pet.exception.ItemNotFoundException;
import com.sunglow.find_my_pet.model.Owner;
import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.repository.OwnerManagerRepository;
import com.sunglow.find_my_pet.repository.PetManagerRepository;
import com.sunglow.find_my_pet.repository.PosterManagerRepository;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PosterServiceTests {

    @Mock
    private PosterManagerRepository posterRepository;
    @Mock
    private OwnerManagerRepository ownerRepository;
    @Mock
    private PetManagerRepository petManagerRepository;

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
            .lostDate(LocalDate.parse("2024-09-02"))
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
            .datePosted(LocalDate.parse("2024-09-26"))
            .description(
                "Gray tabby cat with green eyes. Wearing a blue collar with a bell.")
            .title("Missing Tabby Cat - Please Help")
            .pet(pet1)
            .build();

        pet2 = Pet.builder()
                .id(2L)
            .name("Mochi")
            .colour("Cream")
            .age(2)
            .isFound(false)
            .longitude(-2.2426)
            .latitude(53.4808)
            .imageURL("https://example.com/mochi.jpg")
            .lostDate(LocalDate.parse("2024-08-27"))
            .build();

        owner2 = Owner.builder()
                .id(2L)
            .name("Kevin Lee")
            .contactNumber("0161 496 0735")
            .emailAddress("kevin.l@geemail.com")
            .pet(pet2)
            .build();
        pet2.setOwner(owner2);

        poster2 = Poster.builder()
            .datePosted(LocalDate.parse("2024-09-27"))
            .description("Siamese cat with blue eyes. Very vocal and responds to 'Mochi'.")
            .title("Lost Siamese Cat - Reward Offered")
            .pet(pet2)
            .build();

        posters = new ArrayList<>();
        lenient().when(posterRepository.findAll()).thenReturn(posters);
    }

    @Nested
    class GetAllPosters {

        @Test
        @DisplayName("Should return an empty list when no posters exist")
        void noPostersExist() {
            when(posterRepository.findAll()).thenReturn(posters);
            List<Poster> actual = posterService.getAllPosters();
            assertThat(actual).isEmpty();
        }

        @Test
        @DisplayName("Should return a list with null field when null Model-related field")
        void nullField() {
            Poster posterWithoutPetInfo = Poster.builder()
                .datePosted(LocalDate.parse("2024-09-26"))
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
        void singleItemList() {
            posters.add(poster1);
            when(posterRepository.findAll()).thenReturn(posters);
            List<Poster> actual = posterService.getAllPosters();

            assertThat(actual).hasSize(1);
            assertThat(actual.getFirst()).isEqualTo(poster1);
            assertThat(actual.getFirst().getPet()).isEqualTo(pet1);
            assertThat(actual.getFirst().getPet().getOwner()).isEqualTo(owner1);
            assertThat(actual.getFirst().getTitle()).isEqualTo("Missing Tabby Cat - Please Help");
            assertThat(actual.getFirst().getDescription()).isEqualTo(
                "Gray tabby cat with green eyes. Wearing a blue collar with a bell.");
            assertThat(actual.getFirst().getDatePosted()).isEqualTo(LocalDate.parse("2024-09-26"));
        }

        @Test
        @DisplayName("Should return a list with multiple items when two posters exist")
        void multipleItemsList() {
            posters.add(poster1);
            posters.add(poster2);
            when(posterRepository.findAll()).thenReturn(posters);
            List<Poster> actual = posterService.getAllPosters();
            assertThat(actual).hasSize(2);

            assertThat(actual.getFirst()).isEqualTo(poster1);
            assertThat(actual.getFirst().getPet()).isEqualTo(pet1);
            assertThat(actual.getFirst().getPet().getOwner()).isEqualTo(owner1);
            assertThat(actual.getFirst().getTitle()).isEqualTo("Missing Tabby Cat - Please Help");
            assertThat(actual.getFirst().getDescription()).isEqualTo(
                "Gray tabby cat with green eyes. Wearing a blue collar with a bell.");
            assertThat(actual.getFirst().getDatePosted()).isEqualTo(LocalDate.parse("2024-09-26"));

            assertThat(actual.get(1)).isEqualTo(poster2);
            assertThat(actual.get(1).getPet()).isEqualTo(pet2);
            assertThat(actual.get(1).getPet().getOwner()).isEqualTo(owner2);
            assertThat(actual.get(1).getTitle()).isEqualTo("Lost Siamese Cat - Reward Offered");
            assertThat(actual.get(1).getDescription()).isEqualTo(
                "Siamese cat with blue eyes. Very vocal and responds to 'Mochi'.");
            assertThat(actual.get(1).getDatePosted()).isEqualTo(LocalDate.parse("2024-09-27"));
        }

    }

    @Nested
    class getPostersById {

        @Test
        @DisplayName("Throws an ItemNotFoundException when there is no poster with the specified ID.")
        void testGetPosterByIdDoesNotExist() {
            when(posterRepository.findById(12L)).thenThrow(new ItemNotFoundException("No poster with this ID."));
            assertThrows(ItemNotFoundException.class, () -> posterService.getPosterById(12L));
        }

        @Test
        @DisplayName("Returns the poster with the specified ID.")
        void testGetPosterByIdSingleItem() {
            Poster posterWithId2 = Poster.builder()
                    .id(2L)
                    .datePosted(LocalDate.parse("2024-09-27"))
                    .description("Siamese cat with blue eyes. Very vocal and responds to 'Mochi'.")
                    .title("Lost Siamese Cat - Reward Offered")
                    .pet(pet2)
                    .build();

            when(posterRepository.findById(2L)).thenReturn(Optional.of(posterWithId2));

            Poster foundPoster = posterService.getPosterById(2L);

            assertThat(foundPoster).isNotNull();
            assertThat(foundPoster.getId()).isEqualTo(2L);
            assertThat(foundPoster.getPet()).isEqualTo(pet2);
            assertThat(foundPoster.getPet().getOwner()).isEqualTo(owner2);
            assertThat(foundPoster.getTitle()).isEqualTo("Lost Siamese Cat - Reward Offered");
            assertThat(foundPoster.getDescription()).isEqualTo(
                    "Siamese cat with blue eyes. Very vocal and responds to 'Mochi'.");
            assertThat(foundPoster.getDatePosted()).isEqualTo(LocalDate.parse("2024-09-27"));
        }

        @Test
        @DisplayName("Returns the correct poster by ID when multiple posters exist in the repository.")
        void testGetPosterByIdMultipleItemsUsingFindById() {

            Poster posterWithId1 = Poster.builder()
                    .id(1L)
                    .datePosted(LocalDate.parse("2024-09-26"))
                    .description("Gray tabby cat with green eyes. Wearing a blue collar with a bell.")
                    .title("Missing Tabby Cat - Please Help")
                    .pet(pet1)
                    .build();

            Poster posterWithId2 = Poster.builder()
                    .id(2L)
                    .datePosted(LocalDate.parse("2024-09-27"))
                    .description("Siamese cat with blue eyes. Very vocal and responds to 'Mochi'.")
                    .title("Lost Siamese Cat - Reward Offered")
                    .pet(pet2)
                    .build();

            when(posterRepository.findById(1L)).thenReturn(Optional.of(posterWithId1));
            when(posterRepository.findById(2L)).thenReturn(Optional.of(posterWithId2));

            Poster foundPosterWithId1 = posterService.getPosterById(1L);
            Poster foundPosterWithId2 = posterService.getPosterById(2L);

            assertThat(foundPosterWithId1).isNotNull();
            assertThat(foundPosterWithId1.getId()).isEqualTo(1L);
            assertThat(foundPosterWithId1.getPet()).isEqualTo(pet1);
            assertThat(foundPosterWithId1.getTitle()).isEqualTo("Missing Tabby Cat - Please Help");
            assertThat(foundPosterWithId1.getDescription()).isEqualTo("Gray tabby cat with green eyes. Wearing a blue collar with a bell.");
            assertThat(foundPosterWithId1.getDatePosted()).isEqualTo(LocalDate.parse("2024-09-26"));

            assertThat(foundPosterWithId2).isNotNull();
            assertThat(foundPosterWithId2.getId()).isEqualTo(2L);
            assertThat(foundPosterWithId2.getPet()).isEqualTo(pet2);
            assertThat(foundPosterWithId2.getTitle()).isEqualTo("Lost Siamese Cat - Reward Offered");
            assertThat(foundPosterWithId2.getDescription()).isEqualTo("Siamese cat with blue eyes. Very vocal and responds to 'Mochi'.");
            assertThat(foundPosterWithId2.getDatePosted()).isEqualTo(LocalDate.parse("2024-09-27"));
        }
    }

}
