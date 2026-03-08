package com.nicusystem.flowsheet;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a new flowsheet entry.
 *
 * @param patientId     reference to the patient
 * @param category      flowsheet entry category
 * @param entryTime     when the observation or action occurred
 * @param fieldName     name of the documented field
 * @param fieldValue    value of the documented field
 * @param documentedBy  clinician who documented the entry
 * @param notes         additional notes
 */
public record CreateFlowsheetEntryRequest(
        @NotNull UUID patientId,
        @NotNull FlowsheetCategory category,
        @NotNull Instant entryTime,
        @NotNull String fieldName,
        @NotNull String fieldValue,
        @NotNull String documentedBy,
        String notes
) {
}
