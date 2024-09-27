package com.sunglow.find_my_pet.initialiseDB;

import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.model.Owner;
import com.sunglow.find_my_pet.model.Poster;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class InitialiseDB {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Bean
    CommandLineRunner populateDb() {
        return args -> {
            createPoster("Buddy", "Golden", 3, false, -0.1276, 51.5074,
                "https://example.com/buddy.jpg", "John Smith", "020 7946 0958", "john.smith@gmail.co.uk",
                "Medium-sized dog with a fluffy golden coat, floppy ears, and a bushy tail");

            createPoster("Max", "Black", 5, false, -2.2426, 53.4808,
                "https://example.com/max.jpg", "Emma Jones", "0161 496 0287", "emma.jones@gmail.co.uk",
                "Large dog with sleek black fur, pointy ears, and a white patch on the chest");

            createPoster("Charlie", "Brown", 2, false, -3.1883, 55.9533,
                "https://example.com/charlie.jpg", "Olivia Brown", "0131 496 0236", "olivia.brown@yahoo.co.uk",
                "Small terrier with scruffy brown fur, a black nose, and expressive eyebrows");

            createPoster("Luna", "White", 1, false, -1.8904, 52.4862,
                "https://example.com/luna.jpg", "William Taylor", "0121 496 0685", "william.taylor@yahoo.co.uk",
                "Slender cat with pure white fur, bright blue eyes, and a long, fluffy tail");

            createPoster("Milo", "Orange", 4, false, -2.5879, 51.4545,
                "https://example.com/milo.jpg", "Sophie Evans", "0117 496 0534", "sophie.evans@yahoo.co.uk",
                "Plump tabby cat with distinctive orange and white stripes, green eyes, and a pink nose");
        };
    }

    private void createPoster(String name, String colour, int age, boolean isFound,
        double longitude, double latitude, String imageURL,
        String ownerName, String ownerContact, String ownerEmail, String petDescription) {
        transactionTemplate.execute(status -> {
            Pet pet = Pet.builder()
                .name(name)
                .colour(colour)
                .age(age)
                .isFound(isFound)
                .longitude(longitude)
                .latitude(latitude)
                .imageURL(imageURL)
                .lostDate(LocalDateTime.now().minusDays((long) (Math.random() * 30)))
                .build();

            entityManager.persist(pet);

            Owner owner = Owner.builder()
                .name(ownerName)
                .contactNumber(ownerContact)
                .emailAddress(ownerEmail)
                .pet(pet)
                .build();

            entityManager.persist(owner);

            Poster poster = Poster.builder()
                .datePosted(LocalDateTime.now())
                .description(petDescription)
                .title("Help find " + pet.getName())
                .pet(pet)
                .build();

            entityManager.persist(poster);

            pet.setOwner(owner);
            pet.setPoster(poster);

            entityManager.merge(pet);
            return null;
        });
    }
}