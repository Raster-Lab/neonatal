package com.nicusystem.procedure;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ProcedureDocumentationMapper.
 */
class ProcedureDocumentationMapperTest {

    private final ProcedureDocumentationMapper mapper =
            new ProcedureDocumentationMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final ProcedureDocumentation entity = new ProcedureDocumentation();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant performedAt = Instant.now();
        final Instant createdAt = Instant.now();
        final Instant updatedAt = Instant.now();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setProcedureType(ProcedureType.INTUBATION);
        entity.setPerformedBy("doctor-001");
        entity.setAssistedBy("nurse-002");
        entity.setIndication("Respiratory failure");
        entity.setTechnique("Oral intubation");
        entity.setFindings("Successful placement");
        entity.setComplications("None");
        entity.setNotes("Well tolerated");
        entity.setPerformedAt(performedAt);
        entity.setCreatedAt(createdAt);
        entity.setUpdatedAt(updatedAt);

        // When
        final ProcedureDocumentationDto dto = mapper.toDto(entity);

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
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant performedAt = Instant.now();
        final CreateProcedureDocumentationRequest request =
                new CreateProcedureDocumentationRequest(
                        patientId, ProcedureType.LUMBAR_PUNCTURE,
                        "doctor-001", "nurse-002",
                        "Rule out meningitis", "Aseptic technique",
                        "Clear CSF", "None",
                        "Sent for culture", performedAt);

        // When
        final ProcedureDocumentation entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getProcedureType())
                .isEqualTo(ProcedureType.LUMBAR_PUNCTURE);
        assertThat(entity.getPerformedBy()).isEqualTo("doctor-001");
        assertThat(entity.getAssistedBy()).isEqualTo("nurse-002");
        assertThat(entity.getIndication()).isEqualTo("Rule out meningitis");
        assertThat(entity.getTechnique()).isEqualTo("Aseptic technique");
        assertThat(entity.getFindings()).isEqualTo("Clear CSF");
        assertThat(entity.getComplications()).isEqualTo("None");
        assertThat(entity.getNotes()).isEqualTo("Sent for culture");
        assertThat(entity.getPerformedAt()).isEqualTo(performedAt);
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant performedAt = Instant.now();
        final CreateProcedureDocumentationRequest request =
                new CreateProcedureDocumentationRequest(
                        patientId, ProcedureType.UMBILICAL_LINE,
                        "doctor-001", null,
                        null, null, null, null,
                        null, performedAt);

        // When
        final ProcedureDocumentation entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getAssistedBy()).isNull();
        assertThat(entity.getIndication()).isNull();
        assertThat(entity.getTechnique()).isNull();
        assertThat(entity.getFindings()).isNull();
        assertThat(entity.getComplications()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
