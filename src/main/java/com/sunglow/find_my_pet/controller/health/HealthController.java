package com.sunglow.find_my_pet.controller.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @Autowired
    private DatabaseHealthIndicator databaseHealthIndicator;

    @GetMapping("/health")
    public ResponseEntity<Health> health() {
        Health health = databaseHealthIndicator.health();
        return ResponseEntity.ok(health);
    }
}
