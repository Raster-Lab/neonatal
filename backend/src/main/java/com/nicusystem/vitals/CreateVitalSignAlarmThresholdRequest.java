package com.nicusystem.vitals;

import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating or updating a vital sign alarm threshold.
 *
 * @param vitalType                   type of vital sign
 * @param minimumGestationalAgeWeeks  minimum gestational age this threshold applies to
 * @param maximumGestationalAgeWeeks  maximum gestational age this threshold applies to
 * @param minimumWeightGrams          minimum patient weight this threshold applies to
 * @param maximumWeightGrams          maximum patient weight this threshold applies to
 * @param lowAlarmValue               value below which a low alarm triggers
 * @param highAlarmValue              value above which a high alarm triggers
 * @param criticalLowValue            value below which a critical low alarm triggers
 * @param criticalHighValue           value above which a critical high alarm triggers
 * @param unit                        unit of measurement
 * @param description                 optional description
 */
public record CreateVitalSignAlarmThresholdRequest(
        @NotNull VitalSignType vitalType,
        Integer minimumGestationalAgeWeeks,
        Integer maximumGestationalAgeWeeks,
        Integer minimumWeightGrams,
        Integer maximumWeightGrams,
        Double lowAlarmValue,
        Double highAlarmValue,
        Double criticalLowValue,
        Double criticalHighValue,
        @NotNull String unit,
        String description
) {
}
