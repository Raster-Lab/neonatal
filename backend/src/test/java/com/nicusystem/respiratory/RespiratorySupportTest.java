package com.nicusystem.respiratory;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for RespiratorySupport enum.
 */
class RespiratorySupportTest {

    @Test
    void shouldContainAllExpectedValues() {
        // When
        final RespiratorySupport[] values = RespiratorySupport.values();

        // Then
        assertThat(values).containsExactlyInAnyOrder(
                RespiratorySupport.ROOM_AIR,
                RespiratorySupport.NASAL_CANNULA,
                RespiratorySupport.HIGH_FLOW_NASAL_CANNULA,
                RespiratorySupport.CPAP,
                RespiratorySupport.NIPPV,
                RespiratorySupport.CONVENTIONAL_VENTILATION,
                RespiratorySupport.HIGH_FREQUENCY_OSCILLATORY,
                RespiratorySupport.HIGH_FREQUENCY_JET,
                RespiratorySupport.INHALED_NITRIC_OXIDE,
                RespiratorySupport.ECMO
        );
    }

    @Test
    void shouldResolveFromName() {
        // When / Then
        assertThat(RespiratorySupport.valueOf("CPAP")).isEqualTo(RespiratorySupport.CPAP);
        assertThat(RespiratorySupport.valueOf("ECMO")).isEqualTo(RespiratorySupport.ECMO);
        assertThat(RespiratorySupport.valueOf("ROOM_AIR")).isEqualTo(RespiratorySupport.ROOM_AIR);
    }
}
