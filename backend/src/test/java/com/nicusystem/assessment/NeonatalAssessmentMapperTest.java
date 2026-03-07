package com.nicusystem.assessment;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for NeonatalAssessmentMapper.
 */
class NeonatalAssessmentMapperTest {

    private final NeonatalAssessmentMapper mapper = new NeonatalAssessmentMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final NeonatalAssessment entity = new NeonatalAssessment();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant assessedAt = Instant.now();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setAssessmentType(AssessmentType.ADMISSION);
        entity.setAssessedAt(assessedAt);
        entity.setAssessedBy("nurse-001");
        entity.setNeuroTone("Normal tone");
        entity.setNeuroReflexes("Intact");
        entity.setNeuroSeizureActivity(false);
        entity.setNeuroFontanelleStatus("Flat");
        entity.setCardiacPerfusion("Good");
        entity.setCardiacCapillaryRefillSeconds(2);
        entity.setCardiacHeartSounds("Regular");
        entity.setCardiacPulses("2+ bilaterally");
        entity.setRespBreathSounds("Clear");
        entity.setRespWorkOfBreathing("None");
        entity.setRespChestMovement("Symmetric");
        entity.setGiAbdomen("Soft");
        entity.setGiBowelSounds(true);
        entity.setGiStoolPattern("Meconium");
        entity.setGiFeedingTolerance("Tolerating");
        entity.setGuUrineOutputMlPerKgHr(1.5);
        entity.setGuGenitaliaAssessment("Normal");
        entity.setMskelExtremities("Full ROM");
        entity.setMskelHips("Stable");
        entity.setMskelSpine("Intact");
        entity.setIntegSkinIntegrity("Intact");
        entity.setIntegSkinColor("Pink");
        entity.setIntegRashes("None");
        entity.setIntegJaundice(true);
        entity.setIntegBradenQScore(18);
        entity.setNotes("Stable infant");

        // When
        final NeonatalAssessmentDto dto = mapper.toDto(entity);

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
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant assessedAt = Instant.now();
        final CreateNeonatalAssessmentRequest request = new CreateNeonatalAssessmentRequest(
                patientId, AssessmentType.SHIFT, assessedAt, "nurse-002",
                "Hypotonic", "Moro absent", true, "Bulging",
                "Poor", 4, "Murmur", "Weak",
                "Crackles", "Severe", "Asymmetric",
                "Distended", false, "No stool", "Intolerant",
                0.5, "Ambiguous",
                "Restricted", "Click present", "Scoliosis",
                "Open wound", "Jaundiced", "Erythema", true, 10,
                "Critically ill infant");

        // When
        final NeonatalAssessment entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getAssessmentType()).isEqualTo(AssessmentType.SHIFT);
        assertThat(entity.getAssessedAt()).isEqualTo(assessedAt);
        assertThat(entity.getAssessedBy()).isEqualTo("nurse-002");
        assertThat(entity.getNeuroTone()).isEqualTo("Hypotonic");
        assertThat(entity.getNeuroReflexes()).isEqualTo("Moro absent");
        assertThat(entity.getNeuroSeizureActivity()).isTrue();
        assertThat(entity.getNeuroFontanelleStatus()).isEqualTo("Bulging");
        assertThat(entity.getCardiacPerfusion()).isEqualTo("Poor");
        assertThat(entity.getCardiacCapillaryRefillSeconds()).isEqualTo(4);
        assertThat(entity.getCardiacHeartSounds()).isEqualTo("Murmur");
        assertThat(entity.getCardiacPulses()).isEqualTo("Weak");
        assertThat(entity.getRespBreathSounds()).isEqualTo("Crackles");
        assertThat(entity.getRespWorkOfBreathing()).isEqualTo("Severe");
        assertThat(entity.getRespChestMovement()).isEqualTo("Asymmetric");
        assertThat(entity.getGiAbdomen()).isEqualTo("Distended");
        assertThat(entity.getGiBowelSounds()).isFalse();
        assertThat(entity.getGiStoolPattern()).isEqualTo("No stool");
        assertThat(entity.getGiFeedingTolerance()).isEqualTo("Intolerant");
        assertThat(entity.getGuUrineOutputMlPerKgHr()).isEqualTo(0.5);
        assertThat(entity.getGuGenitaliaAssessment()).isEqualTo("Ambiguous");
        assertThat(entity.getMskelExtremities()).isEqualTo("Restricted");
        assertThat(entity.getMskelHips()).isEqualTo("Click present");
        assertThat(entity.getMskelSpine()).isEqualTo("Scoliosis");
        assertThat(entity.getIntegSkinIntegrity()).isEqualTo("Open wound");
        assertThat(entity.getIntegSkinColor()).isEqualTo("Jaundiced");
        assertThat(entity.getIntegRashes()).isEqualTo("Erythema");
        assertThat(entity.getIntegJaundice()).isTrue();
        assertThat(entity.getIntegBradenQScore()).isEqualTo(10);
        assertThat(entity.getNotes()).isEqualTo("Critically ill infant");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant assessedAt = Instant.now();
        final CreateNeonatalAssessmentRequest request = new CreateNeonatalAssessmentRequest(
                patientId, AssessmentType.DAILY_ROUND, assessedAt, "doctor-001",
                null, null, null, null,
                null, null, null, null,
                null, null, null,
                null, null, null, null,
                null, null,
                null, null, null,
                null, null, null, null, null,
                null);

        // When
        final NeonatalAssessment entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getNeuroTone()).isNull();
        assertThat(entity.getNeuroSeizureActivity()).isNull();
        assertThat(entity.getCardiacPerfusion()).isNull();
        assertThat(entity.getGiBowelSounds()).isNull();
        assertThat(entity.getGuUrineOutputMlPerKgHr()).isNull();
        assertThat(entity.getIntegJaundice()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
