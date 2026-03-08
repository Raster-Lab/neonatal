package com.nicusystem.rounding;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a new daily rounding summary.
 *
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
 */
public record CreateDailyRoundingSummaryRequest(
        @NotNull UUID patientId,
        @NotNull LocalDate roundingDate,
        String presentingProblems,
        String overnightEvents,
        String currentVitalsSummary,
        String currentMedicationsSummary,
        String assessmentPlan,
        @NotNull String attendingPhysician,
        String participants,
        String actionItems,
        String notes
) {
}
