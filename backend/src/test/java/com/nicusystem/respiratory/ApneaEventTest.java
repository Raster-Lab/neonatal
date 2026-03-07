package com.nicusystem.respiratory;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ApneaEvent entity.
 */
class ApneaEventTest {

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final ApneaEvent event = new ApneaEvent();
        final UUID patientId = UUID.randomUUID();

        // When
        event.setPatientId(patientId);

        // Then
        assertThat(event.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetOccurredAt() {
        // Given
        final ApneaEvent event = new ApneaEvent();
        final Instant now = Instant.now();

        // When
        event.setOccurredAt(now);

        // Then
        assertThat(event.getOccurredAt()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetDurationSeconds() {
        // Given
        final ApneaEvent event = new ApneaEvent();

        // When
        event.setDurationSeconds(30);

        // Then
        assertThat(event.getDurationSeconds()).isEqualTo(30);
    }

    @Test
    void shouldSetAndGetAssociatedBradycardia() {
        // Given
        final ApneaEvent event = new ApneaEvent();

        // When
        event.setAssociatedBradycardia(true);

        // Then
        assertThat(event.isAssociatedBradycardia()).isTrue();
    }

    @Test
    void shouldSetAndGetLowestHeartRate() {
        // Given
        final ApneaEvent event = new ApneaEvent();

        // When
        event.setLowestHeartRate(75);

        // Then
        assertThat(event.getLowestHeartRate()).isEqualTo(75);
    }

    @Test
    void shouldSetAndGetLowestSpo2() {
        // Given
        final ApneaEvent event = new ApneaEvent();

        // When
        event.setLowestSpo2(82.0);

        // Then
        assertThat(event.getLowestSpo2()).isEqualTo(82.0);
    }

    @Test
    void shouldSetAndGetStimulationRequired() {
        // Given
        final ApneaEvent event = new ApneaEvent();

        // When
        event.setStimulationRequired(true);

        // Then
        assertThat(event.isStimulationRequired()).isTrue();
    }

    @Test
    void shouldSetAndGetBaggingRequired() {
        // Given
        final ApneaEvent event = new ApneaEvent();

        // When
        event.setBaggingRequired(true);

        // Then
        assertThat(event.isBaggingRequired()).isTrue();
    }

    @Test
    void shouldSetAndGetCaffeineDose() {
        // Given
        final ApneaEvent event = new ApneaEvent();

        // When
        event.setCaffeineDose(5.0);

        // Then
        assertThat(event.getCaffeineDose()).isEqualTo(5.0);
    }

    @Test
    void shouldSetAndGetNotes() {
        // Given
        final ApneaEvent event = new ApneaEvent();

        // When
        event.setNotes("self-resolving apnea");

        // Then
        assertThat(event.getNotes()).isEqualTo("self-resolving apnea");
    }
}
