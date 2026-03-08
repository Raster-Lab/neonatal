package com.nicusystem.nirs;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object representing a NIRS reading.
 *
 * <p>Used to return NIRS reading data from the service layer to API callers.</p>
 *
 * @param id               unique identifier of the reading
 * @param patientId        UUID of the patient
 * @param site             body site of the NIRS sensor
 * @param rso2Value        regional oxygen saturation percentage
 * @param baseline         baseline rSO2 for comparison
 * @param recordedAt       timestamp when the reading was recorded
 * @param deviceIdentifier identifier of the NIRS device
 * @param notes            additional clinical notes
 * @param createdAt        audit timestamp of creation
 * @param updatedAt        audit timestamp of last update
 */
public record NirsReadingDto(
        UUID id,
        UUID patientId,
        NirsSite site,
        Double rso2Value,
        Double baseline,
        Instant recordedAt,
        String deviceIdentifier,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {}
