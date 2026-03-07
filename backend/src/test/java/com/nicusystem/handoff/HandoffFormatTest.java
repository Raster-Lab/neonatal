package com.nicusystem.handoff;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for HandoffFormat enum.
 */
class HandoffFormatTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final HandoffFormat[] values = HandoffFormat.values();

        // Then
        assertThat(values).containsExactly(
                HandoffFormat.IPASS,
                HandoffFormat.SBAR
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(HandoffFormat.valueOf("IPASS")).isEqualTo(HandoffFormat.IPASS);
        assertThat(HandoffFormat.valueOf("SBAR")).isEqualTo(HandoffFormat.SBAR);
    }
}
