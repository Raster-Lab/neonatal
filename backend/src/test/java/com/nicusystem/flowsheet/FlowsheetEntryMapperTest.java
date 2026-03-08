package com.nicusystem.flowsheet;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for FlowsheetEntryMapper.
 */
class FlowsheetEntryMapperTest {

    private final FlowsheetEntryMapper mapper =
            new FlowsheetEntryMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final FlowsheetEntry entity = new FlowsheetEntry();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant entryTime = Instant.now();
        final Instant createdAt = Instant.now();
        final Instant updatedAt = Instant.now();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setCategory(FlowsheetCategory.VITAL_SIGNS);
        entity.setEntryTime(entryTime);
        entity.setFieldName("heart_rate");
        entity.setFieldValue("145");
        entity.setDocumentedBy("nurse-001");
        entity.setNotes("Stable vitals");
        entity.setCreatedAt(createdAt);
        entity.setUpdatedAt(updatedAt);

        // When
        final FlowsheetEntryDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.category())
                .isEqualTo(FlowsheetCategory.VITAL_SIGNS);
        assertThat(dto.entryTime()).isEqualTo(entryTime);
        assertThat(dto.fieldName()).isEqualTo("heart_rate");
        assertThat(dto.fieldValue()).isEqualTo("145");
        assertThat(dto.documentedBy()).isEqualTo("nurse-001");
        assertThat(dto.notes()).isEqualTo("Stable vitals");
        assertThat(dto.createdAt()).isEqualTo(createdAt);
        assertThat(dto.updatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant entryTime = Instant.now();
        final CreateFlowsheetEntryRequest request =
                new CreateFlowsheetEntryRequest(
                        patientId, FlowsheetCategory.INTAKE_OUTPUT,
                        entryTime, "iv_fluid_in", "25",
                        "nurse-001", "D10W running");

        // When
        final FlowsheetEntry entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getCategory())
                .isEqualTo(FlowsheetCategory.INTAKE_OUTPUT);
        assertThat(entity.getEntryTime()).isEqualTo(entryTime);
        assertThat(entity.getFieldName()).isEqualTo("iv_fluid_in");
        assertThat(entity.getFieldValue()).isEqualTo("25");
        assertThat(entity.getDocumentedBy()).isEqualTo("nurse-001");
        assertThat(entity.getNotes()).isEqualTo("D10W running");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant entryTime = Instant.now();
        final CreateFlowsheetEntryRequest request =
                new CreateFlowsheetEntryRequest(
                        patientId, FlowsheetCategory.ASSESSMENT,
                        entryTime, "skin_color", "Pink",
                        "nurse-001", null);

        // When
        final FlowsheetEntry entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getNotes()).isNull();
    }
}
