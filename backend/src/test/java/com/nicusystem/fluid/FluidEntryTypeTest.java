package com.nicusystem.fluid;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for FluidEntryType enum.
 */
class FluidEntryTypeTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final FluidEntryType[] values = FluidEntryType.values();

        // Then
        assertThat(values).containsExactly(
                FluidEntryType.INTAKE,
                FluidEntryType.OUTPUT
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(FluidEntryType.valueOf("INTAKE")).isEqualTo(FluidEntryType.INTAKE);
        assertThat(FluidEntryType.valueOf("OUTPUT")).isEqualTo(FluidEntryType.OUTPUT);
    }
}
