package com.nicusystem.nirs;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for NirsReading entity.
 */
class NirsReadingTest {

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final NirsReading reading = new NirsReading();
        final UUID patientId = UUID.randomUUID();

        // When
        reading.setPatientId(patientId);

        // Then
        assertThat(reading.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetSite() {
        // Given
        final NirsReading reading = new NirsReading();

        // When
        reading.setSite(NirsSite.LEFT_CEREBRAL);

        // Then
        assertThat(reading.getSite()).isEqualTo(NirsSite.LEFT_CEREBRAL);
    }

    @Test
    void shouldSetAndGetRso2Value() {
        // Given
        final NirsReading reading = new NirsReading();

        // When
        reading.setRso2Value(72.5);

        // Then
        assertThat(reading.getRso2Value()).isEqualTo(72.5);
    }

    @Test
    void shouldSetAndGetBaseline() {
        // Given
        final NirsReading reading = new NirsReading();

        // When
        reading.setBaseline(75.0);

        // Then
        assertThat(reading.getBaseline()).isEqualTo(75.0);
    }

    @Test
    void shouldSetAndGetRecordedAt() {
        // Given
        final NirsReading reading = new NirsReading();
        final Instant now = Instant.now();

        // When
        reading.setRecordedAt(now);

        // Then
        assertThat(reading.getRecordedAt()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetDeviceIdentifier() {
        // Given
        final NirsReading reading = new NirsReading();

        // When
        reading.setDeviceIdentifier("INVOS-5100C-001");

        // Then
        assertThat(reading.getDeviceIdentifier()).isEqualTo("INVOS-5100C-001");
    }

    @Test
    void shouldSetAndGetNotes() {
        // Given
        final NirsReading reading = new NirsReading();

        // When
        reading.setNotes("Stable cerebral oxygenation");

        // Then
        assertThat(reading.getNotes()).isEqualTo("Stable cerebral oxygenation");
    }
}
