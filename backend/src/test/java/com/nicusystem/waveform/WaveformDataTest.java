package com.nicusystem.waveform;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for WaveformData entity.
 */
class WaveformDataTest {

    @Test
    void shouldSetAndGetPatientId() {
        // Given
        final WaveformData waveformData = new WaveformData();
        final UUID patientId = UUID.randomUUID();

        // When
        waveformData.setPatientId(patientId);

        // Then
        assertThat(waveformData.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void shouldSetAndGetWaveformType() {
        // Given
        final WaveformData waveformData = new WaveformData();

        // When
        waveformData.setWaveformType(WaveformType.ECG);

        // Then
        assertThat(waveformData.getWaveformType()).isEqualTo(WaveformType.ECG);
    }

    @Test
    void shouldSetAndGetDataPoints() {
        // Given
        final WaveformData waveformData = new WaveformData();

        // When
        waveformData.setDataPoints("[1.0, 2.0, 3.0]");

        // Then
        assertThat(waveformData.getDataPoints()).isEqualTo("[1.0, 2.0, 3.0]");
    }

    @Test
    void shouldSetAndGetSamplingRateHz() {
        // Given
        final WaveformData waveformData = new WaveformData();

        // When
        waveformData.setSamplingRateHz(250.0);

        // Then
        assertThat(waveformData.getSamplingRateHz()).isEqualTo(250.0);
    }

    @Test
    void shouldSetAndGetStartTime() {
        // Given
        final WaveformData waveformData = new WaveformData();
        final Instant now = Instant.now();

        // When
        waveformData.setStartTime(now);

        // Then
        assertThat(waveformData.getStartTime()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetEndTime() {
        // Given
        final WaveformData waveformData = new WaveformData();
        final Instant now = Instant.now();

        // When
        waveformData.setEndTime(now);

        // Then
        assertThat(waveformData.getEndTime()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetDeviceIdentifier() {
        // Given
        final WaveformData waveformData = new WaveformData();

        // When
        waveformData.setDeviceIdentifier("MONITOR-001");

        // Then
        assertThat(waveformData.getDeviceIdentifier()).isEqualTo("MONITOR-001");
    }

    @Test
    void shouldSetAndGetUnit() {
        // Given
        final WaveformData waveformData = new WaveformData();

        // When
        waveformData.setUnit("mV");

        // Then
        assertThat(waveformData.getUnit()).isEqualTo("mV");
    }

    @Test
    void shouldSetAndGetNotes() {
        // Given
        final WaveformData waveformData = new WaveformData();

        // When
        waveformData.setNotes("Artifact detected at 2s mark");

        // Then
        assertThat(waveformData.getNotes()).isEqualTo("Artifact detected at 2s mark");
    }
}
