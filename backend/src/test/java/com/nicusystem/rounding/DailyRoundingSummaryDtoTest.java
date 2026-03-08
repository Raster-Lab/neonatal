package com.nicusystem.rounding;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for DailyRoundingSummaryDto record.
 */
class DailyRoundingSummaryDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final LocalDate roundingDate = LocalDate.of(2026, 3, 15);
        final Instant createdAt = Instant.now();
        final Instant updatedAt = Instant.now();

        // When
        final DailyRoundingSummaryDto dto = new DailyRoundingSummaryDto(
                id, patientId, roundingDate,
                "Respiratory distress",
                "Desaturation episode",
                "HR 140, SpO2 95%",
                "Caffeine citrate 10mg",
                "Continue current management",
                "Dr. Smith",
                "Dr. Smith, Nurse Jones",
                "Order head ultrasound",
                "Parents updated",
                createdAt, updatedAt);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.roundingDate()).isEqualTo(roundingDate);
        assertThat(dto.presentingProblems())
                .isEqualTo("Respiratory distress");
        assertThat(dto.overnightEvents())
                .isEqualTo("Desaturation episode");
        assertThat(dto.currentVitalsSummary())
                .isEqualTo("HR 140, SpO2 95%");
        assertThat(dto.currentMedicationsSummary())
                .isEqualTo("Caffeine citrate 10mg");
        assertThat(dto.assessmentPlan())
                .isEqualTo("Continue current management");
        assertThat(dto.attendingPhysician()).isEqualTo("Dr. Smith");
        assertThat(dto.participants())
                .isEqualTo("Dr. Smith, Nurse Jones");
        assertThat(dto.actionItems())
                .isEqualTo("Order head ultrasound");
        assertThat(dto.notes()).isEqualTo("Parents updated");
        assertThat(dto.createdAt()).isEqualTo(createdAt);
        assertThat(dto.updatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final LocalDate roundingDate = LocalDate.of(2026, 3, 15);

        // When
        final DailyRoundingSummaryDto dto = new DailyRoundingSummaryDto(
                id, patientId, roundingDate,
                null, null, null, null, null,
                "Dr. Smith",
                null, null, null,
                null, null);

        // Then
        assertThat(dto.presentingProblems()).isNull();
        assertThat(dto.overnightEvents()).isNull();
        assertThat(dto.currentVitalsSummary()).isNull();
        assertThat(dto.currentMedicationsSummary()).isNull();
        assertThat(dto.assessmentPlan()).isNull();
        assertThat(dto.participants()).isNull();
        assertThat(dto.actionItems()).isNull();
        assertThat(dto.notes()).isNull();
        assertThat(dto.createdAt()).isNull();
        assertThat(dto.updatedAt()).isNull();
    }
}
