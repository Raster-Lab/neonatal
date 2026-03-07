package com.nicusystem.vitals;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for VitalSign entity.
 *
 * @param id              unique identifier
 * @param patientId       reference to the patient
 * @param vitalType       type of vital sign
 * @param value           numeric value
 * @param unit            unit of measurement
 * @param recordedAt      when measurement was recorded
 * @param temperatureSite temperature measurement site
 * @param manualEntry     whether manually entered
 * @param notes           optional notes
 */
public record VitalSignDto(
        UUID id,
        UUID patientId,
        VitalSignType vitalType,
        Double value,
        String unit,
        Instant recordedAt,
        TemperatureSite temperatureSite,
        boolean manualEntry,
        String notes
) {
}
