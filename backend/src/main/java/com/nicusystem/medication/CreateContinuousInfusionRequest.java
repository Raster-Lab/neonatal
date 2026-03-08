package com.nicusystem.medication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

/**
 * Request to create a new continuous infusion.
 *
 * @param patientId the patient identifier
 * @param drugName the drug name
 * @param concentration the concentration value
 * @param concentrationUnit the unit of concentration
 * @param rate the infusion rate
 * @param rateUnit the unit of rate
 * @param dosePerKgPerMin dose per kg per minute
 * @param weightGrams patient weight in grams
 * @param startTime the start time
 * @param orderedBy the ordering clinician
 * @param notes additional notes
 */
public record CreateContinuousInfusionRequest(
        @NotNull UUID patientId,
        @NotBlank String drugName,
        Double concentration,
        String concentrationUnit,
        @NotNull Double rate,
        @NotBlank String rateUnit,
        Double dosePerKgPerMin,
        Integer weightGrams,
        Instant startTime,
        String orderedBy,
        String notes
) {
}
