package com.nicusystem.assessment;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for NeonatalAssessment entity.
 */
class NeonatalAssessmentTest {

    @Test
    void shouldSetAndGetPatientId() {
        final NeonatalAssessment a = new NeonatalAssessment();
        final UUID id = UUID.randomUUID();
        a.setPatientId(id);
        assertThat(a.getPatientId()).isEqualTo(id);
    }

    @Test
    void shouldSetAndGetAssessmentType() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setAssessmentType(AssessmentType.ADMISSION);
        assertThat(a.getAssessmentType()).isEqualTo(AssessmentType.ADMISSION);
    }

    @Test
    void shouldSetAndGetAssessedAt() {
        final NeonatalAssessment a = new NeonatalAssessment();
        final Instant now = Instant.now();
        a.setAssessedAt(now);
        assertThat(a.getAssessedAt()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetAssessedBy() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setAssessedBy("nurse-001");
        assertThat(a.getAssessedBy()).isEqualTo("nurse-001");
    }

    @Test
    void shouldSetAndGetNeuroTone() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setNeuroTone("Normal tone");
        assertThat(a.getNeuroTone()).isEqualTo("Normal tone");
    }

    @Test
    void shouldSetAndGetNeuroReflexes() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setNeuroReflexes("Intact");
        assertThat(a.getNeuroReflexes()).isEqualTo("Intact");
    }

    @Test
    void shouldSetAndGetNeuroSeizureActivity() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setNeuroSeizureActivity(Boolean.FALSE);
        assertThat(a.getNeuroSeizureActivity()).isFalse();
    }

    @Test
    void shouldSetAndGetNeuroFontanelleStatus() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setNeuroFontanelleStatus("Flat");
        assertThat(a.getNeuroFontanelleStatus()).isEqualTo("Flat");
    }

    @Test
    void shouldSetAndGetCardiacPerfusion() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setCardiacPerfusion("Good");
        assertThat(a.getCardiacPerfusion()).isEqualTo("Good");
    }

    @Test
    void shouldSetAndGetCardiacCapillaryRefillSeconds() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setCardiacCapillaryRefillSeconds(2);
        assertThat(a.getCardiacCapillaryRefillSeconds()).isEqualTo(2);
    }

    @Test
    void shouldSetAndGetCardiacHeartSounds() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setCardiacHeartSounds("Regular");
        assertThat(a.getCardiacHeartSounds()).isEqualTo("Regular");
    }

    @Test
    void shouldSetAndGetCardiacPulses() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setCardiacPulses("2+ bilaterally");
        assertThat(a.getCardiacPulses()).isEqualTo("2+ bilaterally");
    }

    @Test
    void shouldSetAndGetRespBreathSounds() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setRespBreathSounds("Clear");
        assertThat(a.getRespBreathSounds()).isEqualTo("Clear");
    }

    @Test
    void shouldSetAndGetRespWorkOfBreathing() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setRespWorkOfBreathing("Mild retractions");
        assertThat(a.getRespWorkOfBreathing()).isEqualTo("Mild retractions");
    }

    @Test
    void shouldSetAndGetRespChestMovement() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setRespChestMovement("Symmetric");
        assertThat(a.getRespChestMovement()).isEqualTo("Symmetric");
    }

    @Test
    void shouldSetAndGetGiAbdomen() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setGiAbdomen("Soft, non-distended");
        assertThat(a.getGiAbdomen()).isEqualTo("Soft, non-distended");
    }

    @Test
    void shouldSetAndGetGiBowelSounds() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setGiBowelSounds(Boolean.TRUE);
        assertThat(a.getGiBowelSounds()).isTrue();
    }

    @Test
    void shouldSetAndGetGiStoolPattern() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setGiStoolPattern("Meconium");
        assertThat(a.getGiStoolPattern()).isEqualTo("Meconium");
    }

    @Test
    void shouldSetAndGetGiFeedingTolerance() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setGiFeedingTolerance("Tolerating");
        assertThat(a.getGiFeedingTolerance()).isEqualTo("Tolerating");
    }

    @Test
    void shouldSetAndGetGuUrineOutputMlPerKgHr() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setGuUrineOutputMlPerKgHr(1.5);
        assertThat(a.getGuUrineOutputMlPerKgHr()).isEqualTo(1.5);
    }

    @Test
    void shouldSetAndGetGuGenitaliaAssessment() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setGuGenitaliaAssessment("Normal male");
        assertThat(a.getGuGenitaliaAssessment()).isEqualTo("Normal male");
    }

    @Test
    void shouldSetAndGetMskelExtremities() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setMskelExtremities("Full ROM");
        assertThat(a.getMskelExtremities()).isEqualTo("Full ROM");
    }

    @Test
    void shouldSetAndGetMskelHips() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setMskelHips("Stable");
        assertThat(a.getMskelHips()).isEqualTo("Stable");
    }

    @Test
    void shouldSetAndGetMskelSpine() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setMskelSpine("Intact");
        assertThat(a.getMskelSpine()).isEqualTo("Intact");
    }

    @Test
    void shouldSetAndGetIntegSkinIntegrity() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setIntegSkinIntegrity("Intact");
        assertThat(a.getIntegSkinIntegrity()).isEqualTo("Intact");
    }

    @Test
    void shouldSetAndGetIntegSkinColor() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setIntegSkinColor("Pink");
        assertThat(a.getIntegSkinColor()).isEqualTo("Pink");
    }

    @Test
    void shouldSetAndGetIntegRashes() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setIntegRashes("None");
        assertThat(a.getIntegRashes()).isEqualTo("None");
    }

    @Test
    void shouldSetAndGetIntegJaundice() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setIntegJaundice(Boolean.TRUE);
        assertThat(a.getIntegJaundice()).isTrue();
    }

    @Test
    void shouldSetAndGetIntegBradenQScore() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setIntegBradenQScore(18);
        assertThat(a.getIntegBradenQScore()).isEqualTo(18);
    }

    @Test
    void shouldSetAndGetNotes() {
        final NeonatalAssessment a = new NeonatalAssessment();
        a.setNotes("Stable infant, no acute concerns");
        assertThat(a.getNotes()).isEqualTo("Stable infant, no acute concerns");
    }
}
