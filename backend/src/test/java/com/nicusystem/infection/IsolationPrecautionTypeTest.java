package com.nicusystem.infection;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link IsolationPrecautionType} enum.
 */
class IsolationPrecautionTypeTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final IsolationPrecautionType[] values =
                IsolationPrecautionType.values();

        // Then
        assertThat(values).containsExactly(
                IsolationPrecautionType.STANDARD,
                IsolationPrecautionType.CONTACT,
                IsolationPrecautionType.ENHANCED_CONTACT,
                IsolationPrecautionType.DROPLET,
                IsolationPrecautionType.AIRBORNE
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(IsolationPrecautionType.valueOf("STANDARD"))
                .isEqualTo(IsolationPrecautionType.STANDARD);
        assertThat(IsolationPrecautionType.valueOf("CONTACT"))
                .isEqualTo(IsolationPrecautionType.CONTACT);
        assertThat(
                IsolationPrecautionType.valueOf("ENHANCED_CONTACT"))
                .isEqualTo(
                        IsolationPrecautionType.ENHANCED_CONTACT);
        assertThat(IsolationPrecautionType.valueOf("DROPLET"))
                .isEqualTo(IsolationPrecautionType.DROPLET);
        assertThat(IsolationPrecautionType.valueOf("AIRBORNE"))
                .isEqualTo(IsolationPrecautionType.AIRBORNE);
    }
}
