package com.sunglow.find_my_pet.controller.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Health", description = "API health check endpoints")
public class HealthController {

    @Autowired
    private DatabaseHealthIndicator databaseHealthIndicator;

    @Operation(summary = "Check API health", description = "Get the health status of the API and its database connection")
    @ApiResponse(responseCode = "200", description = "Health check successful",
        content = @Content(schema = @Schema(implementation = Health.class)))
    @GetMapping("/health")
    public ResponseEntity<Health> health() {
        Health health = databaseHealthIndicator.health();
        return ResponseEntity.ok(health);
    }
}