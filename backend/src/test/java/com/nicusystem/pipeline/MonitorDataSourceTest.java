package com.nicusystem.pipeline;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for MonitorDataSource enum.
 */
class MonitorDataSourceTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final MonitorDataSource[] values = MonitorDataSource.values();

        // Then
        assertThat(values).containsExactly(
                MonitorDataSource.CARDIAC_MONITOR,
                MonitorDataSource.PULSE_OXIMETER,
                MonitorDataSource.VENTILATOR,
                MonitorDataSource.INFUSION_PUMP,
                MonitorDataSource.TEMPERATURE_PROBE,
                MonitorDataSource.BLOOD_PRESSURE_MONITOR,
                MonitorDataSource.CAPNOGRAPH,
                MonitorDataSource.EEG_MONITOR,
                MonitorDataSource.NIRS_MONITOR
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(MonitorDataSource.valueOf("CARDIAC_MONITOR"))
                .isEqualTo(MonitorDataSource.CARDIAC_MONITOR);
        assertThat(MonitorDataSource.valueOf("PULSE_OXIMETER"))
                .isEqualTo(MonitorDataSource.PULSE_OXIMETER);
        assertThat(MonitorDataSource.valueOf("VENTILATOR"))
                .isEqualTo(MonitorDataSource.VENTILATOR);
        assertThat(MonitorDataSource.valueOf("INFUSION_PUMP"))
                .isEqualTo(MonitorDataSource.INFUSION_PUMP);
        assertThat(MonitorDataSource.valueOf("TEMPERATURE_PROBE"))
                .isEqualTo(MonitorDataSource.TEMPERATURE_PROBE);
        assertThat(MonitorDataSource.valueOf("BLOOD_PRESSURE_MONITOR"))
                .isEqualTo(MonitorDataSource.BLOOD_PRESSURE_MONITOR);
        assertThat(MonitorDataSource.valueOf("CAPNOGRAPH"))
                .isEqualTo(MonitorDataSource.CAPNOGRAPH);
        assertThat(MonitorDataSource.valueOf("EEG_MONITOR"))
                .isEqualTo(MonitorDataSource.EEG_MONITOR);
        assertThat(MonitorDataSource.valueOf("NIRS_MONITOR"))
                .isEqualTo(MonitorDataSource.NIRS_MONITOR);
    }
}
