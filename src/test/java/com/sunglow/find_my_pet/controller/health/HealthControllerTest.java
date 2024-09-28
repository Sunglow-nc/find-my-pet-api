package com.sunglow.find_my_pet.controller.health;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatabaseHealthIndicator databaseHealthIndicator;

    @Test
    void testHealthEndpointWhenDatabaseIsHealthy() throws Exception {
        when(databaseHealthIndicator.health()).thenReturn(
            Health.up()
                .withDetail("database", "Database is up")
                .withDetail("message", "Find My Pet is available")
                .build()
        );

        mockMvc.perform(get("/api/v1/health"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("UP"))
            .andExpect(jsonPath("$.details.database").value("Database is up"))
            .andExpect(jsonPath("$.details.message").value("Find My Pet is available"));
    }

    @Test
    void testHealthEndpointWhenDatabaseIsUnhealthy() throws Exception {
        when(databaseHealthIndicator.health()).thenReturn(
            Health.down()
                .withDetail("database", "Database is down")
                .withDetail("message", "Find My Pet is unavailable")
                .build()
        );

        mockMvc.perform(get("/api/v1/health"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("DOWN"))
            .andExpect(jsonPath("$.details.database").value("Database is down"))
            .andExpect(jsonPath("$.details.message").value("Find My Pet is unavailable"));
    }
}