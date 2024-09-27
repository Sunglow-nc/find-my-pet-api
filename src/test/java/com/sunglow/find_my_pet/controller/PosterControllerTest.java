package com.sunglow.find_my_pet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.repository.PosterManagerRespository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(posterController).build();
        mapper = new ObjectMapper();
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
        posters.add(new Poster(1L, LocalDateTime.of(2024, 4, 6, 10, 30), "", "Minnie"));

        when(mockPosterServiceImpl.getAllPosters()).thenReturn(posters);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/posters"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Minnie"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].datePosted").value("2024-04-06 10:30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").isEmpty());
    }

    @Test
    void testGetAllPosters() throws Exception {

        List<Poster> posters = new ArrayList<>();
        posters.add(new Poster(1L, LocalDateTime.of(2024, 4, 6, 10, 30), "Black golden retriever, so fluffy <3", "Minnie"));
        posters.add(new Poster(2L, LocalDateTime.of(2020, 12, 25, 12, 00), "Energetic husky, blue eyes, lost in the snow.", "Kaya"));
        posters.add(new Poster(3L, LocalDateTime.of(1900, 4, 1, 8, 57), "A witch cat, can live a long live, flew with her broom.", "Beetlejuice Beetlejuice"));

        when(mockPosterServiceImpl.getAllPosters()).thenReturn(posters);

        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/posters"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Minnie"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].datePosted").value("2024-04-06 10:30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Black golden retriever, so fluffy <3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Kaya"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title").value("Beetlejuice Beetlejuice"));
    }

    //Testing getPostersById
    @Test
    void testGetPosterByIdWhenEmpty() throws Exception {

        List<Poster> posters = new ArrayList<>();

        when(mockPosterServiceImpl.getPosterById(1L)).thenThrow(new RuntimeException("Poster not found for ID: 1"));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/posters/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.datePosted").doesNotExist());
    }

    @Test
    void testGetPosterByIdWithNullValues() throws Exception {

        List<Poster> posters = new ArrayList<>();
        posters.add(new Poster(1L, LocalDateTime.of(2024, 4, 6, 10, 30), "", "Minnie"));

        when(mockPosterServiceImpl.getPosterById(1L)).thenReturn(posters.getFirst());

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/posters/1"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Minnie"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.datePosted").value("2024-04-06 10:30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").isEmpty());
    }

    @Test
    void testGetPosterById() throws Exception {

        List<Poster> posters = new ArrayList<>();
        posters.add(new Poster(1L, LocalDateTime.of(2024, 4, 6, 10, 30), "Black golden retriever, so fluffy <3", "Minnie"));
        posters.add(new Poster(2L, LocalDateTime.of(2020, 12, 25, 12, 00), "Energetic husky, blue eyes, lost in the snow.", "Kaya"));
        posters.add(new Poster(3L, LocalDateTime.of(1900, 4, 1, 8, 57), "A witch cat, can live a long live, flew with her broom.", "Beetlejuice Beetlejuice"));

        when(mockPosterServiceImpl.getPosterById(2L)).thenReturn(posters.get(1));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/posters/2"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Kaya"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.datePosted").value("2020-12-25 12:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Energetic husky, blue eyes, lost in the snow."));
    }

}