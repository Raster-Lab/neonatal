package com.nicusystem.medication;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Request to create a new TPN order.
 *
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
 * @param orderedBy the ordering clinician
 * @param notes additional notes
 * @param weightGrams patient weight in grams
 */
public record CreateTpnOrderRequest(
        @NotNull UUID patientId,
        Double aminoAcidsPercent,
        @NotNull Double dextrosePercent,
        Double lipidsPercent,
        Double sodiumMeqPerL,
        Double potassiumMeqPerL,
        Double calciumMgPerDl,
        Double magnesiumMeqPerL,
        Double phosphorusMmolPerL,
        boolean traceElementsIncluded,
        boolean multivitaminsIncluded,
        Double gir,
        @NotNull Double totalVolumeMl,
        @NotNull Double infusionRateMlPerHr,
        int cycleHours,
        int dayNumber,
        String orderedBy,
        String notes,
        Integer weightGrams
) {
}
