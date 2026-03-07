package com.nicusystem.growth;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateGrowthMeasurementRequest record.
 */
class CreateGrowthMeasurementRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant measuredAt = Instant.now();

        // When
        final CreateGrowthMeasurementRequest request = new CreateGrowthMeasurementRequest(
                patientId, MeasurementType.HEAD_CIRCUMFERENCE, 30.5,
                75.0, 0.8, 34, measuredAt, "notes");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.measurementType()).isEqualTo(MeasurementType.HEAD_CIRCUMFERENCE);
        assertThat(request.value()).isEqualTo(30.5);
        assertThat(request.percentile()).isEqualTo(75.0);
        assertThat(request.zScore()).isEqualTo(0.8);
        assertThat(request.correctedAgeWeeks()).isEqualTo(34);
        assertThat(request.measuredAt()).isEqualTo(measuredAt);
        assertThat(request.notes()).isEqualTo("notes");
    }

    @Test
    void shouldCreateRequestWithNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant measuredAt = Instant.now();

        // When
        final CreateGrowthMeasurementRequest request = new CreateGrowthMeasurementRequest(
                patientId, MeasurementType.WEIGHT, 1200.0,
                null, null, null, measuredAt, null);

        // Then
        assertThat(request.percentile()).isNull();
        assertThat(request.zScore()).isNull();
        assertThat(request.correctedAgeWeeks()).isNull();
        assertThat(request.notes()).isNull();
    }
}
