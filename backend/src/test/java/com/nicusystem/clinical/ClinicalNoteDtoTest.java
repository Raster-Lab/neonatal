package com.nicusystem.clinical;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ClinicalNoteDto record.
 */
class ClinicalNoteDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final Instant coSignedAt = Instant.now();

        // When
        final ClinicalNoteDto dto = new ClinicalNoteDto(
                id, patientId, NoteType.PROGRESS,
                "subjective", "objective", "assessment", "plan",
                "free text", "author-001", "Physician",
                "cosigner-001", coSignedAt, recordedAt, true);

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
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();

        // When
        final ClinicalNoteDto dto = new ClinicalNoteDto(
                id, patientId, NoteType.ADMISSION,
                null, null, null, null,
                null, "author-001", null,
                null, null, recordedAt, true);

        // Then
        assertThat(dto.subjectiveFindings()).isNull();
        assertThat(dto.objectiveFindings()).isNull();
        assertThat(dto.assessment()).isNull();
        assertThat(dto.plan()).isNull();
        assertThat(dto.freeText()).isNull();
        assertThat(dto.authorRole()).isNull();
        assertThat(dto.coSignerId()).isNull();
        assertThat(dto.coSignedAt()).isNull();
    }
}
