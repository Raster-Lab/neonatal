package com.nicusystem.medication;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for AllergySeverity enum.
 */
class AllergySeverityTest {

    @Test
    void values_containsAllExpectedSeverities() {
        // When
        final AllergySeverity[] values = AllergySeverity.values();

        // Then
        assertThat(values).containsExactlyInAnyOrder(
                AllergySeverity.MILD,
                AllergySeverity.MODERATE,
                AllergySeverity.SEVERE,
                AllergySeverity.LIFE_THREATENING);
    }

    @Test
    void valueOf_returnsCorrectEnum() {
        assertThat(AllergySeverity.valueOf("MILD"))
                .isEqualTo(AllergySeverity.MILD);
        assertThat(AllergySeverity.valueOf("MODERATE"))
                .isEqualTo(AllergySeverity.MODERATE);
        assertThat(AllergySeverity.valueOf("SEVERE"))
                .isEqualTo(AllergySeverity.SEVERE);
        assertThat(AllergySeverity.valueOf("LIFE_THREATENING"))
                .isEqualTo(AllergySeverity.LIFE_THREATENING);
    }
}
