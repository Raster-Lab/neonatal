package com.nicusystem.procedure;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateProcedureDocumentationRequest record.
 */
class CreateProcedureDocumentationRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant performedAt = Instant.now();

        // When
        final CreateProcedureDocumentationRequest request =
                new CreateProcedureDocumentationRequest(
                        patientId, ProcedureType.LINE_PLACEMENT,
                        "doctor-001", "nurse-002",
                        "IV access required", "Ultrasound-guided",
                        "Successful cannulation", "None",
                        "22G catheter placed", performedAt);

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.procedureType())
                .isEqualTo(ProcedureType.LINE_PLACEMENT);
        assertThat(request.performedBy()).isEqualTo("doctor-001");
        assertThat(request.assistedBy()).isEqualTo("nurse-002");
        assertThat(request.indication()).isEqualTo("IV access required");
        assertThat(request.technique()).isEqualTo("Ultrasound-guided");
        assertThat(request.findings()).isEqualTo("Successful cannulation");
        assertThat(request.complications()).isEqualTo("None");
        assertThat(request.notes()).isEqualTo("22G catheter placed");
        assertThat(request.performedAt()).isEqualTo(performedAt);
    }

    @Test
    void shouldCreateRequestWithNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant performedAt = Instant.now();

        // When
        final CreateProcedureDocumentationRequest request =
                new CreateProcedureDocumentationRequest(
                        patientId, ProcedureType.CHEST_TUBE,
                        "doctor-001", null,
                        null, null, null, null,
                        null, performedAt);

        // Then
        assertThat(request.assistedBy()).isNull();
        assertThat(request.indication()).isNull();
        assertThat(request.technique()).isNull();
        assertThat(request.findings()).isNull();
        assertThat(request.complications()).isNull();
        assertThat(request.notes()).isNull();
    }
}
