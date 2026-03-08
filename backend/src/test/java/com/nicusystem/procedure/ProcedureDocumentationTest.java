package com.nicusystem.procedure;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ProcedureDocumentation entity.
 */
class ProcedureDocumentationTest {

    @Test
    void shouldCreateWithNoArgConstructor() {
        // Given / When
        final ProcedureDocumentation doc = new ProcedureDocumentation();

        // Then
        assertThat(doc).isNotNull();
    }

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final ProcedureDocumentation doc = new ProcedureDocumentation();
        final UUID patientId = UUID.randomUUID();

        // When
        doc.setPatientId(patientId);

        // Then
        assertThat(doc.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetProcedureType() {
        // Given
        final ProcedureDocumentation doc = new ProcedureDocumentation();

        // When
        doc.setProcedureType(ProcedureType.INTUBATION);

        // Then
        assertThat(doc.getProcedureType()).isEqualTo(ProcedureType.INTUBATION);
    }

    @Test
    void shouldSetAndGetPerformedBy() {
        // Given
        final ProcedureDocumentation doc = new ProcedureDocumentation();

        // When
        doc.setPerformedBy("doctor-001");

        // Then
        assertThat(doc.getPerformedBy()).isEqualTo("doctor-001");
    }

    @Test
    void shouldSetAndGetAssistedBy() {
        // Given
        final ProcedureDocumentation doc = new ProcedureDocumentation();

        // When
        doc.setAssistedBy("nurse-002");

        // Then
        assertThat(doc.getAssistedBy()).isEqualTo("nurse-002");
    }

    @Test
    void shouldSetAndGetIndication() {
        // Given
        final ProcedureDocumentation doc = new ProcedureDocumentation();

        // When
        doc.setIndication("Respiratory failure");

        // Then
        assertThat(doc.getIndication()).isEqualTo("Respiratory failure");
    }

    @Test
    void shouldSetAndGetTechnique() {
        // Given
        final ProcedureDocumentation doc = new ProcedureDocumentation();

        // When
        doc.setTechnique("Oral intubation with video laryngoscopy");

        // Then
        assertThat(doc.getTechnique())
                .isEqualTo("Oral intubation with video laryngoscopy");
    }

    @Test
    void shouldSetAndGetFindings() {
        // Given
        final ProcedureDocumentation doc = new ProcedureDocumentation();

        // When
        doc.setFindings("Clear CSF obtained");

        // Then
        assertThat(doc.getFindings()).isEqualTo("Clear CSF obtained");
    }

    @Test
    void shouldSetAndGetComplications() {
        // Given
        final ProcedureDocumentation doc = new ProcedureDocumentation();

        // When
        doc.setComplications("None");

        // Then
        assertThat(doc.getComplications()).isEqualTo("None");
    }

    @Test
    void shouldSetAndGetNotes() {
        // Given
        final ProcedureDocumentation doc = new ProcedureDocumentation();

        // When
        doc.setNotes("Procedure well tolerated");

        // Then
        assertThat(doc.getNotes()).isEqualTo("Procedure well tolerated");
    }

    @Test
    void shouldSetAndGetPerformedAt() {
        // Given
        final ProcedureDocumentation doc = new ProcedureDocumentation();
        final Instant now = Instant.now();

        // When
        doc.setPerformedAt(now);

        // Then
        assertThat(doc.getPerformedAt()).isEqualTo(now);
    }
}
