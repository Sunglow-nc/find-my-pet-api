package com.sunglow.find_my_pet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sunglow.find_my_pet.exception.GlobalExceptionHandler;
import com.sunglow.find_my_pet.exception.ItemNotFoundException;
import com.sunglow.find_my_pet.model.Owner;
import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.service.PosterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.closeTo;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
class PosterControllerAllAndByIdTest {

    @Mock
    private PosterServiceImpl mockPosterServiceImpl;

    @InjectMocks
    private PosterController posterController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(posterController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    //Testing getAllPosters
    @Test
    void testGetAllPostersWhenEmpty() throws Exception {

        List<Poster> posters = new ArrayList<>();

        when(mockPosterServiceImpl.getAllPosters()).thenReturn(posters);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/posters"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].datePosted").doesNotExist());
    }

    @Test
    void testGetAllPostersWithNullValues() throws Exception {

        List<Poster> posters = new ArrayList<>();

        // Poster 1
        Pet pet1 = Pet.builder()
                .id(1L)
                .name("Buddy")
                .colour("Golden")
                .age(3)
                .isFound(false)
                .longitude(-1.234567)
                .latitude(53.456789)
                .imageURL("https://example.com/buddy.jpg")
                .lostDate(LocalDateTime.of(2024, 3, 15, 14, 30))
                .build();

        Owner owner1 = Owner.builder()
                .id(1L)
                .name("John Doe")
                .contactNumber("+1234567890")
                .emailAddress("john.doe@example.com")
                .pet(pet1)
                .build();

        pet1.setOwner(owner1);

        Poster poster1 = Poster.builder()
                .id(1L)
                .datePosted(LocalDateTime.of(2024, 4, 6, 10, 30))
                .description("")
                .title("Missing Dog: Buddy")
                .pet(pet1)
                .build();


        // Poster 2
        Pet pet2 = Pet.builder()
                .id(2L)
                .name("Luna")
                .colour("Black")
                .age(2)
                .isFound(false)
                .longitude(-0.987654)
                .latitude(52.123456)
                .imageURL("https://example.com/luna.jpg")
                .lostDate(LocalDateTime.of(2023, 12, 25, 12, 00))
                .build();

        Owner owner2 = Owner.builder()
                .id(2L)
                .name("Jane Smith")
                .contactNumber("+0987654321")
                .emailAddress("jane.smith@example.com")
                .pet(pet2)
                .build();

        pet2.setOwner(owner2);

        Poster poster2 = Poster.builder()
                .id(2L)
                .datePosted(LocalDateTime.of(2024, 1, 1, 8, 45))
                .description("Black cat with a white spot on the chest, lost on New Year's Eve.")
                .title("Lost Cat: Luna")
                .pet(pet2)
                .build();


        // Poster 3
        Pet pet3 = Pet.builder()
                .id(3L)
                .name("Max")
                .colour("Brown and White")
                .age(5)
                .isFound(true)
                .longitude(-2.345678)
                .latitude(54.123456)
                .imageURL("https://example.com/max.jpg")
                .lostDate(LocalDateTime.of(2024, 5, 1, 9, 00))
                .build();

        Owner owner3 = Owner.builder()
                .id(3L)
                .name("Sam Wilson")
                .contactNumber("+1122334455")
                .emailAddress("sam.wilson@example.com")
                .pet(pet3)
                .build();

        pet3.setOwner(owner3);

        Poster poster3 = Poster.builder()
                .id(3L)
                .datePosted(LocalDateTime.of(2024, 5, 2, 15, 00))
                .description("Found a brown and white dog near the riverbank. Very playful and healthy.")
                .title("Found Dog: Max")
                .pet(pet3)
                .build();

        posters.add(poster1);
        posters.add(poster2);
        posters.add(poster3);

        System.out.println(mapper.writeValueAsString(posters));

        when(mockPosterServiceImpl.getAllPosters()).thenReturn(posters);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/posters"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Missing Dog: Buddy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].datePosted").value("2024-04-06 10:30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.colour").value("Golden"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.owner.emailAddress").value("john.doe@example.com"));
    }

    @Test
    void testGetAllPosters() throws Exception {

        List<Poster> posters = new ArrayList<>();

        // Poster 1
        Pet pet1 = Pet.builder()
                .id(1L)
                .name("Buddy")
                .colour("Golden")
                .age(3)
                .isFound(false)
                .longitude(-1.234567)
                .latitude(53.456789)
                .imageURL("https://example.com/buddy.jpg")
                .lostDate(LocalDateTime.of(2024, 3, 15, 14, 30))
                .build();

        Owner owner1 = Owner.builder()
                .id(1L)
                .name("John Doe")
                .contactNumber("+1234567890")
                .emailAddress("john.doe@example.com")
                .pet(pet1)
                .build();

        pet1.setOwner(owner1);

        Poster poster1 = Poster.builder()
                .id(1L)
                .datePosted(LocalDateTime.of(2024, 4, 6, 10, 30))
                .description("Golden retriever, very friendly, lost near the park.")
                .title("Missing Dog: Buddy")
                .pet(pet1)
                .build();


        // Poster 2
        Pet pet2 = Pet.builder()
                .id(2L)
                .name("Luna")
                .colour("Black")
                .age(2)
                .isFound(false)
                .longitude(-0.987654)
                .latitude(52.123456)
                .imageURL("https://example.com/luna.jpg")
                .lostDate(LocalDateTime.of(2023, 12, 25, 12, 00))
                .build();

        Owner owner2 = Owner.builder()
                .id(2L)
                .name("Jane Smith")
                .contactNumber("+0987654321")
                .emailAddress("jane.smith@example.com")
                .pet(pet2)
                .build();

        pet2.setOwner(owner2);

        Poster poster2 = Poster.builder()
                .id(2L)
                .datePosted(LocalDateTime.of(2024, 1, 1, 8, 45))
                .description("Black cat with a white spot on the chest, lost on New Year's Eve.")
                .title("Lost Cat: Luna")
                .pet(pet2)
                .build();


        // Poster 3
        Pet pet3 = Pet.builder()
                .id(3L)
                .name("Max")
                .colour("Brown and White")
                .age(5)
                .isFound(true)
                .longitude(-2.345678)
                .latitude(54.123456)
                .imageURL("https://example.com/max.jpg")
                .lostDate(LocalDateTime.of(2024, 5, 1, 9, 00))
                .build();

        Owner owner3 = Owner.builder()
                .id(3L)
                .name("Sam Wilson")
                .contactNumber("+1122334455")
                .emailAddress("sam.wilson@example.com")
                .pet(pet3)
                .build();

        pet3.setOwner(owner3);

        Poster poster3 = Poster.builder()
                .id(3L)
                .datePosted(LocalDateTime.of(2024, 5, 2, 15, 00))
                .description("Found a brown and white dog near the riverbank. Very playful and healthy.")
                .title("Found Dog: Max")
                .pet(pet3)
                .build();

        posters.add(poster1);
        posters.add(poster2);
        posters.add(poster3);

        System.out.println(mapper.writeValueAsString(posters));

        when(mockPosterServiceImpl.getAllPosters()).thenReturn(posters);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/posters"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Missing Dog: Buddy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].datePosted").value("2024-04-06 10:30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Golden retriever, very friendly, lost near the park."))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.colour").value("Golden"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.owner.emailAddress").value("john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].pet.isFound").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].pet.latitude").value(closeTo(52.123456, 0.000001)));
    }

    //Testing getPostersById
    @Test
    void testGetPosterByIdWhenEmpty() throws Exception {

        when(mockPosterServiceImpl.getPosterById(1L)).thenThrow(new ItemNotFoundException("Poster not found for ID :" + 1L));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/posters/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].datePosted").doesNotExist());
    }

    @Test
    void testGetPosterByIdWithNullValues() throws Exception {

        List<Poster> posters = new ArrayList<>();

        // Poster 1
        Pet pet1 = Pet.builder()
                .id(1L)
                .name("Buddy")
                .colour("Golden")
                .age(3)
                .isFound(false)
                .longitude(-1.234567)
                .latitude(53.456789)
                .imageURL("https://example.com/buddy.jpg")
                .lostDate(LocalDateTime.of(2024, 3, 15, 14, 30))
                .build();

        Owner owner1 = Owner.builder()
                .id(1L)
                .name("John Doe")
                .contactNumber("+1234567890")
                .emailAddress("john.doe@example.com")
                .pet(pet1)
                .build();

        pet1.setOwner(owner1);

        Poster poster1 = Poster.builder()
                .id(1L)
                .datePosted(LocalDateTime.of(2024, 4, 6, 10, 30))
                .description("")
                .title("Missing Dog: Buddy")
                .pet(pet1)
                .build();


        // Poster 2
        Pet pet2 = Pet.builder()
                .id(2L)
                .name("Luna")
                .colour("Black")
                .age(2)
                .isFound(false)
                .longitude(-0.987654)
                .latitude(52.123456)
                .imageURL("https://example.com/luna.jpg")
                .lostDate(LocalDateTime.of(2023, 12, 25, 12, 00))
                .build();

        Owner owner2 = Owner.builder()
                .id(2L)
                .name("Jane Smith")
                .contactNumber("+0987654321")
                .emailAddress("jane.smith@example.com")
                .pet(pet2)
                .build();

        pet2.setOwner(owner2);

        Poster poster2 = Poster.builder()
                .id(2L)
                .description("Black cat with a white spot on the chest, lost on New Year's Eve.")
                .title("Lost Cat: Luna")
                .pet(pet2)
                .build();


        // Poster 3
        Pet pet3 = Pet.builder()
                .id(3L)
                .name("Max")
                .colour("Brown and White")
                .age(5)
                .isFound(true)
                .longitude(-2.345678)
                .latitude(54.123456)
                .imageURL("https://example.com/max.jpg")
                .lostDate(LocalDateTime.of(2024, 5, 1, 9, 00))
                .build();

        Owner owner3 = Owner.builder()
                .id(3L)
                .name("Sam Wilson")
                .contactNumber("+1122334455")
                .emailAddress("sam.wilson@example.com")
                .pet(pet3)
                .build();

        pet3.setOwner(owner3);

        Poster poster3 = Poster.builder()
                .id(3L)
                .datePosted(LocalDateTime.of(2024, 5, 2, 15, 00))
                .description("Found a brown and white dog near the riverbank. Very playful and healthy.")
                .title("Found Dog: Max")
                .pet(pet3)
                .build();

        posters.add(poster1);
        posters.add(poster2);
        posters.add(poster3);

        when(mockPosterServiceImpl.getPosterById(2L)).thenReturn(posters.get(1));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/posters/2"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Lost Cat: Luna"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.datePosted").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Black cat with a white spot on the chest, lost on New Year's Eve."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pet.colour").value("Black"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pet.owner.emailAddress").value("jane.smith@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pet.isFound").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pet.longitude").value(closeTo(-0.987654, 0.000001)));
    }

    @Test
    void testGetPosterById() throws Exception {

        List<Poster> posters = new ArrayList<>();

        // Poster 1
        Pet pet1 = Pet.builder()
                .id(1L)
                .name("Buddy")
                .colour("Golden")
                .age(3)
                .isFound(false)
                .longitude(-1.234567)
                .latitude(53.456789)
                .imageURL("https://example.com/buddy.jpg")
                .lostDate(LocalDateTime.of(2024, 3, 15, 14, 30))
                .build();

        Owner owner1 = Owner.builder()
                .id(1L)
                .name("John Doe")
                .contactNumber("+1234567890")
                .emailAddress("john.doe@example.com")
                .pet(pet1)
                .build();

        pet1.setOwner(owner1);

        Poster poster1 = Poster.builder()
                .id(1L)
                .datePosted(LocalDateTime.of(2024, 4, 6, 10, 30))
                .description("Golden retriever, very friendly, lost near the park.")
                .title("Missing Dog: Buddy")
                .pet(pet1)
                .build();


        // Poster 2
        Pet pet2 = Pet.builder()
                .id(2L)
                .name("Luna")
                .colour("Black")
                .age(2)
                .isFound(false)
                .longitude(-0.987654)
                .latitude(52.123456)
                .imageURL("https://example.com/luna.jpg")
                .lostDate(LocalDateTime.of(2023, 12, 25, 12, 00))
                .build();

        Owner owner2 = Owner.builder()
                .id(2L)
                .name("Jane Smith")
                .contactNumber("+0987654321")
                .emailAddress("jane.smith@example.com")
                .pet(pet2)
                .build();

        pet2.setOwner(owner2);

        Poster poster2 = Poster.builder()
                .id(2L)
                .datePosted(LocalDateTime.of(2024, 1, 1, 8, 45))
                .description("Black cat with a white spot on the chest, lost on New Year's Eve.")
                .title("Lost Cat: Luna")
                .pet(pet2)
                .build();


        // Poster 3
        Pet pet3 = Pet.builder()
                .id(3L)
                .name("Max")
                .colour("Brown and White")
                .age(5)
                .isFound(true)
                .longitude(-2.345678)
                .latitude(54.123456)
                .imageURL("https://example.com/max.jpg")
                .lostDate(LocalDateTime.of(2024, 5, 1, 9, 00))
                .build();

        Owner owner3 = Owner.builder()
                .id(3L)
                .name("Sam Wilson")
                .contactNumber("+1122334455")
                .emailAddress("sam.wilson@example.com")
                .pet(pet3)
                .build();

        pet3.setOwner(owner3);

        Poster poster3 = Poster.builder()
                .id(3L)
                .datePosted(LocalDateTime.of(2024, 5, 2, 15, 00))
                .description("Found a brown and white dog near the riverbank. Very playful and healthy.")
                .title("Found Dog: Max")
                .pet(pet3)
                .build();

        posters.add(poster1);
        posters.add(poster2);
        posters.add(poster3);

        when(mockPosterServiceImpl.getPosterById(3L)).thenReturn(posters.get(2));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/posters/3"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Found Dog: Max"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.datePosted").value("2024-05-02 15:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Found a brown and white dog near the riverbank. Very playful and healthy."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pet.colour").value("Brown and White"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pet.owner.emailAddress").value("sam.wilson@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pet.isFound").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pet.longitude").value(closeTo(-2.345678, 0.000001)));
    }
}