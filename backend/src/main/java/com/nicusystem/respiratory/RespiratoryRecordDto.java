package com.nicusystem.respiratory;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object representing a respiratory support record.
 *
 * <p>Used to return respiratory record data from the service layer to API callers.</p>
 *
 * @param id           unique identifier of the record
 * @param patientId    UUID of the patient
 * @param supportMode  the respiratory support mode
 * @param fio2Percent  fraction of inspired oxygen percentage (21–100)
 * @param peep         positive end-expiratory pressure in cmH2O
 * @param pip          peak inspiratory pressure in cmH2O
 * @param ratePerMin   set ventilator rate per minute
 * @param tiSeconds    inspiratory time in seconds
 * @param mapCmh2o     mean airway pressure in cmH2O
 * @param flowLpm      gas flow in litres per minute
 * @param recordedAt   timestamp the parameters were recorded
 * @param recordedBy   clinician who recorded the values
 * @param notes        additional clinical notes
 * @param createdAt    audit timestamp of creation
 * @param updatedAt    audit timestamp of last update
 */
public record RespiratoryRecordDto(
        UUID id,
        UUID patientId,
        RespiratorySupport supportMode,
        Double fio2Percent,
        Double peep,
        Double pip,
        Integer ratePerMin,
        Double tiSeconds,
        Double mapCmh2o,
        Double flowLpm,
        Instant recordedAt,
        String recordedBy,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {}
