package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request record for recording a new laboratory result.
 *
 * @param labOrderId        the lab order UUID (required)
 * @param patientId         the patient UUID (required)
 * @param testName          specific test name within the panel (required)
 * @param resultValue       the result value (required)
 * @param unit              unit of measurement
 * @param referenceRangeLow normal range lower bound
 * @param referenceRangeHigh normal range upper bound
 * @param isCritical        whether this is a critical value
 * @param isAbnormal        whether this is outside the normal range
 * @param resultedAt        when the result was reported
 * @param resultedBy        staff member reporting the result
 * @param notes             optional notes
 */
public record CreateLabResultRequest(
        @NotNull UUID labOrderId,
        @NotNull UUID patientId,
        @NotBlank String testName,
        @NotBlank String resultValue,
        String unit,
        String referenceRangeLow,
        String referenceRangeHigh,
        boolean isCritical,
        boolean isAbnormal,
        Instant resultedAt,
        String resultedBy,
        String notes
) {
}
