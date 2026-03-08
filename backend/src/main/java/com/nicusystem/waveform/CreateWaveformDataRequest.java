package com.nicusystem.waveform;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating new waveform data.
 *
 * @param patientId        reference to the patient
 * @param waveformType     type of waveform
 * @param dataPoints       JSON array of numeric data points
 * @param samplingRateHz   sampling rate in Hertz
 * @param startTime        start time of the waveform segment
 * @param endTime          end time of the waveform segment
 * @param deviceIdentifier identifier of the monitoring device (optional)
 * @param unit             unit of measurement
 * @param notes            optional notes
 */
public record CreateWaveformDataRequest(
        @NotNull UUID patientId,
        @NotNull WaveformType waveformType,
        @NotNull String dataPoints,
        @NotNull Double samplingRateHz,
        @NotNull Instant startTime,
        @NotNull Instant endTime,
        String deviceIdentifier,
        @NotNull String unit,
        String notes
) {
}
