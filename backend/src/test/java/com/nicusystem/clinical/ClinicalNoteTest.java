package com.nicusystem.clinical;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ClinicalNote entity.
 */
class ClinicalNoteTest {

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final ClinicalNote note = new ClinicalNote();
        final UUID patientId = UUID.randomUUID();

        // When
        note.setPatientId(patientId);

        // Then
        assertThat(note.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetNoteType() {
        // Given
        final ClinicalNote note = new ClinicalNote();

        // When
        note.setNoteType(NoteType.PROGRESS);

        // Then
        assertThat(note.getNoteType()).isEqualTo(NoteType.PROGRESS);
    }

    @Test
    void shouldSetAndGetSubjectiveFindings() {
        // Given
        final ClinicalNote note = new ClinicalNote();

        // When
        note.setSubjectiveFindings("Patient appears stable");

        // Then
        assertThat(note.getSubjectiveFindings()).isEqualTo("Patient appears stable");
    }

    @Test
    void shouldSetAndGetObjectiveFindings() {
        // Given
        final ClinicalNote note = new ClinicalNote();

        // When
        note.setObjectiveFindings("HR 145, RR 40");

        // Then
        assertThat(note.getObjectiveFindings()).isEqualTo("HR 145, RR 40");
    }

    @Test
    void shouldSetAndGetAssessment() {
        // Given
        final ClinicalNote note = new ClinicalNote();

        // When
        note.setAssessment("Stable premature infant");

        // Then
        assertThat(note.getAssessment()).isEqualTo("Stable premature infant");
    }

    @Test
    void shouldSetAndGetPlan() {
        // Given
        final ClinicalNote note = new ClinicalNote();

        // When
        note.setPlan("Continue current management");

        // Then
        assertThat(note.getPlan()).isEqualTo("Continue current management");
    }

    @Test
    void shouldSetAndGetFreeText() {
        // Given
        final ClinicalNote note = new ClinicalNote();

        // When
        note.setFreeText("Additional free text notes");

        // Then
        assertThat(note.getFreeText()).isEqualTo("Additional free text notes");
    }

    @Test
    void shouldSetAndGetAuthorId() {
        // Given
        final ClinicalNote note = new ClinicalNote();

        // When
        note.setAuthorId("doctor-001");

        // Then
        assertThat(note.getAuthorId()).isEqualTo("doctor-001");
    }

    @Test
    void shouldSetAndGetAuthorRole() {
        // Given
        final ClinicalNote note = new ClinicalNote();

        // When
        note.setAuthorRole("Attending Physician");

        // Then
        assertThat(note.getAuthorRole()).isEqualTo("Attending Physician");
    }

    @Test
    void shouldSetAndGetCoSignerId() {
        // Given
        final ClinicalNote note = new ClinicalNote();

        // When
        note.setCoSignerId("resident-002");

        // Then
        assertThat(note.getCoSignerId()).isEqualTo("resident-002");
    }

    @Test
    void shouldSetAndGetCoSignedAt() {
        // Given
        final ClinicalNote note = new ClinicalNote();
        final Instant now = Instant.now();

        // When
        note.setCoSignedAt(now);

        // Then
        assertThat(note.getCoSignedAt()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetRecordedAt() {
        // Given
        final ClinicalNote note = new ClinicalNote();
        final Instant now = Instant.now();

        // When
        note.setRecordedAt(now);

        // Then
        assertThat(note.getRecordedAt()).isEqualTo(now);
    }

    @Test
    void shouldDefaultActiveToTrue() {
        // Given / When
        final ClinicalNote note = new ClinicalNote();

        // Then
        assertThat(note.isActive()).isTrue();
    }

    @Test
    void shouldSetAndGetActive() {
        // Given
        final ClinicalNote note = new ClinicalNote();

        // When
        note.setActive(false);

        // Then
        assertThat(note.isActive()).isFalse();
    }
}
