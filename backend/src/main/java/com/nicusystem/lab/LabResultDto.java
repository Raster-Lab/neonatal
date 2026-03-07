package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for LabResult entity.
 *
 * @param id                unique identifier
 * @param labOrderId        reference to the originating lab order
 * @param patientId         reference to the patient
 * @param testName          specific test name within the panel
 * @param resultValue       the result value as a string
 * @param unit              unit of measurement
 * @param referenceRangeLow normal range lower bound
 * @param referenceRangeHigh normal range upper bound
 * @param isCritical        whether this is a critical value
 * @param isAbnormal        whether this is outside the normal range
 * @param resultedAt        when the result was reported
 * @param resultedBy        staff member who reported the result
 * @param notes             optional notes
 * @param createdAt         audit: record creation time
 * @param updatedAt         audit: last update time
 */
public record LabResultDto(
        UUID id,
        UUID labOrderId,
        UUID patientId,
        String testName,
        String resultValue,
        String unit,
        String referenceRangeLow,
        String referenceRangeHigh,
        boolean isCritical,
        boolean isAbnormal,
        Instant resultedAt,
        String resultedBy,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
}
