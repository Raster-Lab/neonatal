package com.nicusystem.clinical;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ClinicalNoteMapper.
 */
class ClinicalNoteMapperTest {

    private final ClinicalNoteMapper mapper = new ClinicalNoteMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final ClinicalNote entity = new ClinicalNote();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final Instant coSignedAt = Instant.now();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setNoteType(NoteType.PROGRESS);
        entity.setSubjectiveFindings("subjective");
        entity.setObjectiveFindings("objective");
        entity.setAssessment("assessment");
        entity.setPlan("plan");
        entity.setFreeText("free text");
        entity.setAuthorId("author-001");
        entity.setAuthorRole("Physician");
        entity.setCoSignerId("cosigner-001");
        entity.setCoSignedAt(coSignedAt);
        entity.setRecordedAt(recordedAt);
        entity.setActive(true);

        // When
        final ClinicalNoteDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.noteType()).isEqualTo(NoteType.PROGRESS);
        assertThat(dto.subjectiveFindings()).isEqualTo("subjective");
        assertThat(dto.objectiveFindings()).isEqualTo("objective");
        assertThat(dto.assessment()).isEqualTo("assessment");
        assertThat(dto.plan()).isEqualTo("plan");
        assertThat(dto.freeText()).isEqualTo("free text");
        assertThat(dto.authorId()).isEqualTo("author-001");
        assertThat(dto.authorRole()).isEqualTo("Physician");
        assertThat(dto.coSignerId()).isEqualTo("cosigner-001");
        assertThat(dto.coSignedAt()).isEqualTo(coSignedAt);
        assertThat(dto.recordedAt()).isEqualTo(recordedAt);
        assertThat(dto.active()).isTrue();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final CreateClinicalNoteRequest request = new CreateClinicalNoteRequest(
                patientId, NoteType.ADMISSION,
                "subjective", "objective", "assessment", "plan",
                "free text", "author-001", "Resident", recordedAt);

        // When
        final ClinicalNote entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getNoteType()).isEqualTo(NoteType.ADMISSION);
        assertThat(entity.getSubjectiveFindings()).isEqualTo("subjective");
        assertThat(entity.getObjectiveFindings()).isEqualTo("objective");
        assertThat(entity.getAssessment()).isEqualTo("assessment");
        assertThat(entity.getPlan()).isEqualTo("plan");
        assertThat(entity.getFreeText()).isEqualTo("free text");
        assertThat(entity.getAuthorId()).isEqualTo("author-001");
        assertThat(entity.getAuthorRole()).isEqualTo("Resident");
        assertThat(entity.getRecordedAt()).isEqualTo(recordedAt);
        assertThat(entity.isActive()).isTrue();
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final CreateClinicalNoteRequest request = new CreateClinicalNoteRequest(
                patientId, NoteType.PROCEDURE,
                null, null, null, null,
                null, "author-001", null, recordedAt);

        // When
        final ClinicalNote entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getSubjectiveFindings()).isNull();
        assertThat(entity.getObjectiveFindings()).isNull();
        assertThat(entity.getAssessment()).isNull();
        assertThat(entity.getPlan()).isNull();
        assertThat(entity.getFreeText()).isNull();
        assertThat(entity.getAuthorRole()).isNull();
    }
}
