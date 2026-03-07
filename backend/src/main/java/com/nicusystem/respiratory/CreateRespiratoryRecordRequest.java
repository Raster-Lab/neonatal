package com.nicusystem.respiratory;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request record for creating a new respiratory support record.
 *
 * <p>Fields annotated with {@link NotNull} are mandatory; all others are optional
 * and depend on the support mode in use.</p>
 *
 * @param patientId    UUID of the patient (required)
 * @param supportMode  the respiratory support mode (required)
 * @param fio2Percent  fraction of inspired oxygen percentage (21–100)
 * @param peep         positive end-expiratory pressure in cmH2O
 * @param pip          peak inspiratory pressure in cmH2O
 * @param ratePerMin   set ventilator rate per minute
 * @param tiSeconds    inspiratory time in seconds
 * @param mapCmh2o     mean airway pressure in cmH2O
 * @param flowLpm      gas flow in litres per minute
 * @param recordedAt   timestamp when the parameters were observed (required)
 * @param recordedBy   clinician who recorded the values
 * @param notes        additional clinical notes
 */
public record CreateRespiratoryRecordRequest(
        @NotNull UUID patientId,
        @NotNull RespiratorySupport supportMode,
        Double fio2Percent,
        Double peep,
        Double pip,
        Integer ratePerMin,
        Double tiSeconds,
        Double mapCmh2o,
        Double flowLpm,
        @NotNull Instant recordedAt,
        String recordedBy,
        String notes
) {}
