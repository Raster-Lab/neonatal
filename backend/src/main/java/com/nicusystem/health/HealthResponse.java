package com.nicusystem.health;

import java.time.Instant;

/**
 * Response record for the health endpoint.
 *
 * @param status    the health status
 * @param version   the application version
 * @param timestamp the current timestamp
 */
public record HealthResponse(String status, String version, Instant timestamp) {
}
