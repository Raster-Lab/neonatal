package com.nicusystem.rounding;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateDailyRoundingSummaryRequest record.
 */
class CreateDailyRoundingSummaryRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final LocalDate roundingDate = LocalDate.of(2026, 3, 15);

        // When
        final CreateDailyRoundingSummaryRequest request =
                new CreateDailyRoundingSummaryRequest(
                        patientId, roundingDate,
                        "Respiratory distress",
                        "Desaturation episode",
                        "HR 140, SpO2 95%",
                        "Caffeine citrate 10mg",
                        "Continue current management",
                        "Dr. Smith",
                        "Dr. Smith, Nurse Jones",
                        "Order head ultrasound",
                        "Parents updated");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.roundingDate()).isEqualTo(roundingDate);
        assertThat(request.presentingProblems())
                .isEqualTo("Respiratory distress");
        assertThat(request.overnightEvents())
                .isEqualTo("Desaturation episode");
        assertThat(request.currentVitalsSummary())
                .isEqualTo("HR 140, SpO2 95%");
        assertThat(request.currentMedicationsSummary())
                .isEqualTo("Caffeine citrate 10mg");
        assertThat(request.assessmentPlan())
                .isEqualTo("Continue current management");
        assertThat(request.attendingPhysician())
                .isEqualTo("Dr. Smith");
        assertThat(request.participants())
                .isEqualTo("Dr. Smith, Nurse Jones");
        assertThat(request.actionItems())
                .isEqualTo("Order head ultrasound");
        assertThat(request.notes()).isEqualTo("Parents updated");
    }

    @Test
    void shouldCreateRequestWithNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final LocalDate roundingDate = LocalDate.of(2026, 3, 15);

        // When
        final CreateDailyRoundingSummaryRequest request =
                new CreateDailyRoundingSummaryRequest(
                        patientId, roundingDate,
                        null, null, null, null, null,
                        "Dr. Smith",
                        null, null, null);

        // Then
        assertThat(request.presentingProblems()).isNull();
        assertThat(request.overnightEvents()).isNull();
        assertThat(request.currentVitalsSummary()).isNull();
        assertThat(request.currentMedicationsSummary()).isNull();
        assertThat(request.assessmentPlan()).isNull();
        assertThat(request.participants()).isNull();
        assertThat(request.actionItems()).isNull();
        assertThat(request.notes()).isNull();
    }
}
