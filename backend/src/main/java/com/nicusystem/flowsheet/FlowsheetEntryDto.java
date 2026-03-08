package com.nicusystem.flowsheet;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for FlowsheetEntry entity.
 *
 * @param id            unique identifier
 * @param patientId     reference to the patient
 * @param category      flowsheet entry category
 * @param entryTime     when the observation or action occurred
 * @param fieldName     name of the documented field
 * @param fieldValue    value of the documented field
 * @param documentedBy  clinician who documented the entry
 * @param notes         additional notes
 * @param createdAt     when the record was created
 * @param updatedAt     when the record was last updated
 */
public record FlowsheetEntryDto(
        UUID id,
        UUID patientId,
        FlowsheetCategory category,
        Instant entryTime,
        String fieldName,
        String fieldValue,
        String documentedBy,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
}
