package com.nicusystem.handoff;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ShiftHandoffDto record.
 */
class ShiftHandoffDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant handoffAt = Instant.now();

        // When
        final ShiftHandoffDto dto = new ShiftHandoffDto(
                id, patientId, HandoffFormat.IPASS, handoffAt,
                "Dr. Smith", "Dr. Jones",
                "stable", "Summary", "Action list", "Contingency", "Read-back",
                null, null, null, null,
                "Notes", true);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.handoffFormat()).isEqualTo(HandoffFormat.IPASS);
        assertThat(dto.handoffAt()).isEqualTo(handoffAt);
        assertThat(dto.handingOffProvider()).isEqualTo("Dr. Smith");
        assertThat(dto.receivingProvider()).isEqualTo("Dr. Jones");
        assertThat(dto.ipassIllnessSeverity()).isEqualTo("stable");
        assertThat(dto.ipassPatientSummary()).isEqualTo("Summary");
        assertThat(dto.ipassActionList()).isEqualTo("Action list");
        assertThat(dto.ipassSituationAwareness()).isEqualTo("Contingency");
        assertThat(dto.ipassSynthesisByReceiver()).isEqualTo("Read-back");
        assertThat(dto.sbarSituation()).isNull();
        assertThat(dto.sbarBackground()).isNull();
        assertThat(dto.sbarAssessment()).isNull();
        assertThat(dto.sbarRecommendation()).isNull();
        assertThat(dto.notes()).isEqualTo("Notes");
        assertThat(dto.active()).isTrue();
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant handoffAt = Instant.now();

        // When
        final ShiftHandoffDto dto = new ShiftHandoffDto(
                id, patientId, HandoffFormat.SBAR, handoffAt,
                "Dr. A", "Dr. B",
                null, null, null, null, null,
                null, null, null, null,
                null, true);

        // Then
        assertThat(dto.ipassIllnessSeverity()).isNull();
        assertThat(dto.ipassPatientSummary()).isNull();
        assertThat(dto.sbarSituation()).isNull();
        assertThat(dto.sbarBackground()).isNull();
        assertThat(dto.sbarAssessment()).isNull();
        assertThat(dto.sbarRecommendation()).isNull();
        assertThat(dto.notes()).isNull();
    }
}
