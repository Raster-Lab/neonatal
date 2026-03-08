package com.nicusystem.procedure;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ProcedureDocumentationDto record.
 */
class ProcedureDocumentationDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant performedAt = Instant.now();
        final Instant createdAt = Instant.now();
        final Instant updatedAt = Instant.now();

        // When
        final ProcedureDocumentationDto dto = new ProcedureDocumentationDto(
                id, patientId, ProcedureType.INTUBATION,
                "doctor-001", "nurse-002",
                "Respiratory failure", "Oral intubation",
                "Successful placement", "None",
                "Well tolerated", performedAt,
                createdAt, updatedAt);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.procedureType()).isEqualTo(ProcedureType.INTUBATION);
        assertThat(dto.performedBy()).isEqualTo("doctor-001");
        assertThat(dto.assistedBy()).isEqualTo("nurse-002");
        assertThat(dto.indication()).isEqualTo("Respiratory failure");
        assertThat(dto.technique()).isEqualTo("Oral intubation");
        assertThat(dto.findings()).isEqualTo("Successful placement");
        assertThat(dto.complications()).isEqualTo("None");
        assertThat(dto.notes()).isEqualTo("Well tolerated");
        assertThat(dto.performedAt()).isEqualTo(performedAt);
        assertThat(dto.createdAt()).isEqualTo(createdAt);
        assertThat(dto.updatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant performedAt = Instant.now();

        // When
        final ProcedureDocumentationDto dto = new ProcedureDocumentationDto(
                id, patientId, ProcedureType.LUMBAR_PUNCTURE,
                "doctor-001", null,
                null, null, null, null,
                null, performedAt, null, null);

        // Then
        assertThat(dto.assistedBy()).isNull();
        assertThat(dto.indication()).isNull();
        assertThat(dto.technique()).isNull();
        assertThat(dto.findings()).isNull();
        assertThat(dto.complications()).isNull();
        assertThat(dto.notes()).isNull();
        assertThat(dto.createdAt()).isNull();
        assertThat(dto.updatedAt()).isNull();
    }
}
