package com.nicusystem.growth;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for GrowthMeasurement entity.
 */
class GrowthMeasurementTest {

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final GrowthMeasurement measurement = new GrowthMeasurement();
        final UUID patientId = UUID.randomUUID();

        // When
        measurement.setPatientId(patientId);

        // Then
        assertThat(measurement.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetMeasurementType() {
        // Given
        final GrowthMeasurement measurement = new GrowthMeasurement();

        // When
        measurement.setMeasurementType(MeasurementType.WEIGHT);

        // Then
        assertThat(measurement.getMeasurementType()).isEqualTo(MeasurementType.WEIGHT);
    }

    @Test
    void shouldSetAndGetValue() {
        // Given
        final GrowthMeasurement measurement = new GrowthMeasurement();

        // When
        measurement.setValue(1500.0);

        // Then
        assertThat(measurement.getValue()).isEqualTo(1500.0);
    }

    @Test
    void shouldSetAndGetPercentile() {
        // Given
        final GrowthMeasurement measurement = new GrowthMeasurement();

        // When
        measurement.setPercentile(50.0);

        // Then
        assertThat(measurement.getPercentile()).isEqualTo(50.0);
    }

    @Test
    void shouldSetAndGetZScore() {
        // Given
        final GrowthMeasurement measurement = new GrowthMeasurement();

        // When
        measurement.setZScore(-1.5);

        // Then
        assertThat(measurement.getZScore()).isEqualTo(-1.5);
    }

    @Test
    void shouldSetAndGetCorrectedAgeWeeks() {
        // Given
        final GrowthMeasurement measurement = new GrowthMeasurement();

        // When
        measurement.setCorrectedAgeWeeks(32);

        // Then
        assertThat(measurement.getCorrectedAgeWeeks()).isEqualTo(32);
    }

    @Test
    void shouldSetAndGetMeasuredAt() {
        // Given
        final GrowthMeasurement measurement = new GrowthMeasurement();
        final Instant now = Instant.now();

        // When
        measurement.setMeasuredAt(now);

        // Then
        assertThat(measurement.getMeasuredAt()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetNotes() {
        // Given
        final GrowthMeasurement measurement = new GrowthMeasurement();

        // When
        measurement.setNotes("Measured after feeding");

        // Then
        assertThat(measurement.getNotes()).isEqualTo("Measured after feeding");
    }
}
