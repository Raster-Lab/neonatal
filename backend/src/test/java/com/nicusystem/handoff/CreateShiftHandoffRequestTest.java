package com.nicusystem.handoff;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateShiftHandoffRequest record.
 */
class CreateShiftHandoffRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant handoffAt = Instant.now();

        // When
        final CreateShiftHandoffRequest request = new CreateShiftHandoffRequest(
                patientId, HandoffFormat.IPASS, handoffAt,
                "Dr. Smith", "Dr. Jones",
                "stable", "Patient summary", "Action list",
                "Contingency plan", "Read-back complete",
                null, null, null, null,
                "Evening handoff notes");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.handoffFormat()).isEqualTo(HandoffFormat.IPASS);
        assertThat(request.handoffAt()).isEqualTo(handoffAt);
        assertThat(request.handingOffProvider()).isEqualTo("Dr. Smith");
        assertThat(request.receivingProvider()).isEqualTo("Dr. Jones");
        assertThat(request.ipassIllnessSeverity()).isEqualTo("stable");
        assertThat(request.ipassPatientSummary()).isEqualTo("Patient summary");
        assertThat(request.ipassActionList()).isEqualTo("Action list");
        assertThat(request.ipassSituationAwareness()).isEqualTo("Contingency plan");
        assertThat(request.ipassSynthesisByReceiver()).isEqualTo("Read-back complete");
        assertThat(request.sbarSituation()).isNull();
        assertThat(request.sbarBackground()).isNull();
        assertThat(request.sbarAssessment()).isNull();
        assertThat(request.sbarRecommendation()).isNull();
        assertThat(request.notes()).isEqualTo("Evening handoff notes");
    }

    @Test
    void shouldCreateRequestWithNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant handoffAt = Instant.now();

        // When
        final CreateShiftHandoffRequest request = new CreateShiftHandoffRequest(
                patientId, HandoffFormat.SBAR, handoffAt,
                "Nurse A", "Nurse B",
                null, null, null, null, null,
                null, null, null, null,
                null);

        // Then
        assertThat(request.ipassIllnessSeverity()).isNull();
        assertThat(request.ipassPatientSummary()).isNull();
        assertThat(request.sbarSituation()).isNull();
        assertThat(request.sbarBackground()).isNull();
        assertThat(request.sbarAssessment()).isNull();
        assertThat(request.sbarRecommendation()).isNull();
        assertThat(request.notes()).isNull();
    }
}
