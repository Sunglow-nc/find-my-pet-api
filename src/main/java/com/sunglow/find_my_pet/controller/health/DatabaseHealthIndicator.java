package com.sunglow.find_my_pet.controller.health;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component()
public class DatabaseHealthIndicator implements HealthIndicator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Health health() {
        if (isDatabaseHealthy()) {
            return Health.up()
                .withDetail("database", "Database is up")
                .withDetail("message", "Find My Pet is available")
                .build();
        } else {
            return Health.down()
                .withDetail("database", "Database is down")
                .withDetail("message", "Find My Pet is unavailable")
                .build();
        }
    }

    private boolean isDatabaseHealthy() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}