package com.nicusystem.assessment;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateNeonatalAssessmentRequest record.
 */
class CreateNeonatalAssessmentRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant assessedAt = Instant.now();

        // When
        final CreateNeonatalAssessmentRequest request = new CreateNeonatalAssessmentRequest(
                patientId, AssessmentType.ADMISSION, assessedAt, "nurse-001",
                "Normal tone", "Intact", false, "Flat",
                "Good", 2, "Regular", "2+ bilaterally",
                "Clear", "None", "Symmetric",
                "Soft", true, "Meconium", "Tolerating",
                1.5, "Normal",
                "Full ROM", "Stable", "Intact",
                "Intact", "Pink", "None", true, 18,
                "Stable infant");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.assessmentType()).isEqualTo(AssessmentType.ADMISSION);
        assertThat(request.assessedAt()).isEqualTo(assessedAt);
        assertThat(request.assessedBy()).isEqualTo("nurse-001");
        assertThat(request.neuroTone()).isEqualTo("Normal tone");
        assertThat(request.neuroReflexes()).isEqualTo("Intact");
        assertThat(request.neuroSeizureActivity()).isFalse();
        assertThat(request.neuroFontanelleStatus()).isEqualTo("Flat");
        assertThat(request.cardiacPerfusion()).isEqualTo("Good");
        assertThat(request.cardiacCapillaryRefillSeconds()).isEqualTo(2);
        assertThat(request.cardiacHeartSounds()).isEqualTo("Regular");
        assertThat(request.cardiacPulses()).isEqualTo("2+ bilaterally");
        assertThat(request.respBreathSounds()).isEqualTo("Clear");
        assertThat(request.respWorkOfBreathing()).isEqualTo("None");
        assertThat(request.respChestMovement()).isEqualTo("Symmetric");
        assertThat(request.giAbdomen()).isEqualTo("Soft");
        assertThat(request.giBowelSounds()).isTrue();
        assertThat(request.giStoolPattern()).isEqualTo("Meconium");
        assertThat(request.giFeedingTolerance()).isEqualTo("Tolerating");
        assertThat(request.guUrineOutputMlPerKgHr()).isEqualTo(1.5);
        assertThat(request.guGenitaliaAssessment()).isEqualTo("Normal");
        assertThat(request.mskelExtremities()).isEqualTo("Full ROM");
        assertThat(request.mskelHips()).isEqualTo("Stable");
        assertThat(request.mskelSpine()).isEqualTo("Intact");
        assertThat(request.integSkinIntegrity()).isEqualTo("Intact");
        assertThat(request.integSkinColor()).isEqualTo("Pink");
        assertThat(request.integRashes()).isEqualTo("None");
        assertThat(request.integJaundice()).isTrue();
        assertThat(request.integBradenQScore()).isEqualTo(18);
        assertThat(request.notes()).isEqualTo("Stable infant");
    }

    @Test
    void shouldCreateRequestWithNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant assessedAt = Instant.now();

        // When
        final CreateNeonatalAssessmentRequest request = new CreateNeonatalAssessmentRequest(
                patientId, AssessmentType.SHIFT, assessedAt, "nurse-002",
                null, null, null, null,
                null, null, null, null,
                null, null, null,
                null, null, null, null,
                null, null,
                null, null, null,
                null, null, null, null, null,
                null);

        // Then
        assertThat(request.neuroTone()).isNull();
        assertThat(request.neuroSeizureActivity()).isNull();
        assertThat(request.cardiacPerfusion()).isNull();
        assertThat(request.giBowelSounds()).isNull();
        assertThat(request.guUrineOutputMlPerKgHr()).isNull();
        assertThat(request.integJaundice()).isNull();
        assertThat(request.notes()).isNull();
    }
}
