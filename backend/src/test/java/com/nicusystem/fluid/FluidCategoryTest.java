package com.nicusystem.fluid;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for FluidCategory enum.
 */
class FluidCategoryTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final FluidCategory[] values = FluidCategory.values();

        // Then
        assertThat(values).containsExactly(
                FluidCategory.IV_FLUID,
                FluidCategory.ORAL_FEED,
                FluidCategory.BLOOD_PRODUCT,
                FluidCategory.URINE,
                FluidCategory.STOOL,
                FluidCategory.DRAIN,
                FluidCategory.EMESIS,
                FluidCategory.OTHER
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(FluidCategory.valueOf("IV_FLUID")).isEqualTo(FluidCategory.IV_FLUID);
        assertThat(FluidCategory.valueOf("ORAL_FEED")).isEqualTo(FluidCategory.ORAL_FEED);
        assertThat(FluidCategory.valueOf("URINE")).isEqualTo(FluidCategory.URINE);
        assertThat(FluidCategory.valueOf("OTHER")).isEqualTo(FluidCategory.OTHER);
    }
}
