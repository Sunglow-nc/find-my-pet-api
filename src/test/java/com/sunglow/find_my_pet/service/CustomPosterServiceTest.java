package com.sunglow.find_my_pet.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sunglow.find_my_pet.exception.ItemNotFoundException;
import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.repository.PetManagerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CustomPosterServiceTest {

    @Mock
    private PetManagerRepository petManagerRepository;

    @InjectMocks
    private PetServiceImpl petService;

    @Test
    void findByColourWithExistingPetsShouldReturnListOfPets() {
        // Arrange
        String colour = "black";
        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setColour(colour);
        Pet pet2 = new Pet();
        pet2.setId(2L);
        pet2.setColour(colour);
        List<Pet> expectedPets = Arrays.asList(pet1, pet2);

        when(petManagerRepository.findByColour(colour)).thenReturn(expectedPets);

        // Act
        List<Pet> actualPets = petService.getPetsByColour(colour);

        // Assert
        assertEquals(expectedPets, actualPets);
        verify(petManagerRepository, times(1)).findByColour(colour);
    }

    @Test
    void findByColourWithNoPetsShouldThrowItemNotFoundException() {
        // Arrange
        String colour = "purple";
        when(petManagerRepository.findByColour(colour)).thenReturn(Collections.emptyList());

        // Act & Assert
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
            () -> petService.getPetsByColour(colour));

        assertEquals(String.format("No Pets found with colour %s", colour), exception.getMessage());
        verify(petManagerRepository, times(1)).findByColour(colour);
    }

    @Test
    void findByColourWithNullColourShouldThrowItemNotFoundException() {
        // Arrange
        when(petManagerRepository.findByColour(null)).thenReturn(Collections.emptyList());

        // Act & Assert
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
            () -> petService.getPetsByColour(null));

        assertEquals("No Pets found with colour null", exception.getMessage());
        verify(petManagerRepository, times(1)).findByColour(null);
    }

    @Test
    void findByColourWithEmptyColourShouldThrowItemNotFoundException() {
        // Arrange
        when(petManagerRepository.findByColour("")).thenReturn(Collections.emptyList());

        // Act & Assert
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
            () -> petService.getPetsByColour(""));

        assertEquals("No Pets found with colour ", exception.getMessage());
        verify(petManagerRepository, times(1)).findByColour("");
    }

    @Test
    void findByColourWithWhitespaceColourShouldThrowItemNotFoundException() {
        // Arrange
        when(petManagerRepository.findByColour("   ")).thenReturn(Collections.emptyList());

        // Act & Assert
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
            () -> petService.getPetsByColour("   "));

        assertEquals("No Pets found with colour    ", exception.getMessage());
        verify(petManagerRepository, times(1)).findByColour("   ");
    }

    @Test
    void findByColourWithDifferentCaseShouldBeCaseSensitive() {
        // Arrange
        String colour = "BLACK";
        when(petManagerRepository.findByColour(colour)).thenReturn(Collections.emptyList());

        // Act & Assert
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
            () -> petService.getPetsByColour(colour));

        assertEquals(String.format("No Pets found with colour %s", colour), exception.getMessage());
        verify(petManagerRepository, times(1)).findByColour(colour);
    }

    @Test
    void findByTypeWithExistingPetsShouldReturnListOfPets() {
        // Arrange
        String type = "dog";
        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setType(type);
        Pet pet2 = new Pet();
        pet2.setId(2L);
        pet2.setType(type);
        List<Pet> expectedPets = Arrays.asList(pet1, pet2);

        when(petManagerRepository.findByType(type)).thenReturn(expectedPets);

        // Act
        List<Pet> actualPets = petService.getPetsByType(type);

        // Assert
        assertEquals(expectedPets, actualPets);
        verify(petManagerRepository, times(1)).findByType(type);
    }

    @Test
    void findByTypeWithNoPetsShouldThrowItemNotFoundException() {
        // Arrange
        String type = "unicorn";
        when(petManagerRepository.findByType(type)).thenReturn(Collections.emptyList());

        // Act & Assert
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
            () -> petService.getPetsByType(type));

        assertEquals(String.format("No Pets found with type %s", type), exception.getMessage());
        verify(petManagerRepository, times(1)).findByType(type);
    }

    @Test
    void findByTypeWithNullTypeShouldThrowItemNotFoundException() {
        // Arrange
        when(petManagerRepository.findByType(null)).thenReturn(Collections.emptyList());

        // Act & Assert
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
            () -> petService.getPetsByType(null));

        assertEquals("No Pets found with type null", exception.getMessage());
        verify(petManagerRepository, times(1)).findByType(null);
    }

    @Test
    void findByTypeWithEmptyTypeShouldThrowItemNotFoundException() {
        // Arrange
        when(petManagerRepository.findByType("")).thenReturn(Collections.emptyList());

        // Act & Assert
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
            () -> petService.getPetsByType(""));

        assertEquals("No Pets found with type ", exception.getMessage());
        verify(petManagerRepository, times(1)).findByType("");
    }

    @Test
    void findByTypeWithWhitespaceTypeShouldThrowItemNotFoundException() {
        // Arrange
        when(petManagerRepository.findByType("   ")).thenReturn(Collections.emptyList());

        // Act & Assert
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
            () -> petService.getPetsByType("   "));

        assertEquals("No Pets found with type    ", exception.getMessage());
        verify(petManagerRepository, times(1)).findByType("   ");
    }

    @Test
    void findByTypeWithDifferentCaseShouldBeCaseSensitive() {
        // Arrange
        String type = "DOG";
        when(petManagerRepository.findByType(type)).thenReturn(Collections.emptyList());

        // Act & Assert
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
            () -> petService.getPetsByType(type));

        assertEquals(String.format("No Pets found with type %s", type), exception.getMessage());
        verify(petManagerRepository, times(1)).findByType(type);
    }

}