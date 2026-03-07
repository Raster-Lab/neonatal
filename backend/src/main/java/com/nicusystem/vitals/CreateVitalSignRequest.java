package com.nicusystem.vitals;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a new vital sign measurement.
 *
 * @param patientId       reference to the patient
 * @param vitalType       type of vital sign
 * @param value           numeric value
 * @param unit            unit of measurement
 * @param recordedAt      when measurement was recorded
 * @param temperatureSite temperature measurement site (optional)
 * @param manualEntry     whether manually entered
 * @param notes           optional notes
 */
public record CreateVitalSignRequest(
        @NotNull UUID patientId,
        @NotNull VitalSignType vitalType,
        @NotNull Double value,
        @NotNull String unit,
        @NotNull Instant recordedAt,
        TemperatureSite temperatureSite,
        boolean manualEntry,
        String notes
) {
}
