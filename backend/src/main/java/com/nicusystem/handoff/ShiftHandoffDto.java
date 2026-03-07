package com.nicusystem.handoff;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for ShiftHandoff entity.
 *
 * @param id                      unique identifier
 * @param patientId               reference to the patient
 * @param handoffFormat           the structured communication format used
 * @param handoffAt               when the handoff occurred
 * @param handingOffProvider      provider handing off the patient
 * @param receivingProvider       provider receiving the patient
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
 * @param active                  whether the handoff record is active
 */
public record ShiftHandoffDto(
        UUID id,
        UUID patientId,
        HandoffFormat handoffFormat,
        Instant handoffAt,
        String handingOffProvider,
        String receivingProvider,
        String ipassIllnessSeverity,
        String ipassPatientSummary,
        String ipassActionList,
        String ipassSituationAwareness,
        String ipassSynthesisByReceiver,
        String sbarSituation,
        String sbarBackground,
        String sbarAssessment,
        String sbarRecommendation,
        String notes,
        boolean active
) {
}
