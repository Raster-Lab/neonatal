package com.nicusystem.waveform;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateWaveformDataRequest record.
 */
class CreateWaveformDataRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(10);

        // When
        final CreateWaveformDataRequest request = new CreateWaveformDataRequest(
                patientId, WaveformType.RESPIRATORY, "[0.5, 0.8, 0.3]",
                30.0, startTime, endTime, "VENT-002", "L/min", "notes");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.waveformType()).isEqualTo(WaveformType.RESPIRATORY);
        assertThat(request.dataPoints()).isEqualTo("[0.5, 0.8, 0.3]");
        assertThat(request.samplingRateHz()).isEqualTo(30.0);
        assertThat(request.startTime()).isEqualTo(startTime);
        assertThat(request.endTime()).isEqualTo(endTime);
        assertThat(request.deviceIdentifier()).isEqualTo("VENT-002");
        assertThat(request.unit()).isEqualTo("L/min");
        assertThat(request.notes()).isEqualTo("notes");
    }

    @Test
    void shouldCreateRequestWithNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(10);

        // When
        final CreateWaveformDataRequest request = new CreateWaveformDataRequest(
                patientId, WaveformType.ECG, "[1.0, 2.0]",
                250.0, startTime, endTime, null, "mV", null);

        // Then
        assertThat(request.deviceIdentifier()).isNull();
        assertThat(request.notes()).isNull();
    }
}
