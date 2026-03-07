package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request record for recording a new blood draw volume.
 *
 * @param patientId  the patient UUID (required)
 * @param labOrderId the associated lab order UUID (optional)
 * @param drawnAt    when the blood draw occurred (required)
 * @param volumeUl   volume drawn in microliters (required)
 * @param drawnBy    staff member who performed the draw
 * @param notes      optional notes
 */
public record CreateBloodDrawVolumeRequest(
        @NotNull UUID patientId,
        UUID labOrderId,
        @NotNull Instant drawnAt,
        @NotNull Double volumeUl,
        String drawnBy,
        String notes
) {
}
