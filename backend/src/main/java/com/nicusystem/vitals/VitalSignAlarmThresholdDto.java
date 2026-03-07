package com.nicusystem.vitals;

import java.util.UUID;

/**
 * Data transfer object for VitalSignAlarmThreshold entity.
 *
 * @param id                          unique identifier
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
 * @param active                      whether this threshold is active
 */
public record VitalSignAlarmThresholdDto(
        UUID id,
        VitalSignType vitalType,
        Integer minimumGestationalAgeWeeks,
        Integer maximumGestationalAgeWeeks,
        Integer minimumWeightGrams,
        Integer maximumWeightGrams,
        Double lowAlarmValue,
        Double highAlarmValue,
        Double criticalLowValue,
        Double criticalHighValue,
        String unit,
        String description,
        boolean active
) {
}
