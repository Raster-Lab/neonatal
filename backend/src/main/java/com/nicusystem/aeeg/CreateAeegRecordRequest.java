package com.nicusystem.aeeg;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request record for creating a new aEEG record.
 *
 * <p>Fields annotated with {@link NotNull} are mandatory; all others are optional.</p>
 *
 * @param patientId              UUID of the patient (required)
 * @param classification         background pattern classification (required)
 * @param upperMarginAmplitude   upper margin amplitude in microvolts (required)
 * @param lowerMarginAmplitude   lower margin amplitude in microvolts (required)
 * @param bandwidth              bandwidth in microvolts (required)
 * @param seizureDetected        whether seizure activity was detected
 * @param seizureDurationSeconds duration of seizure in seconds
 * @param recordingStartTime     timestamp when recording started (required)
 * @param recordingEndTime       timestamp when recording ended (required)
 * @param deviceIdentifier       identifier of the aEEG device
 * @param notes                  additional clinical notes
 */
public record CreateAeegRecordRequest(
        @NotNull UUID patientId,
        @NotNull AeegClassification classification,
        @NotNull Double upperMarginAmplitude,
        @NotNull Double lowerMarginAmplitude,
        @NotNull Double bandwidth,
        boolean seizureDetected,
        Integer seizureDurationSeconds,
        @NotNull Instant recordingStartTime,
        @NotNull Instant recordingEndTime,
        String deviceIdentifier,
        String notes
) {}
