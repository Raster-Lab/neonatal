package com.nicusystem.vitals;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TemperatureSiteTest {

    @Test
    void values_shouldContainAllSites() {
        // Given & When
        final TemperatureSite[] values = TemperatureSite.values();

        // Then
        assertThat(values).containsExactly(
                TemperatureSite.AXILLARY,
                TemperatureSite.RECTAL,
                TemperatureSite.SKIN,
                TemperatureSite.CORE
        );
    }

    @Test
    void valueOf_validName_shouldReturnEnum() {
        assertThat(TemperatureSite.valueOf("AXILLARY"))
                .isEqualTo(TemperatureSite.AXILLARY);
        assertThat(TemperatureSite.valueOf("RECTAL"))
                .isEqualTo(TemperatureSite.RECTAL);
        assertThat(TemperatureSite.valueOf("SKIN"))
                .isEqualTo(TemperatureSite.SKIN);
        assertThat(TemperatureSite.valueOf("CORE"))
                .isEqualTo(TemperatureSite.CORE);
    }
}
