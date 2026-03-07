package com.nicusystem.transfer;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request payload for recording a patient transfer.
 *
 * @param patientId      the patient UUID (required)
 * @param fromUnit       the unit transferred from (required)
 * @param toUnit         the unit transferred to (required)
 * @param fromFacility   the facility transferred from
 * @param toFacility     the facility transferred to
 * @param transferType   internal or external transfer
 * @param transferReason clinical reason for transfer
 * @param transferredAt  date and time of transfer (required)
 * @param transferredBy  provider who arranged transfer
 * @param transportMode  mode of transport
 * @param notes          additional notes
 */
public record CreatePatientTransferRequest(
        @NotNull UUID patientId,
        @NotBlank String fromUnit,
        @NotBlank String toUnit,
        String fromFacility,
        String toFacility,
        PatientTransferType transferType,
        String transferReason,
        @NotNull Instant transferredAt,
        String transferredBy,
        String transportMode,
        String notes
) {
}
