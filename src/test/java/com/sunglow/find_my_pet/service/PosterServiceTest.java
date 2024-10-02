package com.sunglow.find_my_pet.service;

import com.sunglow.find_my_pet.exception.ItemNotFoundException;
import com.sunglow.find_my_pet.model.Owner;
import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.repository.OwnerManagerRepository;
import com.sunglow.find_my_pet.repository.PetManagerRepository;
import com.sunglow.find_my_pet.repository.PosterManagerRepository;
import com.sunglow.find_my_pet.util.PosterBuilderUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PosterServiceTest {

    @Mock
    private PosterManagerRepository posterRepository;

    @Mock
    private PetManagerRepository petManagerRepository;

    @Mock
    private OwnerManagerRepository ownerManagerRepository;

    @InjectMocks
    private PosterServiceImpl posterService;

    private List<Poster> samplePosters;

    @BeforeEach
    public void setup() {
        samplePosters = PosterBuilderUtil.buildSamplePosters();
    }

    @Nested
    class GetAllPosters {

        @Test
        @DisplayName("Should return an empty list when no posters exist")
        void noPostersExist() {
            when(posterRepository.findAll()).thenReturn(new ArrayList<>());
            List<Poster> actual = posterService.getAllPosters();
            assertThat(actual).isEmpty();
        }

        @Test
        @DisplayName("Should return a list with null field when null Model-related field")
        void nullField() {
            Poster posterWithoutPetInfo = Poster.builder()
                .datePosted(LocalDate.parse("2024-09-26"))
                .description("Test description")
                .title("Test Title")
                .build();
            List<Poster> testList = new ArrayList<>();
            testList.add(posterWithoutPetInfo);
            when(posterRepository.findAll()).thenReturn(testList);
            List<Poster> actual = posterService.getAllPosters();
            assertThat(actual.getFirst().getPet()).isNull();
        }

        @Test
        @DisplayName("Should return a list with one item when only one poster exists")
        void singleItemList() {
            List<Poster> singlePosterList = new ArrayList<>();
            singlePosterList.add(samplePosters.get(0));
            when(posterRepository.findAll()).thenReturn(singlePosterList);
            List<Poster> actual = posterService.getAllPosters();

            assertThat(actual).hasSize(1);
            Poster poster = actual.getFirst();
            assertThat(poster.getTitle()).isEqualTo("Missing Dog: Buddy");
            assertThat(poster.getDescription()).isEqualTo(
                "Golden retriever, very friendly, lost near the park.");
            assertThat(poster.getDatePosted()).isEqualTo(LocalDate.parse("2024-04-06"));
            assertThat(poster.getPet().getName()).isEqualTo("Buddy");
            assertThat(poster.getPet().getOwner().getName()).isEqualTo("John Doe");
        }

        @Test
        @DisplayName("Should return a list with multiple items when multiple posters exist")
        void multipleItemsList() {
            when(posterRepository.findAll()).thenReturn(samplePosters);
            List<Poster> actual = posterService.getAllPosters();
            assertThat(actual).hasSize(4);

            // Check first poster
            Poster firstPoster = actual.get(0);
            assertThat(firstPoster.getTitle()).isEqualTo("Missing Dog: Buddy");
            assertThat(firstPoster.getDescription()).isEqualTo(
                "Golden retriever, very friendly, lost near the park.");
            assertThat(firstPoster.getDatePosted()).isEqualTo(LocalDate.parse("2024-04-06"));

            // Check second poster
            Poster secondPoster = actual.get(1);
            assertThat(secondPoster.getTitle()).isEqualTo("Lost Cat: Luna");
            assertThat(secondPoster.getDescription()).isEqualTo(
                "Black cat with a white spot on the chest, lost on New Year's Eve.");
            assertThat(secondPoster.getDatePosted()).isEqualTo(LocalDate.parse("2024-01-01"));
        }
    }

    @Nested
    class GetPostersById {

        @Test
        @DisplayName("Throws an ItemNotFoundException when there is no poster with the specified ID.")
        void testGetPosterByIdDoesNotExist() {
            when(posterRepository.findById(99L)).thenReturn(Optional.empty());
            assertThrows(ItemNotFoundException.class, () -> posterService.getPosterById(99L));
        }

        @Test
        @DisplayName("Returns the poster with the specified ID.")
        void testGetPosterByIdSingleItem() {
            Poster expectedPoster = samplePosters.get(1); // Get the second poster
            when(posterRepository.findById(2L)).thenReturn(Optional.of(expectedPoster));

            Poster foundPoster = posterService.getPosterById(2L);

            assertThat(foundPoster).isNotNull();
            assertThat(foundPoster.getId()).isEqualTo(2L);
            assertThat(foundPoster.getTitle()).isEqualTo("Lost Cat: Luna");
            assertThat(foundPoster.getDescription()).isEqualTo(
                "Black cat with a white spot on the chest, lost on New Year's Eve.");
            assertThat(foundPoster.getDatePosted()).isEqualTo(LocalDate.parse("2024-01-01"));
            assertThat(foundPoster.getPet().getName()).isEqualTo("Luna");
            assertThat(foundPoster.getPet().getOwner().getName()).isEqualTo("Jane Smith");
        }

        @Test
        @DisplayName("Returns the correct poster by ID when multiple posters exist in the repository.")
        void testGetPosterByIdMultipleItemsUsingFindById() {
            when(posterRepository.findById(1L)).thenReturn(Optional.of(samplePosters.get(0)));
            when(posterRepository.findById(3L)).thenReturn(Optional.of(samplePosters.get(2)));

            Poster foundPosterWithId1 = posterService.getPosterById(1L);
            Poster foundPosterWithId3 = posterService.getPosterById(3L);

            assertThat(foundPosterWithId1).isNotNull();
            assertThat(foundPosterWithId1.getId()).isEqualTo(1L);
            assertThat(foundPosterWithId1.getTitle()).isEqualTo("Missing Dog: Buddy");
            assertThat(foundPosterWithId1.getDescription()).isEqualTo(
                "Golden retriever, very friendly, lost near the park.");
            assertThat(foundPosterWithId1.getDatePosted()).isEqualTo(LocalDate.parse("2024-04-06"));

            assertThat(foundPosterWithId3).isNotNull();
            assertThat(foundPosterWithId3.getId()).isEqualTo(3L);
            assertThat(foundPosterWithId3.getTitle()).isEqualTo("Found Dog: Max");
            assertThat(foundPosterWithId3.getDescription()).isEqualTo(
                "Found a brown and white dog near the riverbank. Very playful and healthy.");
            assertThat(foundPosterWithId3.getDatePosted()).isEqualTo(LocalDate.parse("2024-05-02"));
        }
    }


    @Nested
    class InsertPoster {

        @Test
        @DisplayName("Should successfully insert a new poster")
        void testPostPoster() {
            Poster poster = samplePosters.getFirst();
            Pet pet = poster.getPet();
            Owner owner = pet.getOwner();

            when(ownerManagerRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
            when(petManagerRepository.findById(pet.getId())).thenReturn(Optional.of(pet));
            when(posterRepository.save(any(Poster.class))).thenReturn(poster);

            Poster actualResult = posterService.insertPoster(poster);

            assertEquals(poster, actualResult);
            verify(ownerManagerRepository).findById(owner.getId());
            verify(petManagerRepository).findById(pet.getId());
            verify(posterRepository).save(any(Poster.class));
        }
    }

    @Nested
    class UpdatePoster {

        @Test
        @DisplayName("Should successfully update an existing poster")
        void testUpdatePosterById() {
            Poster existingPoster = samplePosters.getFirst();
            Poster updatedPoster = samplePosters.getLast();

            when(posterRepository.findById(existingPoster.getId())).thenReturn(
                Optional.of(existingPoster));
            when(posterRepository.save(any(Poster.class))).thenReturn(updatedPoster);

            Optional<Poster> actualResult = posterService.updatePoster(existingPoster.getId(),
                updatedPoster);

            assertTrue(actualResult.isPresent());
            Poster result = actualResult.get();
            assertEquals(updatedPoster.getTitle(), result.getTitle(), "Title should be updated");
            assertEquals(updatedPoster.getDescription(), result.getDescription(),
                "Description should be updated");
            assertEquals(updatedPoster.getDatePosted(), result.getDatePosted(),
                "DatePosted should be updated");

            verify(posterRepository).findById(existingPoster.getId());
            verify(posterRepository).save(any(Poster.class));
        }

        @Test
        @DisplayName("Should return empty Optional when updating non-existent poster")
        void testUpdatePosterById_whenEmpty() {
            Poster updatedPoster = samplePosters.getLast();
            Long nonExistentId = 450L;
            when(posterRepository.findById(nonExistentId)).thenReturn(Optional.empty());

            Optional<Poster> actualResult = posterService.updatePoster(nonExistentId,
                updatedPoster);

            assertTrue(actualResult.isEmpty());
            verify(posterRepository).findById(nonExistentId);
            verifyNoMoreInteractions(posterRepository, ownerManagerRepository,
                petManagerRepository);
        }
    }
}