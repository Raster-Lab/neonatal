package com.nicusystem.aeeg;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for AeegClassification enum.
 */
class AeegClassificationTest {

    @Test
    void shouldContainAllExpectedValues() {
        // When
        final AeegClassification[] values = AeegClassification.values();

        // Then
        assertThat(values).containsExactlyInAnyOrder(
                AeegClassification.CONTINUOUS_NORMAL_VOLTAGE,
                AeegClassification.DISCONTINUOUS,
                AeegClassification.BURST_SUPPRESSION,
                AeegClassification.LOW_VOLTAGE,
                AeegClassification.FLAT_TRACE,
                AeegClassification.SEIZURE
        );
    }

    @Test
    void shouldResolveFromName() {
        // When / Then
        assertThat(AeegClassification.valueOf("CONTINUOUS_NORMAL_VOLTAGE"))
                .isEqualTo(AeegClassification.CONTINUOUS_NORMAL_VOLTAGE);
        assertThat(AeegClassification.valueOf("SEIZURE"))
                .isEqualTo(AeegClassification.SEIZURE);
        assertThat(AeegClassification.valueOf("BURST_SUPPRESSION"))
                .isEqualTo(AeegClassification.BURST_SUPPRESSION);
    }
}
