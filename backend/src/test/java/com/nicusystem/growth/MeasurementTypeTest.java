package com.nicusystem.growth;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for MeasurementType enum.
 */
class MeasurementTypeTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final MeasurementType[] values = MeasurementType.values();

        // Then
        assertThat(values).containsExactly(
                MeasurementType.WEIGHT,
                MeasurementType.LENGTH,
                MeasurementType.HEAD_CIRCUMFERENCE
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(MeasurementType.valueOf("WEIGHT")).isEqualTo(MeasurementType.WEIGHT);
        assertThat(MeasurementType.valueOf("LENGTH")).isEqualTo(MeasurementType.LENGTH);
        assertThat(MeasurementType.valueOf("HEAD_CIRCUMFERENCE"))
                .isEqualTo(MeasurementType.HEAD_CIRCUMFERENCE);
    }
}
