package com.sunglow.find_my_pet.initialiseDB;


import com.sunglow.find_my_pet.model.Owner;
import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.repository.OwnerManagerRepository;
import com.sunglow.find_my_pet.repository.PetManagerRepository;
import com.sunglow.find_my_pet.repository.PosterManagerRepository;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                    .imageURL(
                        "https://res.cloudinary.com/deloldrn2/image/upload/v1727788303/grey_dog_ueuwyq.png")
                    .lostDate(LocalDate.parse("2024-09-02"))
                    .build();

                Owner owner1 = Owner.builder()
                    .name("Sarah Thompson")
                    .contactNumber("020 7946 0958")
                    .emailAddress("sarah.t@gmail.com")
                    .pet(pet1)
                    .build();

                ownerRepository.save(owner1);

                Poster poster1 = Poster.builder()
                    .datePosted(LocalDate.parse("2024-09-26"))
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
                    .imageURL(
                        "https://res.cloudinary.com/deloldrn2/image/upload/v1727788303/cream-cat_rjtd8t.png")
                    .lostDate(LocalDate.parse("2024-08-27"))
                    .build();

                Owner owner2 = Owner.builder()
                    .name("Kevin Lee")
                    .contactNumber("0161 496 0735")
                    .emailAddress("kevin.l@geemail.com")
                    .pet(pet2)
                    .build();

                ownerRepository.save(owner2);

                Poster poster2 = Poster.builder()
                    .datePosted(LocalDate.parse("2024-09-27"))
                    .description("Siamese cat with blue eyes. Very vocal and responds to 'Mochi'.")
                    .title("Lost Siamese Cat - Reward Offered")
                    .pet(pet2)
                    .build();

                posterRepository.save(poster2);

                Pet pet3 = Pet.builder()
                    .name("Jerry")
                    .colour("Brown")
                    .age(5)
                    .isFound(false)
                    .longitude(-1.4746)
                    .latitude(52.5615)
                    .type("Mouse")
                    .imageURL(
                        "https://res.cloudinary.com/deloldrn2/image/upload/v1727788303/brown_mouse_intdsv.png")
                    .lostDate(LocalDate.parse("2024-09-23"))
                    .build();

                Owner owner3 = Owner.builder()
                    .name("Emma Wilson")
                    .contactNumber("07700 900123")
                    .emailAddress("emma.w@gmail.com")
                    .pet(pet3)
                    .build();

                ownerRepository.save(owner3);

                Poster poster3 = Poster.builder()
                    .datePosted(LocalDate.parse("2024-09-24"))
                    .description(
                        "Large mouse with long fur. Love to hang out with a cat named Tom.")
                    .title("Missing Mouse - Please Contact If Seen")
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
                    .imageURL(
                        "https://res.cloudinary.com/deloldrn2/image/upload/v1727788303/golden_dog_ou3w8u.png")
                    .lostDate(LocalDate.parse("2024-07-26"))
                    .build();

                Owner owner4 = Owner.builder()
                    .name("John Smith")
                    .contactNumber("0131 496 0638")
                    .emailAddress("john.smith@yahoo.com")
                    .pet(pet4)
                    .build();

                ownerRepository.save(owner4);

                Poster poster4 = Poster.builder()
                    .datePosted(LocalDate.parse("2024-08-04"))
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
                    .imageURL(
                        "https://res.cloudinary.com/deloldrn2/image/upload/v1727788301/white_bird_fzaaxv.png")
                    .lostDate(LocalDate.parse("2024-09-03"))
                    .build();

                Owner owner5 = Owner.builder()
                    .name("Sophie Martin")
                    .contactNumber("01632 960983")
                    .emailAddress("sophie.m@yahoo.com")
                    .pet(pet5)
                    .build();

                ownerRepository.save(owner5);

                Poster poster5 = Poster.builder()
                    .datePosted(LocalDate.parse("2024-09-09"))
                    .description("Small white bird, very fluffy and friendly. This bird has a distinct, pure white appearance and might be timid but approachable. If found or spotted, please contact me. Thank you for helping reunite this little one with its family!")
                    .title("Lost White Bird – Small, Fluffy, and Friendly")
                    .pet(pet5)
                    .build();

                posterRepository.save(poster5);

                Pet pet7 = Pet.builder()
                        .name("Shawn")
                        .colour("White")
                        .age(4)
                        .isFound(false)
                        .longitude(-2.5879)
                        .latitude(51.4545)
                        .type("Sheep")
                        .imageURL("https://res.cloudinary.com/deloldrn2/image/upload/v1728380044/Shaun_the_Sheep_yjgfxo.png")
                        .lostDate(LocalDate.parse("2024-09-12"))
                        .build();

                Owner owner7 = Owner.builder()
                        .name("Emma Brown")
                        .contactNumber("07456123789")
                        .emailAddress("emma.brown@yahoo.com")
                        .pet(pet7)
                        .build();

                ownerRepository.save(owner7);

                Poster poster7 = Poster.builder()
                        .datePosted(LocalDate.parse("2024-09-16"))
                        .description("Shawn is a 4-year-old, friendly white sheep. He has a thick, fluffy wool coat and was last seen near Bristol. Shawn might be wandering nearby, grazing. If you spot him or have any information, please contact me. Your help in bringing Shawn home is greatly appreciated!")
                        .title("Lost Sheep – Shawn the Sheep")
                        .pet(pet7)
                        .build();

                posterRepository.save(poster7);

                Pet pet8 = Pet.builder()
                        .name("Flareon")
                        .colour("Crimson Red")
                        .age(5)
                        .isFound(false)
                        .longitude(-2.58670837036)
                        .latitude(51.4489503074)
                        .type("Fire Fox")
                        .imageURL("https://res.cloudinary.com/deloldrn2/image/upload/v1728382042/fox_napsj5.png")
                        .lostDate(LocalDate.parse("2024-09-18"))
                        .build();

                Owner owner8 = Owner.builder()
                        .name("Ash Ketchum")
                        .contactNumber("07123456789")
                        .emailAddress("ash.ketchum@palettown.com")
                        .pet(pet8)
                        .build();

                ownerRepository.save(owner8);

                Poster poster8 = Poster.builder()
                        .datePosted(LocalDate.parse("2024-09-20"))
                        .description("Flareon is a majestic fire fox with a striking crimson red coat and glowing embers at the tip of its tail. Last seen near [location], Flareon is friendly but may cause small sparks when frightened. Please help us find this beloved companion. If you spot Flareon, avoid sudden movements and contact me immediately. We greatly appreciate your help in bringing Flareon home safely!")
                        .title("Lost Fire Fox – Flareon, Crimson Red")
                        .pet(pet8)
                        .build();

                posterRepository.save(poster8);


                initialised = true;
            }
        };
    }

}