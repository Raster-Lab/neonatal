package com.nicusystem.vitals.autodoc;

import java.util.UUID;

import com.nicusystem.vitals.VitalSignType;
import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating an automated vital signs documentation configuration.
 *
 * @param patientId reference to the patient
 * @param vitalType type of vital sign to auto-document
 * @param interval  documentation interval
 * @param notes     optional notes
 */
public record CreateAutoDocConfigRequest(
        @NotNull UUID patientId,
        @NotNull VitalSignType vitalType,
        @NotNull AutoDocInterval interval,
        String notes
) {
}
