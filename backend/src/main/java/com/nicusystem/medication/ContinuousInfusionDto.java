package com.nicusystem.medication;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for continuous infusion data.
 *
 * @param id the unique identifier
 * @param patientId the patient identifier
 * @param drugName the drug name
 * @param concentration the concentration value
 * @param concentrationUnit the unit of concentration
 * @param rate the infusion rate
 * @param rateUnit the unit of rate
 * @param dosePerKgPerMin dose per kg per minute
 * @param weightGrams patient weight in grams
 * @param startTime the start time
 * @param endTime the end time
 * @param status the infusion status
 * @param orderedBy the ordering clinician
 * @param notes additional notes
 * @param createdAt the creation timestamp
 * @param updatedAt the last update timestamp
 */
public record ContinuousInfusionDto(
        UUID id,
        UUID patientId,
        String drugName,
        Double concentration,
        String concentrationUnit,
        Double rate,
        String rateUnit,
        Double dosePerKgPerMin,
        Integer weightGrams,
        Instant startTime,
        Instant endTime,
        InfusionStatus status,
        String orderedBy,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
}
