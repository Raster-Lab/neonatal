package com.nicusystem.waveform;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for WaveformDataDto record.
 */
class WaveformDataDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(10);

        // When
        final WaveformDataDto dto = new WaveformDataDto(
                id, patientId, WaveformType.ECG, "[1.0, 2.0, 3.0]",
                250.0, startTime, endTime, "MONITOR-001", "mV", "notes");

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.waveformType()).isEqualTo(WaveformType.ECG);
        assertThat(dto.dataPoints()).isEqualTo("[1.0, 2.0, 3.0]");
        assertThat(dto.samplingRateHz()).isEqualTo(250.0);
        assertThat(dto.startTime()).isEqualTo(startTime);
        assertThat(dto.endTime()).isEqualTo(endTime);
        assertThat(dto.deviceIdentifier()).isEqualTo("MONITOR-001");
        assertThat(dto.unit()).isEqualTo("mV");
        assertThat(dto.notes()).isEqualTo("notes");
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(10);

        // When
        final WaveformDataDto dto = new WaveformDataDto(
                id, patientId, WaveformType.PULSE_OXIMETRY, "[98, 97, 99]",
                60.0, startTime, endTime, null, "%", null);

        // Then
        assertThat(dto.deviceIdentifier()).isNull();
        assertThat(dto.notes()).isNull();
    }
}
