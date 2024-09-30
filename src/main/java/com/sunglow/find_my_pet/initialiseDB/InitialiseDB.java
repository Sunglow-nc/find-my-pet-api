package com.sunglow.find_my_pet.initialiseDB;


import com.sunglow.find_my_pet.model.Owner;
import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.repository.OwnerManagerRepository;
import com.sunglow.find_my_pet.repository.PetManagerRepository;
import com.sunglow.find_my_pet.repository.PosterManagerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class InitialiseDB {

    private boolean initialised = false;

    @Bean(name = "populateDb")
    public CommandLineRunner populateDb(PetManagerRepository petRepository,
        OwnerManagerRepository ownerRepository, PosterManagerRepository posterRepository) {
        return args -> {
            if (!initialised) {
                petRepository.deleteAll();
                ownerRepository.deleteAll();
                posterRepository.deleteAll();

                Pet pet1 = Pet.builder()
                    .name("Whiskers")
                    .colour("Grey")
                    .age(3)
                    .isFound(false)
                    .longitude(-0.1278)
                    .latitude(51.5074)
                    .type("Dog")
                    .imageURL("https://example.com/whiskers.jpg")
                    .lostDate(LocalDateTime.parse("2024-09-02T22:00"))
                    .build();

                Owner owner1 = Owner.builder()
                    .name("Sarah Thompson")
                    .contactNumber("020 7946 0958")
                    .emailAddress("sarah.t@gmail.com")
                    .pet(pet1)
                    .build();

                ownerRepository.save(owner1);

                Poster poster1 = Poster.builder()
                    .datePosted(LocalDateTime.parse("2024-09-26T10:15"))
                    .description(
                        "Gray tabby cat with green eyes. Wearing a blue collar with a bell.")
                    .title("Missing Tabby Cat - Please Help")
                    .pet(pet1)
                    .build();

                posterRepository.save(poster1);

                Pet pet2 = Pet.builder()
                    .name("Mochi")
                    .colour("Cream")
                    .age(2)
                    .isFound(false)
                    .longitude(-2.2426)
                    .latitude(53.4808)
                    .type("Cat")
                    .imageURL("https://example.com/mochi.jpg")
                    .lostDate(LocalDateTime.parse("2024-08-27T08:30"))
                    .build();

                Owner owner2 = Owner.builder()
                    .name("Kevin Lee")
                    .contactNumber("0161 496 0735")
                    .emailAddress("kevin.l@geemail.com")
                    .pet(pet2)
                    .build();

                ownerRepository.save(owner2);

                Poster poster2 = Poster.builder()
                    .datePosted(LocalDateTime.parse("2024-09-27T13:45"))
                    .description("Siamese cat with blue eyes. Very vocal and responds to 'Mochi'.")
                    .title("Lost Siamese Cat - Reward Offered")
                    .pet(pet2)
                    .build();

                posterRepository.save(poster2);

                Pet pet3 = Pet.builder()
                    .name("Fluffy")
                    .colour("Brown")
                    .age(5)
                    .isFound(false)
                    .longitude(-1.4746)
                    .latitude(52.5615)
                    .type("Mouse")
                    .imageURL("https://example.com/fluffy.jpg")
                    .lostDate(LocalDateTime.parse("2024-09-23T11:00"))
                    .build();

                Owner owner3 = Owner.builder()
                    .name("Emma Wilson")
                    .contactNumber("07700 900123")
                    .emailAddress("emma.w@gmail.com")
                    .pet(pet3)
                    .build();

                ownerRepository.save(owner3);

                Poster poster3 = Poster.builder()
                    .datePosted(LocalDateTime.parse("2024-09-24T15:30"))
                    .description(
                        "Large Maine Coon cat with long fur. Very friendly and may approach if called.")
                    .title("Missing Maine Coon - Please Contact If Seen")
                    .pet(pet3)
                    .build();

                posterRepository.save(poster3);

                Pet pet4 = Pet.builder()
                    .name("Buddy")
                    .colour("Golden")
                    .age(4)
                    .isFound(false)
                    .longitude(-3.1883)
                    .latitude(55.9533)
                    .type("Dog")
                    .imageURL("https://example.com/buddy.jpg")
                    .lostDate(LocalDateTime.parse("2024-07-26T18:30"))
                    .build();

                Owner owner4 = Owner.builder()
                    .name("John Smith")
                    .contactNumber("0131 496 0638")
                    .emailAddress("john.smith@yahoo.com")
                    .pet(pet4)
                    .build();

                ownerRepository.save(owner4);

                Poster poster4 = Poster.builder()
                    .datePosted(LocalDateTime.parse("2024-08-04T11:00"))
                    .description(
                        "Friendly golden retriever, responds to 'Buddy'. Last seen near Central Park.")
                    .title("Lost Golden Retriever - Reward Offered")
                    .pet(pet4)
                    .build();

                posterRepository.save(poster4);

                Pet pet5 = Pet.builder()
                    .name("Bella")
                    .colour("White")
                    .age(2)
                    .isFound(false)
                    .longitude(-1.8904)
                    .latitude(52.4862)
                    .type("Bird")
                    .imageURL("https://example.com/bella.jpg")
                    .lostDate(LocalDateTime.parse("2024-09-03T16:00"))
                    .build();

                Owner owner5 = Owner.builder()
                    .name("Sophie Martin")
                    .contactNumber("01632 960983")
                    .emailAddress("sophie.m@yahoo.com")
                    .pet(pet5)
                    .build();

                ownerRepository.save(owner5);

                Poster poster5 = Poster.builder()
                    .datePosted(LocalDateTime.parse("2024-09-09T18:20"))
                    .description("Small white poodle, recently groomed. Wearing a pink bow.")
                    .title("Missing Poodle - Please Call If Seen")
                    .pet(pet5)
                    .build();

                posterRepository.save(poster5);
                initialised = true;
            }
        };
    }

}