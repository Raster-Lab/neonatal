package com.nicusystem.growth;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for GrowthMeasurementDto record.
 */
class GrowthMeasurementDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant measuredAt = Instant.now();

        // When
        final GrowthMeasurementDto dto = new GrowthMeasurementDto(
                id, patientId, MeasurementType.WEIGHT, 1500.0,
                50.0, -0.5, 32, measuredAt, "notes");

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.measurementType()).isEqualTo(MeasurementType.WEIGHT);
        assertThat(dto.value()).isEqualTo(1500.0);
        assertThat(dto.percentile()).isEqualTo(50.0);
        assertThat(dto.zScore()).isEqualTo(-0.5);
        assertThat(dto.correctedAgeWeeks()).isEqualTo(32);
        assertThat(dto.measuredAt()).isEqualTo(measuredAt);
        assertThat(dto.notes()).isEqualTo("notes");
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant measuredAt = Instant.now();

        // When
        final GrowthMeasurementDto dto = new GrowthMeasurementDto(
                id, patientId, MeasurementType.LENGTH, 45.0,
                null, null, null, measuredAt, null);

        // Then
        assertThat(dto.percentile()).isNull();
        assertThat(dto.zScore()).isNull();
        assertThat(dto.correctedAgeWeeks()).isNull();
        assertThat(dto.notes()).isNull();
    }
}
