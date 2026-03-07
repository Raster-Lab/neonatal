package com.nicusystem.assessment;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for NeonatalAssessmentDto record.
 */
class NeonatalAssessmentDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant assessedAt = Instant.now();

        // When
        final NeonatalAssessmentDto dto = new NeonatalAssessmentDto(
                id, patientId, AssessmentType.ADMISSION, assessedAt, "nurse-001",
                "Normal tone", "Intact", false, "Flat",
                "Good", 2, "Regular", "2+ bilaterally",
                "Clear", "None", "Symmetric",
                "Soft", true, "Meconium", "Tolerating",
                1.5, "Normal",
                "Full ROM", "Stable", "Intact",
                "Intact", "Pink", "None", true, 18,
                "Stable infant");

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.assessmentType()).isEqualTo(AssessmentType.ADMISSION);
        assertThat(dto.assessedAt()).isEqualTo(assessedAt);
        assertThat(dto.assessedBy()).isEqualTo("nurse-001");
        assertThat(dto.neuroTone()).isEqualTo("Normal tone");
        assertThat(dto.neuroReflexes()).isEqualTo("Intact");
        assertThat(dto.neuroSeizureActivity()).isFalse();
        assertThat(dto.neuroFontanelleStatus()).isEqualTo("Flat");
        assertThat(dto.cardiacPerfusion()).isEqualTo("Good");
        assertThat(dto.cardiacCapillaryRefillSeconds()).isEqualTo(2);
        assertThat(dto.cardiacHeartSounds()).isEqualTo("Regular");
        assertThat(dto.cardiacPulses()).isEqualTo("2+ bilaterally");
        assertThat(dto.respBreathSounds()).isEqualTo("Clear");
        assertThat(dto.respWorkOfBreathing()).isEqualTo("None");
        assertThat(dto.respChestMovement()).isEqualTo("Symmetric");
        assertThat(dto.giAbdomen()).isEqualTo("Soft");
        assertThat(dto.giBowelSounds()).isTrue();
        assertThat(dto.giStoolPattern()).isEqualTo("Meconium");
        assertThat(dto.giFeedingTolerance()).isEqualTo("Tolerating");
        assertThat(dto.guUrineOutputMlPerKgHr()).isEqualTo(1.5);
        assertThat(dto.guGenitaliaAssessment()).isEqualTo("Normal");
        assertThat(dto.mskelExtremities()).isEqualTo("Full ROM");
        assertThat(dto.mskelHips()).isEqualTo("Stable");
        assertThat(dto.mskelSpine()).isEqualTo("Intact");
        assertThat(dto.integSkinIntegrity()).isEqualTo("Intact");
        assertThat(dto.integSkinColor()).isEqualTo("Pink");
        assertThat(dto.integRashes()).isEqualTo("None");
        assertThat(dto.integJaundice()).isTrue();
        assertThat(dto.integBradenQScore()).isEqualTo(18);
        assertThat(dto.notes()).isEqualTo("Stable infant");
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant assessedAt = Instant.now();

        // When
        final NeonatalAssessmentDto dto = new NeonatalAssessmentDto(
                id, patientId, AssessmentType.SHIFT, assessedAt, "nurse-002",
                null, null, null, null,
                null, null, null, null,
                null, null, null,
                null, null, null, null,
                null, null,
                null, null, null,
                null, null, null, null, null,
                null);

        // Then
        assertThat(dto.neuroTone()).isNull();
        assertThat(dto.neuroSeizureActivity()).isNull();
        assertThat(dto.cardiacPerfusion()).isNull();
        assertThat(dto.giBowelSounds()).isNull();
        assertThat(dto.guUrineOutputMlPerKgHr()).isNull();
        assertThat(dto.integJaundice()).isNull();
        assertThat(dto.notes()).isNull();
    }
}
