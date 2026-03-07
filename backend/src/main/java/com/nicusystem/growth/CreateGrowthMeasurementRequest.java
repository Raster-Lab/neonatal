package com.nicusystem.growth;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a new growth measurement.
 *
 * @param patientId         reference to the patient
 * @param measurementType   type of growth measurement
 * @param value             numeric value (weight in grams, length/HC in cm)
 * @param percentile        calculated percentile (optional)
 * @param zScore            calculated z-score (optional)
 * @param correctedAgeWeeks corrected gestational age in weeks (optional)
 * @param measuredAt        when the measurement was taken
 * @param notes             optional notes
 */
public record CreateGrowthMeasurementRequest(
        @NotNull UUID patientId,
        @NotNull MeasurementType measurementType,
        @NotNull Double value,
        Double percentile,
        Double zScore,
        Integer correctedAgeWeeks,
        @NotNull Instant measuredAt,
        String notes
) {
}
