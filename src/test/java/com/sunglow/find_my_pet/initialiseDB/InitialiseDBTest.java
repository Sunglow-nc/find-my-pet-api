package com.sunglow.find_my_pet.initialiseDB;

import static org.mockito.Mockito.*;

import com.sunglow.find_my_pet.model.Owner;
import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.repository.OwnerManagerRepository;
import com.sunglow.find_my_pet.repository.PetManagerRepository;
import com.sunglow.find_my_pet.repository.PosterManagerRepository;
import java.time.LocalDateTime;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
public class InitialiseDBTest {

    @Autowired
    private InitialiseDB initialiseDB;

    @MockBean
    private PetManagerRepository petRepository;

    @MockBean
    private OwnerManagerRepository ownerRepository;

    @MockBean
    private PosterManagerRepository posterRepository;

    @Before("")
    void setUp(){
        // Verify that deleteAll was called once
        verify(petRepository, times(1)).deleteAll();
        verify(ownerRepository, times(1)).deleteAll();
        verify(posterRepository, times(1)).deleteAll();
        // Verify that save was called 5 times for each repository
        verify(ownerRepository, times(5)).save(any(Owner.class));
        verify(posterRepository, times(5)).save(any(Poster.class));

        // Verify the details of the first owner
        verify(ownerRepository).save(argThat(owner ->
                "Sarah Thompson".equals(owner.getName()) &&
                        "020 7946 0958".equals(owner.getContactNumber()) &&
                        "sarah.t@gmail.com".equals(owner.getEmailAddress()) &&
                        verifyPet(owner.getPet())
        ));

        // Verify the details of the first poster
        verify(posterRepository).save(argThat(poster ->
                LocalDateTime.parse("2024-09-26T10:15").equals(poster.getDatePosted()) &&
                        "Gray tabby cat with green eyes. Wearing a blue collar with a bell.".equals(
                                poster.getDescription()) &&
                        "Missing Tabby Cat - Please Help".equals(poster.getTitle())
        ));

    }

    @Test
    @DisplayName("Test the initialisation of the database")
    public void testPopulateDb() throws Exception {
        initialiseDB.populateDb(petRepository, ownerRepository, posterRepository)
            .run(new String[]{});
    }

    private boolean verifyPet(Pet pet) {
        return "Whiskers".equals(pet.getName()) &&
            "Grey".equals(pet.getColour()) &&
            3 == pet.getAge() &&
            !pet.getIsFound() &&
            -0.1278 == pet.getLongitude() &&
            51.5074 == pet.getLatitude() &&
            "https://example.com/whiskers.jpg".equals(pet.getImageURL()) &&
            LocalDateTime.parse("2024-09-02T22:00").equals(pet.getLostDate());
    }
}