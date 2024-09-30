package com.sunglow.find_my_pet.service;

import com.sunglow.find_my_pet.model.Owner;
import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.repository.OwnerManagerRepository;
import com.sunglow.find_my_pet.repository.PetManagerRepository;
import com.sunglow.find_my_pet.repository.PosterManagerRepository;
import com.sunglow.find_my_pet.util.PosterBuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;

import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PosterServiceTest {

    @InjectMocks
    private PosterServiceImpl posterServiceImpl;

    @Mock
    private PosterManagerRepository posterManagerRepository;

    @Mock
    private PetManagerRepository petManagerRepository;

    @Mock
    private OwnerManagerRepository ownerManagerRepository;

    private List<Poster> samplePosters;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        samplePosters = PosterBuilderUtil.buildSamplePosters();
    }

    @Test
    void testPostPoster() {
        Poster poster = samplePosters.getFirst();
        Pet pet = poster.getPet();
        Owner owner = pet.getOwner();

        when(ownerManagerRepository.save(any(Owner.class))).thenReturn(owner);
        when(ownerManagerRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(petManagerRepository.save(any(Pet.class))).thenReturn(pet);
        when(petManagerRepository.findById(pet.getId())).thenReturn(Optional.of(pet));
        when(posterManagerRepository.save(poster)).thenReturn(poster);

        Poster actualResult = posterServiceImpl.insertPoster(poster);

        assertEquals(poster, actualResult);
    }


    @Test
    void testUpdatePosterById() {
        Poster poster = samplePosters.getFirst();
        Pet pet = poster.getPet();
        Owner owner = pet.getOwner();

        Poster lastPoster = samplePosters.getLast();
        Pet lastPet = lastPoster.getPet();
        Owner lastOwner = lastPet.getOwner();

        when(posterManagerRepository.findById(poster.getId())).thenReturn(Optional.of(poster));
        when(ownerManagerRepository.save(any(Owner.class))).thenReturn(owner);
        when(ownerManagerRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(petManagerRepository.save(any(Pet.class))).thenReturn(pet);
        when(petManagerRepository.findById(pet.getId())).thenReturn((Optional.of(pet)));
        when(posterManagerRepository.save(poster)).thenReturn(poster);

        Optional<Poster> actualResult = posterServiceImpl.updatePoster(poster.getId(), lastPoster);

        assertTrue(actualResult.isPresent());

        Poster updatedPoster = actualResult.get();
        assertEquals(lastPoster.getTitle(), updatedPoster.getTitle(), "Title should be updated");
        assertEquals(lastPoster.getDescription(), updatedPoster.getDescription(), "Description should be updated");
        assertEquals(lastPoster.getDatePosted(), updatedPoster.getDatePosted(), "DatePosted should be updated");

        assertEquals(lastPet.getName(), updatedPoster.getPet().getName(), "Pet name should be updated");
        assertEquals(lastPet.getColour(), updatedPoster.getPet().getColour(), "Pet colour should be updated");
        assertEquals(lastOwner.getEmailAddress(), updatedPoster.getPet().getOwner().getEmailAddress(), "Owner email should be updated");
    }

    @Test
    void testUpdatePosterById_whenEmpty() {
        Poster lastPoster = samplePosters.getLast();
        Long posterNotFoundNum = 450L;
        when(posterManagerRepository.findById(posterNotFoundNum)).thenReturn(Optional.empty());

        Optional<Poster> actualResult = posterServiceImpl.updatePoster(posterNotFoundNum, lastPoster);

        assertTrue(actualResult.isEmpty());
    }
}