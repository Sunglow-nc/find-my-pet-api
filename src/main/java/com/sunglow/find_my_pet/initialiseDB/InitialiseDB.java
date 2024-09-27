package com.sunglow.find_my_pet.initialiseDB;


import com.sunglow.find_my_pet.model.Owner;
import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.repository.OwnerManagerRepository;
import com.sunglow.find_my_pet.repository.PetManagerRepository;
import com.sunglow.find_my_pet.repository.PosterManagerRespository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class InitialiseDB {

    @Bean(name = "demoDataLoader")
    public CommandLineRunner demoDataLoader(PetManagerRepository petRepository, OwnerManagerRepository ownerRepository, PosterManagerRespository posterRepository) {
        return args -> {
            petRepository.deleteAll();
            ownerRepository.deleteAll();
            posterRepository.deleteAll();

            Pet pet1 = Pet.builder()
                    .name("Buddy")
                    .colour("Brown")
                    .age(4)
                    .isFound(false)
                    .longitude(37.7749)
                    .latitude(-122.4194)
                    .imageURL("https://example.com/buddy.jpg")
                    .lostDate(LocalDateTime.of(2024, 9, 26, 10, 30))
                    .build();

            pet1 = petRepository.save(pet1);

            Owner owner1 = Owner.builder()
                    .name("John Doe")
                    .contactNumber("123-456-7890")
                    .emailAddress("john.doe@example.com")
                    .pet(pet1)
                    .build();

            owner1 = ownerRepository.save(owner1);

            Poster poster1 = Poster.builder()
                    .datePosted(LocalDateTime.of(2024, 9, 25, 14, 0))
                    .description("Lost brown dog near Golden Gate Park.")
                    .title("Lost Dog: Buddy")
                    .pet(pet1)
                    .build();

            posterRepository.save(poster1);
        };
    }

}