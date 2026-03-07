package com.nicusystem.infection;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO representing an isolation precaution.
 *
 * @param id               precaution UUID
 * @param patientId        patient UUID
 * @param precautionType   type of isolation precaution
 * @param initiatedAt      when the precaution was initiated
 * @param discontinuedAt   when the precaution was discontinued (null if active)
 * @param initiatedBy      provider who initiated
 * @param indication       clinical indication
 * @param notes            clinical notes
 * @param createdAt        creation timestamp
 * @param updatedAt        last update timestamp
 */
public record IsolationPrecautionDto(
        UUID id,
        UUID patientId,
        IsolationPrecautionType precautionType,
        Instant initiatedAt,
        Instant discontinuedAt,
        String initiatedBy,
        String indication,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
}
