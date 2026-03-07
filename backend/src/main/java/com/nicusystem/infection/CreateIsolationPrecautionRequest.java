package com.nicusystem.infection;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating an isolation precaution.
 *
 * @param patientId      patient UUID
 * @param precautionType type of isolation precaution
 * @param initiatedAt    when the precaution was initiated
 * @param initiatedBy    provider who initiated
 * @param indication     clinical indication
 * @param notes          optional clinical notes
 */
public record CreateIsolationPrecautionRequest(
        @NotNull UUID patientId,
        @NotNull IsolationPrecautionType precautionType,
        @NotNull Instant initiatedAt,
        String initiatedBy,
        String indication,
        String notes
) {
}
