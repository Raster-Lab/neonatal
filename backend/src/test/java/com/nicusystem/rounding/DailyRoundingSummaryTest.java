package com.nicusystem.rounding;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for DailyRoundingSummary entity.
 */
class DailyRoundingSummaryTest {

    @Test
    void shouldCreateWithNoArgConstructor() {
        // Given / When
        final DailyRoundingSummary summary = new DailyRoundingSummary();

        // Then
        assertThat(summary).isNotNull();
    }

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final DailyRoundingSummary summary = new DailyRoundingSummary();
        final UUID patientId = UUID.randomUUID();

        // When
        summary.setPatientId(patientId);

        // Then
        assertThat(summary.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetRoundingDate() {
        // Given
        final DailyRoundingSummary summary = new DailyRoundingSummary();
        final LocalDate date = LocalDate.of(2026, 3, 15);

        // When
        summary.setRoundingDate(date);

        // Then
        assertThat(summary.getRoundingDate()).isEqualTo(date);
    }

    @Test
    void shouldSetAndGetPresentingProblems() {
        // Given
        final DailyRoundingSummary summary = new DailyRoundingSummary();

        // When
        summary.setPresentingProblems("Respiratory distress");

        // Then
        assertThat(summary.getPresentingProblems())
                .isEqualTo("Respiratory distress");
    }

    @Test
    void shouldSetAndGetOvernightEvents() {
        // Given
        final DailyRoundingSummary summary = new DailyRoundingSummary();

        // When
        summary.setOvernightEvents("Desaturation episode at 02:00");

        // Then
        assertThat(summary.getOvernightEvents())
                .isEqualTo("Desaturation episode at 02:00");
    }

    @Test
    void shouldSetAndGetCurrentVitalsSummary() {
        // Given
        final DailyRoundingSummary summary = new DailyRoundingSummary();

        // When
        summary.setCurrentVitalsSummary("HR 140, SpO2 95%");

        // Then
        assertThat(summary.getCurrentVitalsSummary())
                .isEqualTo("HR 140, SpO2 95%");
    }

    @Test
    void shouldSetAndGetCurrentMedicationsSummary() {
        // Given
        final DailyRoundingSummary summary = new DailyRoundingSummary();

        // When
        summary.setCurrentMedicationsSummary("Caffeine citrate 10mg daily");

        // Then
        assertThat(summary.getCurrentMedicationsSummary())
                .isEqualTo("Caffeine citrate 10mg daily");
    }

    @Test
    void shouldSetAndGetAssessmentPlan() {
        // Given
        final DailyRoundingSummary summary = new DailyRoundingSummary();

        // When
        summary.setAssessmentPlan("Continue current management");

        // Then
        assertThat(summary.getAssessmentPlan())
                .isEqualTo("Continue current management");
    }

    @Test
    void shouldSetAndGetAttendingPhysician() {
        // Given
        final DailyRoundingSummary summary = new DailyRoundingSummary();

        // When
        summary.setAttendingPhysician("Dr. Smith");

        // Then
        assertThat(summary.getAttendingPhysician())
                .isEqualTo("Dr. Smith");
    }

    @Test
    void shouldSetAndGetParticipants() {
        // Given
        final DailyRoundingSummary summary = new DailyRoundingSummary();

        // When
        summary.setParticipants("Dr. Smith, Nurse Jones, RT Brown");

        // Then
        assertThat(summary.getParticipants())
                .isEqualTo("Dr. Smith, Nurse Jones, RT Brown");
    }

    @Test
    void shouldSetAndGetActionItems() {
        // Given
        final DailyRoundingSummary summary = new DailyRoundingSummary();

        // When
        summary.setActionItems("Order head ultrasound");

        // Then
        assertThat(summary.getActionItems())
                .isEqualTo("Order head ultrasound");
    }

    @Test
    void shouldSetAndGetNotes() {
        // Given
        final DailyRoundingSummary summary = new DailyRoundingSummary();

        // When
        summary.setNotes("Parents present and updated");

        // Then
        assertThat(summary.getNotes())
                .isEqualTo("Parents present and updated");
    }
}
