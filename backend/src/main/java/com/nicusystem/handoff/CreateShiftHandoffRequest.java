package com.nicusystem.handoff;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a new shift handoff report.
 *
 * @param patientId               reference to the patient (required)
 * @param handoffFormat           the structured communication format (required)
 * @param handoffAt               when the handoff occurred (required)
 * @param handingOffProvider      provider handing off the patient (required)
 * @param receivingProvider       provider receiving the patient (required)
 * @param ipassIllnessSeverity    I-PASS illness severity
 * @param ipassPatientSummary     I-PASS patient summary
 * @param ipassActionList         I-PASS action list
 * @param ipassSituationAwareness I-PASS situation awareness and contingency plans
 * @param ipassSynthesisByReceiver I-PASS read-back by receiving provider
 * @param sbarSituation           SBAR situation
 * @param sbarBackground          SBAR background
 * @param sbarAssessment          SBAR assessment
 * @param sbarRecommendation      SBAR recommendation
 * @param notes                   free-text clinical notes
 */
public record CreateShiftHandoffRequest(
        @NotNull UUID patientId,
        @NotNull HandoffFormat handoffFormat,
        @NotNull Instant handoffAt,
        @NotNull String handingOffProvider,
        @NotNull String receivingProvider,
        String ipassIllnessSeverity,
        String ipassPatientSummary,
        String ipassActionList,
        String ipassSituationAwareness,
        String ipassSynthesisByReceiver,
        String sbarSituation,
        String sbarBackground,
        String sbarAssessment,
        String sbarRecommendation,
        String notes
) {
}
