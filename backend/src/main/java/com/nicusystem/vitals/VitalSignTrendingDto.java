package com.nicusystem.vitals;

import java.time.Instant;

/**
 * DTO for vital sign trending data point.
 *
 * @param vitalType   the type of vital sign
 * @param minValue    minimum value in the period
 * @param maxValue    maximum value in the period
 * @param avgValue    average value in the period
 * @param count       number of measurements in the period
 * @param periodStart start of the trending period
 * @param periodEnd   end of the trending period
 */
public record VitalSignTrendingDto(
        VitalSignType vitalType,
        Double minValue,
        Double maxValue,
        Double avgValue,
        long count,
        Instant periodStart,
        Instant periodEnd
) {
}
