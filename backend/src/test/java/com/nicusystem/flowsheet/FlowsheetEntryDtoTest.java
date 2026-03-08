package com.nicusystem.flowsheet;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for FlowsheetEntryDto record.
 */
class FlowsheetEntryDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant entryTime = Instant.now();
        final Instant createdAt = Instant.now();
        final Instant updatedAt = Instant.now();

        // When
        final FlowsheetEntryDto dto = new FlowsheetEntryDto(
                id, patientId, FlowsheetCategory.VITAL_SIGNS,
                entryTime, "heart_rate", "145",
                "nurse-001", "Stable vitals",
                createdAt, updatedAt);

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
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant entryTime = Instant.now();

        // When
        final FlowsheetEntryDto dto = new FlowsheetEntryDto(
                id, patientId, FlowsheetCategory.ASSESSMENT,
                entryTime, "skin_color", "Pink",
                "nurse-001", null,
                null, null);

        // Then
        assertThat(dto.notes()).isNull();
        assertThat(dto.createdAt()).isNull();
        assertThat(dto.updatedAt()).isNull();
    }
}
