package com.nicusystem.procedure;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a new procedure documentation record.
 *
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
 */
public record CreateProcedureDocumentationRequest(
        @NotNull UUID patientId,
        @NotNull ProcedureType procedureType,
        @NotNull String performedBy,
        String assistedBy,
        String indication,
        String technique,
        String findings,
        String complications,
        String notes,
        @NotNull Instant performedAt
) {
}
