package com.nicusystem.health;

import java.time.Instant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for health check endpoints.
 */
@RestController
@RequestMapping("/api/v1/health")
@Tag(name = "Health", description = "Health check endpoints")
public class HealthController {

    private final String appVersion;

    /**
     * Creates a new HealthController.
     *
     * @param appVersion the application version from configuration
     */
    public HealthController(@Value("${app.version}") final String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * Returns the current health status of the application.
     *
     * @return health response with status, version and timestamp
     */
    @GetMapping
    @Operation(summary = "Get application health status")
    public ResponseEntity<HealthResponse> getHealth() {
        return ResponseEntity.ok(
                new HealthResponse("UP", appVersion, Instant.now())
        );
    }
}
