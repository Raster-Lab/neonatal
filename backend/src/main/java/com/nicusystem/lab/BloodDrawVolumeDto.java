package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for BloodDrawVolume entity.
 *
 * @param id        unique identifier
 * @param patientId reference to the patient
 * @param labOrderId reference to the associated lab order
 * @param drawnAt   when the blood draw occurred
 * @param volumeUl  volume drawn in microliters
 * @param drawnBy   staff member who performed the draw
 * @param notes     optional notes
 * @param createdAt audit: record creation time
 * @param updatedAt audit: last update time
 */
public record BloodDrawVolumeDto(
        UUID id,
        UUID patientId,
        UUID labOrderId,
        Instant drawnAt,
        Double volumeUl,
        String drawnBy,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
}
