package com.nicusystem.fluid;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object representing a fluid balance summary for a patient over a time period.
 *
 * @param patientId         reference to the patient
 * @param periodStart       start of the summary period
 * @param periodEnd         end of the summary period
 * @param totalIntakeMl     total fluid intake in milliliters
 * @param totalOutputMl     total fluid output in milliliters
 * @param netBalanceMl      net fluid balance (intake minus output) in milliliters
 * @param patientWeightGrams patient weight in grams (nullable)
 * @param intakePerKgPerDay  intake in ml/kg/day (nullable, only if weight provided)
 * @param outputPerKgPerDay  output in ml/kg/day (nullable, only if weight provided)
 */
public record FluidBalanceSummaryDto(
        UUID patientId,
        Instant periodStart,
        Instant periodEnd,
        Double totalIntakeMl,
        Double totalOutputMl,
        Double netBalanceMl,
        Integer patientWeightGrams,
        Double intakePerKgPerDay,
        Double outputPerKgPerDay
) {
}
