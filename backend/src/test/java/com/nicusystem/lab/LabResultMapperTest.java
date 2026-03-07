package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for LabResultMapper.
 */
class LabResultMapperTest {

    private final LabResultMapper mapper = new LabResultMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final LabResult entity = new LabResult();
        final UUID id = UUID.randomUUID();
        final UUID labOrderId = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();
        entity.setId(id);
        entity.setLabOrderId(labOrderId);
        entity.setPatientId(patientId);
        entity.setTestName("Hemoglobin");
        entity.setResultValue("12.5");
        entity.setUnit("g/dL");
        entity.setReferenceRangeLow("14.0");
        entity.setReferenceRangeHigh("20.0");
        entity.setCritical(true);
        entity.setAbnormal(true);
        entity.setResultedAt(now);
        entity.setResultedBy("Lab Tech");
        entity.setNotes("low value");

        // When
        final LabResultDto dto = mapper.toDto(entity);

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
        assertThat(dto.notes()).isEqualTo("low value");
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID labOrderId = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();
        final CreateLabResultRequest request = new CreateLabResultRequest(
                labOrderId, patientId, "WBC", "8.5", "K/uL",
                "5.0", "15.0", false, false, now, "Tech", "notes");

        // When
        final LabResult entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getLabOrderId()).isEqualTo(labOrderId);
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getTestName()).isEqualTo("WBC");
        assertThat(entity.getResultValue()).isEqualTo("8.5");
        assertThat(entity.getUnit()).isEqualTo("K/uL");
        assertThat(entity.getReferenceRangeLow()).isEqualTo("5.0");
        assertThat(entity.getReferenceRangeHigh()).isEqualTo("15.0");
        assertThat(entity.isCritical()).isFalse();
        assertThat(entity.isAbnormal()).isFalse();
        assertThat(entity.getResultedAt()).isEqualTo(now);
        assertThat(entity.getResultedBy()).isEqualTo("Tech");
        assertThat(entity.getNotes()).isEqualTo("notes");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID labOrderId = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final CreateLabResultRequest request = new CreateLabResultRequest(
                labOrderId, patientId, "Glucose", "85", null,
                null, null, false, false, null, null, null);

        // When
        final LabResult entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getUnit()).isNull();
        assertThat(entity.getReferenceRangeLow()).isNull();
        assertThat(entity.getReferenceRangeHigh()).isNull();
        assertThat(entity.getResultedAt()).isNull();
        assertThat(entity.getResultedBy()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
