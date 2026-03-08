package com.nicusystem.vitals.autodoc;

import java.util.UUID;

import com.nicusystem.vitals.VitalSignType;

/**
 * Data transfer object for AutoDocConfig entity.
 *
 * @param id        unique identifier
 * @param patientId reference to the patient
 * @param vitalType type of vital sign to auto-document
 * @param interval  documentation interval
 * @param enabled   whether this configuration is enabled
 * @param notes     optional notes
 */
public record AutoDocConfigDto(
        UUID id,
        UUID patientId,
        VitalSignType vitalType,
        AutoDocInterval interval,
        boolean enabled,
        String notes
) {
}
