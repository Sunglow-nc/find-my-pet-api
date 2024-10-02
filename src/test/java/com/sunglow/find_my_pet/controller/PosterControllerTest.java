package com.sunglow.find_my_pet.controller;

import static org.hamcrest.Matchers.closeTo;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sunglow.find_my_pet.exception.GlobalExceptionHandler;
import com.sunglow.find_my_pet.exception.ItemNotFoundException;
import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.service.PosterServiceImpl;
import com.sunglow.find_my_pet.util.PosterBuilderUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@AutoConfigureMockMvc
@SpringBootTest
class PosterControllerTest {

    @Mock
    private PosterServiceImpl mockPosterServiceImpl;

    @InjectMocks
    private PosterController posterController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;
    private List<Poster> samplePosters;

    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(posterController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        samplePosters = PosterBuilderUtil.buildSamplePosters();
    }

    // Testing getAllPosters
    @Test
    void testGetAllPostersWhenEmpty() throws Exception {
        List<Poster> emptyPosters = new ArrayList<>();
        when(mockPosterServiceImpl.getAllPosters()).thenReturn(emptyPosters);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/posters"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].datePosted").doesNotExist());
    }

    @Test
    void testGetAllPosters() throws Exception {
        when(mockPosterServiceImpl.getAllPosters()).thenReturn(samplePosters);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/posters"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Missing Dog: Buddy"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].datePosted").value("2024-04-06"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].description")
                .value("Golden retriever, very friendly, lost near the park."))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.colour").value("Golden"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.owner.emailAddress")
                .value("john.doe@example.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[2].pet.isFound").value("true"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].pet.latitude")
                .value(closeTo(52.123456, 0.000001)));
    }

    // Testing getPostersById
    @Test
    void testGetPosterByIdWhenEmpty() throws Exception {
        when(mockPosterServiceImpl.getPosterById(1L)).thenThrow(
            new ItemNotFoundException("Poster not found for ID :" + 1L));

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/posters/id/1"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].datePosted").doesNotExist());
    }

    @Test
    void testGetPosterById() throws Exception {
        when(mockPosterServiceImpl.getPosterById(3L)).thenReturn(samplePosters.get(2));

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/posters/id/3"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Found Dog: Max"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.datePosted").value("2024-05-02"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                .value("Found a brown and white dog near the riverbank. Very playful and healthy."))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pet.colour").value("Brown and White"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pet.owner.emailAddress")
                .value("sam.wilson@example.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pet.isFound").value("true"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pet.longitude")
                .value(closeTo(-2.345678, 0.000001)));
    }

    @Test
    void testPostPoster() throws Exception {

        Poster newPoster = samplePosters.getFirst();

        when(mockPosterServiceImpl.insertPoster(any(newPoster.getClass()))).thenReturn(newPoster);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.post("/api/v1/posters")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(newPoster)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(newPoster.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(newPoster.getTitle()))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value(newPoster.getDescription()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pet.colour")
                .value(newPoster.getPet().getColour()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pet.owner.emailAddress")
                .value(newPoster.getPet().getOwner().getEmailAddress()));

    }

    @Test
    void testUpdateExistingPoster() throws Exception {
        Poster firstPoster = samplePosters.getFirst();

        Poster updatedPoster = firstPoster.toBuilder()
            .title("Updated Title for Second Poster")
            .description("Updated description for the second poster.")
            .build();

        when(mockPosterServiceImpl.updatePoster(anyLong(), any(firstPoster.getClass()))).thenReturn(
            Optional.ofNullable(updatedPoster));

        assert updatedPoster != null;
        this.mockMvcController.perform(
                MockMvcRequestBuilders.put("/api/v1/posters/" + firstPoster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(updatedPoster)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updatedPoster.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(updatedPoster.getTitle()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                .value(updatedPoster.getDescription()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pet.colour")
                .value(updatedPoster.getPet().getColour()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pet.owner.emailAddress")
                .value(updatedPoster.getPet().getOwner().getEmailAddress()));

    }

    @Test
    void testCannotUpdatePoster_notFound() throws Exception {

        when(mockPosterServiceImpl.updatePoster(eq(999L), any())).thenReturn(Optional.empty());

        this.mockMvcController.perform(
                MockMvcRequestBuilders.put("/api/v1/posters/" + 999L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(new Poster())))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andReturn();
    }

    @Test
    void testDeletePosterByIdWhenEmpty() throws Exception {
        doThrow(new ItemNotFoundException("The poster with the specified ID does not exist."))
            .when(mockPosterServiceImpl).deletePosterById(999L);

        this.mockMvcController.perform(MockMvcRequestBuilders.delete("/api/v1/posters/999"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content()
                .string("The poster with the specified ID does not exist."));
    }

    @Test
    void testDeletePosterById() throws Exception {
        doNothing().when(mockPosterServiceImpl).deletePosterById(3L);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.delete("/api/v1/posters/3"))
            .andExpect(MockMvcResultMatchers.status().isNoContent())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$.datePosted").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$.pet.colour").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$.pet.owner.emailAddress").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$.pet.isFound").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$.pet.longitude").doesNotExist());
    }

    @Test
    void testGetPostersByPetColourWhenNonExistent() throws Exception {
        doThrow(new ItemNotFoundException("There is no poster with a pet of this colour."))
            .when(mockPosterServiceImpl).getPostersByPetColour("Forest green");

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/posters/colour/Forest green"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content()
                .string("There is no poster with a pet of this colour."));
    }

    @Test
    void testGetPostersByPetColour() throws Exception {
        List<Poster> validPosters = List.of(samplePosters.get(2));

        when(mockPosterServiceImpl.getPostersByPetColour("Brown and White")).thenReturn(
            validPosters);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/posters/colour/Brown and White"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(3))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Found Dog: Max"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].datePosted").value("2024-05-02"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].description")
                .value("Found a brown and white dog near the riverbank. Very playful and healthy."))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.colour").value("Brown and White"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.owner.emailAddress")
                .value("sam.wilson@example.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.isFound").value("true"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.longitude")
                .value(closeTo(-2.345678, 0.000001)));
    }

    @Test
    void testGetPostersByPetColourMultiplePosters() throws Exception {
        List<Poster> validPosters = List.of(samplePosters.get(1), samplePosters.get(3));

        when(mockPosterServiceImpl.getPostersByPetColour("Black")).thenReturn(validPosters);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/posters/colour/Black"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Lost Cat: Luna"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].datePosted").value("2024-01-01"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].description")
                .value("Black cat with a white spot on the chest, lost on New Year's Eve."))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.colour").value("Black"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.owner.emailAddress")
                .value("jane.smith@example.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.isFound").value("false"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.longitude")
                .value(closeTo(-0.987654, 0.000001)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(4))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Found dog: Age"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].datePosted").value("2024-10-31"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].description")
                .value("Black labrador, very energetic found around Halloween time."))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].pet.colour").value("Black"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].pet.owner.emailAddress")
                .value("jane.smith@example.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].pet.isFound").value("true"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].pet.longitude")
                .value(closeTo(-0.987654, 0.000001)));
    }

    @Test
    void testGetPostersByPetTypeWhenNonExistent() throws Exception {
        doThrow(new ItemNotFoundException("There is no poster with a pet of this type."))
            .when(mockPosterServiceImpl).getPostersByPetType("Marsupial");

        this.mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/posters/type/Marsupial"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content()
                .string("There is no poster with a pet of this type."));
    }

    @Test
    void testGetPostersByPetType() throws Exception {
        List<Poster> validPosters = List.of(samplePosters.get(1));

        when(mockPosterServiceImpl.getPostersByPetType("Cat")).thenReturn(validPosters);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/posters/type/Cat"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Lost Cat: Luna"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].datePosted").value("2024-01-01"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].description")
                .value("Black cat with a white spot on the chest, lost on New Year's Eve."))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.colour").value("Black"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.owner.emailAddress")
                .value("jane.smith@example.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.isFound").value("false"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.longitude")
                .value(closeTo(-0.987654, 0.000001)));
    }

    @Test
    void testGetPostersByPetTypeMultiplePosters() throws Exception {
        List<Poster> validPosters = List.of(samplePosters.get(0), samplePosters.get(3));

        when(mockPosterServiceImpl.getPostersByPetType("Dog")).thenReturn(validPosters);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/posters/type/Dog"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Missing Dog: Buddy"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].datePosted").value("2024-04-06"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].description")
                .value("Golden retriever, very friendly, lost near the park."))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.colour").value("Golden"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.owner.emailAddress")
                .value("john.doe@example.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.isFound").value("false"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].pet.longitude")
                .value(closeTo(-1.234567, 0.000001)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(4))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Found dog: Age"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].datePosted").value("2024-10-31"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].description")
                .value("Black labrador, very energetic found around Halloween time."))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].pet.colour").value("Black"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].pet.owner.emailAddress")
                .value("jane.smith@example.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].pet.isFound").value("true"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].pet.longitude")
                .value(closeTo(-0.987654, 0.000001)));
    }

}