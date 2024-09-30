package com.sunglow.find_my_pet.controller;

import static org.hamcrest.Matchers.closeTo;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunglow.find_my_pet.exception.GlobalExceptionHandler;
import com.sunglow.find_my_pet.exception.ItemNotFoundException;
import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.service.PosterServiceImpl;
import com.sunglow.find_my_pet.util.PosterBuilderUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].datePosted").value("2024-04-06 10:30"))
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
                MockMvcRequestBuilders.get("/api/v1/posters/1"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].datePosted").doesNotExist());
    }

    @Test
    void testGetPosterById() throws Exception {
        when(mockPosterServiceImpl.getPosterById(3L)).thenReturn(samplePosters.get(2));

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/posters/3"))
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Found Dog: Max"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.datePosted").value("2024-05-02 15:00"))
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
    void testDeletePosterByIdWhenEmpty() throws Exception {
        doThrow(new ItemNotFoundException("The poster with the specified ID does not exist."))
                .when(mockPosterServiceImpl).deletePosterById(999L);

        this.mockMvcController.perform(MockMvcRequestBuilders.delete("/api/v1/posters/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("The poster with the specified ID does not exist."));
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

}