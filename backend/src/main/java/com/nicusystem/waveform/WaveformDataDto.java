package com.nicusystem.waveform;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for WaveformData entity.
 *
 * @param id               unique identifier
 * @param patientId        reference to the patient
 * @param waveformType     type of waveform
 * @param dataPoints       JSON array of numeric data points
 * @param samplingRateHz   sampling rate in Hertz
 * @param startTime        start time of the waveform segment
 * @param endTime          end time of the waveform segment
 * @param deviceIdentifier identifier of the monitoring device
 * @param unit             unit of measurement
 * @param notes            optional notes
 */
public record WaveformDataDto(
        UUID id,
        UUID patientId,
        WaveformType waveformType,
        String dataPoints,
        Double samplingRateHz,
        Instant startTime,
        Instant endTime,
        String deviceIdentifier,
        String unit,
        String notes
) {
}
