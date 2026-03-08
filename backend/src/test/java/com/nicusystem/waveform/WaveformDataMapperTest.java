package com.nicusystem.waveform;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for WaveformDataMapper.
 */
class WaveformDataMapperTest {

    private final WaveformDataMapper mapper = new WaveformDataMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final WaveformData entity = new WaveformData();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(10);
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setWaveformType(WaveformType.ECG);
        entity.setDataPoints("[1.0, 2.0, 3.0]");
        entity.setSamplingRateHz(250.0);
        entity.setStartTime(startTime);
        entity.setEndTime(endTime);
        entity.setDeviceIdentifier("MONITOR-001");
        entity.setUnit("mV");
        entity.setNotes("test note");

        // When
        final WaveformDataDto dto = mapper.toDto(entity);

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
        assertThat(dto.notes()).isEqualTo("test note");
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(10);
        final CreateWaveformDataRequest request = new CreateWaveformDataRequest(
                patientId, WaveformType.PULSE_OXIMETRY, "[98, 97, 99]",
                60.0, startTime, endTime, "OXIMETER-01", "%", "notes");

        // When
        final WaveformData entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getWaveformType()).isEqualTo(WaveformType.PULSE_OXIMETRY);
        assertThat(entity.getDataPoints()).isEqualTo("[98, 97, 99]");
        assertThat(entity.getSamplingRateHz()).isEqualTo(60.0);
        assertThat(entity.getStartTime()).isEqualTo(startTime);
        assertThat(entity.getEndTime()).isEqualTo(endTime);
        assertThat(entity.getDeviceIdentifier()).isEqualTo("OXIMETER-01");
        assertThat(entity.getUnit()).isEqualTo("%");
        assertThat(entity.getNotes()).isEqualTo("notes");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(10);
        final CreateWaveformDataRequest request = new CreateWaveformDataRequest(
                patientId, WaveformType.ECG, "[1.0, 2.0]",
                250.0, startTime, endTime, null, "mV", null);

        // When
        final WaveformData entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getDeviceIdentifier()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
