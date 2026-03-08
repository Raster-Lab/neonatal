package com.nicusystem.vitals.autodoc;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AutoDocIntervalTest {

    @Test
    void values_shouldContainAllIntervals() {
        // Given & When
        final AutoDocInterval[] values = AutoDocInterval.values();

        // Then
        assertThat(values).containsExactly(
                AutoDocInterval.EVERY_15_MINUTES,
                AutoDocInterval.EVERY_30_MINUTES,
                AutoDocInterval.HOURLY,
                AutoDocInterval.EVERY_2_HOURS,
                AutoDocInterval.EVERY_4_HOURS
        );
    }

    @Test
    void valueOf_validName_shouldReturnEnum() {
        assertThat(AutoDocInterval.valueOf("EVERY_15_MINUTES"))
                .isEqualTo(AutoDocInterval.EVERY_15_MINUTES);
        assertThat(AutoDocInterval.valueOf("EVERY_30_MINUTES"))
                .isEqualTo(AutoDocInterval.EVERY_30_MINUTES);
        assertThat(AutoDocInterval.valueOf("HOURLY"))
                .isEqualTo(AutoDocInterval.HOURLY);
        assertThat(AutoDocInterval.valueOf("EVERY_2_HOURS"))
                .isEqualTo(AutoDocInterval.EVERY_2_HOURS);
        assertThat(AutoDocInterval.valueOf("EVERY_4_HOURS"))
                .isEqualTo(AutoDocInterval.EVERY_4_HOURS);
    }
}
