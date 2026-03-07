package com.nicusystem.health;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for HealthResponse record.
 */
class HealthResponseTest {

    @Test
    void shouldCreateRecordWithAllFields() {
        // Given
        final Instant now = Instant.now();

        // When
        final HealthResponse response = new HealthResponse("UP", "1.0.0", now);

        // Then
        assertThat(response.status()).isEqualTo("UP");
        assertThat(response.version()).isEqualTo("1.0.0");
        assertThat(response.timestamp()).isEqualTo(now);
    }

    @Test
    void shouldSupportEquality() {
        // Given
        final Instant now = Instant.now();
        final HealthResponse response1 = new HealthResponse("UP", "1.0.0", now);
        final HealthResponse response2 = new HealthResponse("UP", "1.0.0", now);

        // Then
        assertThat(response1).isEqualTo(response2);
        assertThat(response1.hashCode()).isEqualTo(response2.hashCode());
    }

    @Test
    void shouldSupportToString() {
        // Given
        final Instant now = Instant.now();
        final HealthResponse response = new HealthResponse("UP", "1.0.0", now);

        // Then
        assertThat(response.toString()).contains("UP", "1.0.0");
    }
}
