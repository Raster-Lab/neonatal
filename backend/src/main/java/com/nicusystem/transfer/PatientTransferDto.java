package com.nicusystem.transfer;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for PatientTransfer entity.
 *
 * @param id             unique identifier
 * @param patientId      the patient UUID
 * @param fromUnit       the unit transferred from
 * @param toUnit         the unit transferred to
 * @param fromFacility   the facility transferred from
 * @param toFacility     the facility transferred to
 * @param transferType   internal or external transfer
 * @param transferReason clinical reason for transfer
 * @param transferredAt  date and time of transfer
 * @param transferredBy  provider who arranged transfer
 * @param transportMode  mode of transport
 * @param notes          additional notes
 */
public record PatientTransferDto(
        UUID id,
        UUID patientId,
        String fromUnit,
        String toUnit,
        String fromFacility,
        String toFacility,
        PatientTransferType transferType,
        String transferReason,
        Instant transferredAt,
        String transferredBy,
        String transportMode,
        String notes
) {
}
