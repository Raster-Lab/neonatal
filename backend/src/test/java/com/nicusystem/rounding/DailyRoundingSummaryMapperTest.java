package com.nicusystem.rounding;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for DailyRoundingSummaryMapper.
 */
class DailyRoundingSummaryMapperTest {

    private final DailyRoundingSummaryMapper mapper =
            new DailyRoundingSummaryMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final DailyRoundingSummary entity = new DailyRoundingSummary();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final LocalDate roundingDate = LocalDate.of(2026, 3, 15);
        final Instant createdAt = Instant.now();
        final Instant updatedAt = Instant.now();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setRoundingDate(roundingDate);
        entity.setPresentingProblems("Respiratory distress");
        entity.setOvernightEvents("Desaturation episode");
        entity.setCurrentVitalsSummary("HR 140, SpO2 95%");
        entity.setCurrentMedicationsSummary("Caffeine citrate 10mg");
        entity.setAssessmentPlan("Continue current management");
        entity.setAttendingPhysician("Dr. Smith");
        entity.setParticipants("Dr. Smith, Nurse Jones");
        entity.setActionItems("Order head ultrasound");
        entity.setNotes("Parents updated");
        entity.setCreatedAt(createdAt);
        entity.setUpdatedAt(updatedAt);

        // When
        final DailyRoundingSummaryDto dto = mapper.toDto(entity);

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
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final LocalDate roundingDate = LocalDate.of(2026, 3, 15);
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

        // When
        final DailyRoundingSummary entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getRoundingDate()).isEqualTo(roundingDate);
        assertThat(entity.getPresentingProblems())
                .isEqualTo("Respiratory distress");
        assertThat(entity.getOvernightEvents())
                .isEqualTo("Desaturation episode");
        assertThat(entity.getCurrentVitalsSummary())
                .isEqualTo("HR 140, SpO2 95%");
        assertThat(entity.getCurrentMedicationsSummary())
                .isEqualTo("Caffeine citrate 10mg");
        assertThat(entity.getAssessmentPlan())
                .isEqualTo("Continue current management");
        assertThat(entity.getAttendingPhysician())
                .isEqualTo("Dr. Smith");
        assertThat(entity.getParticipants())
                .isEqualTo("Dr. Smith, Nurse Jones");
        assertThat(entity.getActionItems())
                .isEqualTo("Order head ultrasound");
        assertThat(entity.getNotes()).isEqualTo("Parents updated");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final LocalDate roundingDate = LocalDate.of(2026, 3, 15);
        final CreateDailyRoundingSummaryRequest request =
                new CreateDailyRoundingSummaryRequest(
                        patientId, roundingDate,
                        null, null, null, null, null,
                        "Dr. Smith",
                        null, null, null);

        // When
        final DailyRoundingSummary entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPresentingProblems()).isNull();
        assertThat(entity.getOvernightEvents()).isNull();
        assertThat(entity.getCurrentVitalsSummary()).isNull();
        assertThat(entity.getCurrentMedicationsSummary()).isNull();
        assertThat(entity.getAssessmentPlan()).isNull();
        assertThat(entity.getParticipants()).isNull();
        assertThat(entity.getActionItems()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
