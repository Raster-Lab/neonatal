package com.nicusystem.medication;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for TPN order data.
 *
 * @param id the unique identifier
 * @param patientId the patient identifier
 * @param aminoAcidsPercent amino acids percentage
 * @param dextrosePercent dextrose percentage
 * @param lipidsPercent lipids percentage
 * @param sodiumMeqPerL sodium in mEq/L
 * @param potassiumMeqPerL potassium in mEq/L
 * @param calciumMgPerDl calcium in mg/dL
 * @param magnesiumMeqPerL magnesium in mEq/L
 * @param phosphorusMmolPerL phosphorus in mmol/L
 * @param traceElementsIncluded whether trace elements are included
 * @param multivitaminsIncluded whether multivitamins are included
 * @param gir glucose infusion rate
 * @param totalVolumeMl total volume in mL
 * @param infusionRateMlPerHr infusion rate in mL/hr
 * @param cycleHours cycle duration in hours
 * @param dayNumber TPN day number
 * @param status the order status
 * @param orderedBy the ordering clinician
 * @param notes additional notes
 * @param weightGrams patient weight in grams
 * @param createdAt the creation timestamp
 * @param updatedAt the last update timestamp
 */
public record TpnOrderDto(
        UUID id,
        UUID patientId,
        Double aminoAcidsPercent,
        Double dextrosePercent,
        Double lipidsPercent,
        Double sodiumMeqPerL,
        Double potassiumMeqPerL,
        Double calciumMgPerDl,
        Double magnesiumMeqPerL,
        Double phosphorusMmolPerL,
        boolean traceElementsIncluded,
        boolean multivitaminsIncluded,
        Double gir,
        Double totalVolumeMl,
        Double infusionRateMlPerHr,
        int cycleHours,
        int dayNumber,
        TpnStatus status,
        String orderedBy,
        String notes,
        Integer weightGrams,
        Instant createdAt,
        Instant updatedAt
) {
}
