package com.nicusystem.vitals;

import java.time.Instant;

/**
 * DTO for vital sign comparison between current value and historical baseline.
 *
 * @param vitalType        the type of vital sign
 * @param currentValue     the most recent vital sign value
 * @param currentRecordedAt when the current value was recorded
 * @param baselineAvg      average value during the baseline period
 * @param baselineMin      minimum value during the baseline period
 * @param baselineMax      maximum value during the baseline period
 * @param baselineCount    number of measurements in the baseline period
 * @param baselineStart    start of the baseline period
 * @param baselineEnd      end of the baseline period
 * @param deviationPercent percentage deviation of current value from baseline average
 */
public record VitalSignComparisonDto(
        VitalSignType vitalType,
        Double currentValue,
        Instant currentRecordedAt,
        Double baselineAvg,
        Double baselineMin,
        Double baselineMax,
        long baselineCount,
        Instant baselineStart,
        Instant baselineEnd,
        Double deviationPercent
) {
}
