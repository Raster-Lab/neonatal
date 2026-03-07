package com.nicusystem.respiratory;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateApneaEventRequest record.
 */
class CreateApneaEventRequestTest {

    @Test
    void shouldCreateRequestWithRequiredFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant occurredAt = Instant.now();

        // When
        final CreateApneaEventRequest request = new CreateApneaEventRequest(
                patientId, occurredAt, null, false, null, null, false, false, null, null);

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.occurredAt()).isEqualTo(occurredAt);
    }

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant occurredAt = Instant.now();

        // When
        final CreateApneaEventRequest request = new CreateApneaEventRequest(
                patientId, occurredAt, 30, true, 75, 82.0, true, false, 5.0, "brief episode");

        // Then
        assertThat(request.durationSeconds()).isEqualTo(30);
        assertThat(request.associatedBradycardia()).isTrue();
        assertThat(request.lowestHeartRate()).isEqualTo(75);
        assertThat(request.lowestSpo2()).isEqualTo(82.0);
        assertThat(request.stimulationRequired()).isTrue();
        assertThat(request.baggingRequired()).isFalse();
        assertThat(request.caffeineDose()).isEqualTo(5.0);
        assertThat(request.notes()).isEqualTo("brief episode");
    }
}
