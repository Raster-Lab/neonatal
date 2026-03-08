package com.nicusystem.waveform;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for WaveformType enum.
 */
class WaveformTypeTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final WaveformType[] values = WaveformType.values();

        // Then
        assertThat(values).containsExactly(
                WaveformType.ECG,
                WaveformType.PULSE_OXIMETRY,
                WaveformType.RESPIRATORY,
                WaveformType.BLOOD_PRESSURE,
                WaveformType.CAPNOGRAPHY
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(WaveformType.valueOf("ECG")).isEqualTo(WaveformType.ECG);
        assertThat(WaveformType.valueOf("PULSE_OXIMETRY")).isEqualTo(WaveformType.PULSE_OXIMETRY);
        assertThat(WaveformType.valueOf("RESPIRATORY")).isEqualTo(WaveformType.RESPIRATORY);
        assertThat(WaveformType.valueOf("BLOOD_PRESSURE")).isEqualTo(WaveformType.BLOOD_PRESSURE);
        assertThat(WaveformType.valueOf("CAPNOGRAPHY")).isEqualTo(WaveformType.CAPNOGRAPHY);
    }
}
