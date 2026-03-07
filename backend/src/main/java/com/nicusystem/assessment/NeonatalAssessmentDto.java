package com.nicusystem.assessment;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for NeonatalAssessment entity.
 *
 * @param id                          unique identifier
 * @param patientId                   reference to the patient
 * @param assessmentType              type of assessment
 * @param assessedAt                  when the assessment was performed
 * @param assessedBy                  clinician who performed the assessment
 * @param neuroTone                   muscle tone description
 * @param neuroReflexes               reflex assessment
 * @param neuroSeizureActivity        whether seizure activity is present
 * @param neuroFontanelleStatus       fontanelle assessment
 * @param cardiacPerfusion            perfusion assessment
 * @param cardiacCapillaryRefillSeconds capillary refill time in seconds
 * @param cardiacHeartSounds          heart sounds description
 * @param cardiacPulses               pulse quality description
 * @param respBreathSounds            breath sounds description
 * @param respWorkOfBreathing         work of breathing description
 * @param respChestMovement           chest movement description
 * @param giAbdomen                   abdomen assessment
 * @param giBowelSounds               whether bowel sounds are present
 * @param giStoolPattern              stool pattern description
 * @param giFeedingTolerance          feeding tolerance description
 * @param guUrineOutputMlPerKgHr      urine output in mL/kg/hr
 * @param guGenitaliaAssessment       genitalia assessment description
 * @param mskelExtremities            extremities assessment
 * @param mskelHips                   hip assessment
 * @param mskelSpine                  spine assessment
 * @param integSkinIntegrity          skin integrity description
 * @param integSkinColor              skin color description
 * @param integRashes                 rashes and lesions description
 * @param integJaundice               whether jaundice is present
 * @param integBradenQScore           Braden Q pressure ulcer risk score (0-23)
 * @param notes                       free-text clinical notes
 */
public record NeonatalAssessmentDto(
        UUID id,
        UUID patientId,
        AssessmentType assessmentType,
        Instant assessedAt,
        String assessedBy,
        String neuroTone,
        String neuroReflexes,
        Boolean neuroSeizureActivity,
        String neuroFontanelleStatus,
        String cardiacPerfusion,
        Integer cardiacCapillaryRefillSeconds,
        String cardiacHeartSounds,
        String cardiacPulses,
        String respBreathSounds,
        String respWorkOfBreathing,
        String respChestMovement,
        String giAbdomen,
        Boolean giBowelSounds,
        String giStoolPattern,
        String giFeedingTolerance,
        Double guUrineOutputMlPerKgHr,
        String guGenitaliaAssessment,
        String mskelExtremities,
        String mskelHips,
        String mskelSpine,
        String integSkinIntegrity,
        String integSkinColor,
        String integRashes,
        Boolean integJaundice,
        Integer integBradenQScore,
        String notes
) {
}
