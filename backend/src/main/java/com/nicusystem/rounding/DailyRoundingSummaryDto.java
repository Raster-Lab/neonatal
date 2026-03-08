package com.nicusystem.rounding;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Data transfer object for DailyRoundingSummary entity.
 *
 * @param id                          unique identifier
 * @param patientId                   reference to the patient
 * @param roundingDate                date of the rounding
 * @param presentingProblems          presenting problems discussed
 * @param overnightEvents             significant overnight events
 * @param currentVitalsSummary        current vital signs summary
 * @param currentMedicationsSummary   current medications summary
 * @param assessmentPlan              assessment and plan
 * @param attendingPhysician          attending physician leading the rounding
 * @param participants                participants present
 * @param actionItems                 action items identified
 * @param notes                       additional notes
 * @param createdAt                   when the record was created
 * @param updatedAt                   when the record was last updated
 */
public record DailyRoundingSummaryDto(
        UUID id,
        UUID patientId,
        LocalDate roundingDate,
        String presentingProblems,
        String overnightEvents,
        String currentVitalsSummary,
        String currentMedicationsSummary,
        String assessmentPlan,
        String attendingPhysician,
        String participants,
        String actionItems,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
}
