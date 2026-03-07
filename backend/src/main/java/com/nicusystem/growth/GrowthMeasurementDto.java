package com.nicusystem.growth;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for GrowthMeasurement entity.
 *
 * @param id                unique identifier
 * @param patientId         reference to the patient
 * @param measurementType   type of growth measurement
 * @param value             numeric value (weight in grams, length/HC in cm)
 * @param percentile        calculated percentile
 * @param zScore            calculated z-score
 * @param correctedAgeWeeks corrected gestational age in weeks
 * @param measuredAt        when the measurement was taken
 * @param notes             optional notes
 */
public record GrowthMeasurementDto(
        UUID id,
        UUID patientId,
        MeasurementType measurementType,
        Double value,
        Double percentile,
        Double zScore,
        Integer correctedAgeWeeks,
        Instant measuredAt,
        String notes
) {
}
