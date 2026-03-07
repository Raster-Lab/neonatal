package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for LabResultDto record.
 */
class LabResultDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID labOrderId = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();

        // When
        final LabResultDto dto = new LabResultDto(
                id, labOrderId, patientId, "Hemoglobin", "12.5", "g/dL",
                "14.0", "20.0", true, true, now, "Lab Tech", "low", now, now);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.labOrderId()).isEqualTo(labOrderId);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.testName()).isEqualTo("Hemoglobin");
        assertThat(dto.resultValue()).isEqualTo("12.5");
        assertThat(dto.unit()).isEqualTo("g/dL");
        assertThat(dto.referenceRangeLow()).isEqualTo("14.0");
        assertThat(dto.referenceRangeHigh()).isEqualTo("20.0");
        assertThat(dto.isCritical()).isTrue();
        assertThat(dto.isAbnormal()).isTrue();
        assertThat(dto.resultedAt()).isEqualTo(now);
        assertThat(dto.resultedBy()).isEqualTo("Lab Tech");
        assertThat(dto.notes()).isEqualTo("low");
        assertThat(dto.createdAt()).isEqualTo(now);
        assertThat(dto.updatedAt()).isEqualTo(now);
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID labOrderId = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();

        // When
        final LabResultDto dto = new LabResultDto(
                id, labOrderId, patientId, "Glucose", "85", null,
                null, null, false, false, null, null, null, now, now);

        // Then
        assertThat(dto.unit()).isNull();
        assertThat(dto.referenceRangeLow()).isNull();
        assertThat(dto.referenceRangeHigh()).isNull();
        assertThat(dto.isCritical()).isFalse();
        assertThat(dto.isAbnormal()).isFalse();
        assertThat(dto.resultedAt()).isNull();
        assertThat(dto.resultedBy()).isNull();
        assertThat(dto.notes()).isNull();
    }
}
