package com.nicusystem.clinical;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateClinicalNoteRequest record.
 */
class CreateClinicalNoteRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();

        // When
        final CreateClinicalNoteRequest request = new CreateClinicalNoteRequest(
                patientId, NoteType.PROGRESS,
                "subjective", "objective", "assessment", "plan",
                "free text", "author-001", "Physician", recordedAt);

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.noteType()).isEqualTo(NoteType.PROGRESS);
        assertThat(request.subjectiveFindings()).isEqualTo("subjective");
        assertThat(request.objectiveFindings()).isEqualTo("objective");
        assertThat(request.assessment()).isEqualTo("assessment");
        assertThat(request.plan()).isEqualTo("plan");
        assertThat(request.freeText()).isEqualTo("free text");
        assertThat(request.authorId()).isEqualTo("author-001");
        assertThat(request.authorRole()).isEqualTo("Physician");
        assertThat(request.recordedAt()).isEqualTo(recordedAt);
    }

    @Test
    void shouldCreateRequestWithNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();

        // When
        final CreateClinicalNoteRequest request = new CreateClinicalNoteRequest(
                patientId, NoteType.DISCHARGE,
                null, null, null, null,
                null, "author-001", null, recordedAt);

        // Then
        assertThat(request.subjectiveFindings()).isNull();
        assertThat(request.objectiveFindings()).isNull();
        assertThat(request.assessment()).isNull();
        assertThat(request.plan()).isNull();
        assertThat(request.freeText()).isNull();
        assertThat(request.authorRole()).isNull();
    }
}
