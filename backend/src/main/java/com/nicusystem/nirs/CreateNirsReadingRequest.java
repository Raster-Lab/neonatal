package com.nicusystem.nirs;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request record for creating a new NIRS reading.
 *
 * <p>Fields annotated with {@link NotNull} are mandatory; all others are optional.</p>
 *
 * @param patientId        UUID of the patient (required)
 * @param site             body site of the NIRS sensor (required)
 * @param rso2Value        regional oxygen saturation percentage (required)
 * @param baseline         baseline rSO2 for comparison
 * @param recordedAt       timestamp when the reading was recorded (required)
 * @param deviceIdentifier identifier of the NIRS device
 * @param notes            additional clinical notes
 */
public record CreateNirsReadingRequest(
        @NotNull UUID patientId,
        @NotNull NirsSite site,
        @NotNull Double rso2Value,
        Double baseline,
        @NotNull Instant recordedAt,
        String deviceIdentifier,
        String notes
) {}
