package com.nicusystem.aeeg;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object representing an aEEG record.
 *
 * <p>Used to return aEEG record data from the service layer to API callers.</p>
 *
 * @param id                     unique identifier of the record
 * @param patientId              UUID of the patient
 * @param classification         background pattern classification
 * @param upperMarginAmplitude   upper margin amplitude in microvolts
 * @param lowerMarginAmplitude   lower margin amplitude in microvolts
 * @param bandwidth              bandwidth in microvolts
 * @param seizureDetected        whether seizure activity was detected
 * @param seizureDurationSeconds duration of seizure in seconds
 * @param recordingStartTime     timestamp when recording started
 * @param recordingEndTime       timestamp when recording ended
 * @param deviceIdentifier       identifier of the aEEG device
 * @param notes                  additional clinical notes
 * @param createdAt              audit timestamp of creation
 * @param updatedAt              audit timestamp of last update
 */
public record AeegRecordDto(
        UUID id,
        UUID patientId,
        AeegClassification classification,
        Double upperMarginAmplitude,
        Double lowerMarginAmplitude,
        Double bandwidth,
        boolean seizureDetected,
        Integer seizureDurationSeconds,
        Instant recordingStartTime,
        Instant recordingEndTime,
        String deviceIdentifier,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {}
