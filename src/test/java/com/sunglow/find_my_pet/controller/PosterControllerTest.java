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
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Kaya"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title").value("Beetlejuice Beetlejuice"));

    }


    @Test
    void testGetPosterById() {
    }
}