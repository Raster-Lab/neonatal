package com.nicusystem.growth;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for GrowthMeasurementMapper.
 */
class GrowthMeasurementMapperTest {

    private final GrowthMeasurementMapper mapper = new GrowthMeasurementMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final GrowthMeasurement entity = new GrowthMeasurement();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant measuredAt = Instant.now();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setMeasurementType(MeasurementType.WEIGHT);
        entity.setValue(1500.0);
        entity.setPercentile(50.0);
        entity.setZScore(-0.5);
        entity.setCorrectedAgeWeeks(32);
        entity.setMeasuredAt(measuredAt);
        entity.setNotes("test note");

        // When
        final GrowthMeasurementDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.measurementType()).isEqualTo(MeasurementType.WEIGHT);
        assertThat(dto.value()).isEqualTo(1500.0);
        assertThat(dto.percentile()).isEqualTo(50.0);
        assertThat(dto.zScore()).isEqualTo(-0.5);
        assertThat(dto.correctedAgeWeeks()).isEqualTo(32);
        assertThat(dto.measuredAt()).isEqualTo(measuredAt);
        assertThat(dto.notes()).isEqualTo("test note");
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant measuredAt = Instant.now();
        final CreateGrowthMeasurementRequest request = new CreateGrowthMeasurementRequest(
                patientId, MeasurementType.LENGTH, 45.0,
                75.0, 0.8, 34, measuredAt, "notes");

        // When
        final GrowthMeasurement entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getMeasurementType()).isEqualTo(MeasurementType.LENGTH);
        assertThat(entity.getValue()).isEqualTo(45.0);
        assertThat(entity.getPercentile()).isEqualTo(75.0);
        assertThat(entity.getZScore()).isEqualTo(0.8);
        assertThat(entity.getCorrectedAgeWeeks()).isEqualTo(34);
        assertThat(entity.getMeasuredAt()).isEqualTo(measuredAt);
        assertThat(entity.getNotes()).isEqualTo("notes");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant measuredAt = Instant.now();
        final CreateGrowthMeasurementRequest request = new CreateGrowthMeasurementRequest(
                patientId, MeasurementType.WEIGHT, 1200.0,
                null, null, null, measuredAt, null);

        // When
        final GrowthMeasurement entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPercentile()).isNull();
        assertThat(entity.getZScore()).isNull();
        assertThat(entity.getCorrectedAgeWeeks()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
