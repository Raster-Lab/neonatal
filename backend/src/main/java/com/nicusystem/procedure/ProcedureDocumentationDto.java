package com.nicusystem.procedure;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for ProcedureDocumentation entity.
 *
 * @param id             unique identifier
 * @param patientId      reference to the patient
 * @param procedureType  type of procedure performed
 * @param performedBy    clinician who performed the procedure
 * @param assistedBy     clinician who assisted
 * @param indication     clinical indication
 * @param technique      technique used
 * @param findings       findings observed
 * @param complications  complications encountered
 * @param notes          additional notes
 * @param performedAt    when the procedure was performed
 * @param createdAt      when the record was created
 * @param updatedAt      when the record was last updated
 */
public record ProcedureDocumentationDto(
        UUID id,
        UUID patientId,
        ProcedureType procedureType,
        String performedBy,
        String assistedBy,
        String indication,
        String technique,
        String findings,
        String complications,
        String notes,
        Instant performedAt,
        Instant createdAt,
        Instant updatedAt
) {
}
