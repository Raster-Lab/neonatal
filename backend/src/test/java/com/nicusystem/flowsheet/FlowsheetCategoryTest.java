package com.nicusystem.flowsheet;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for FlowsheetCategory enum.
 */
class FlowsheetCategoryTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final FlowsheetCategory[] values = FlowsheetCategory.values();

        // Then
        assertThat(values).containsExactly(
                FlowsheetCategory.VITAL_SIGNS,
                FlowsheetCategory.INTAKE_OUTPUT,
                FlowsheetCategory.ASSESSMENT,
                FlowsheetCategory.INTERVENTION
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(FlowsheetCategory.valueOf("VITAL_SIGNS"))
                .isEqualTo(FlowsheetCategory.VITAL_SIGNS);
        assertThat(FlowsheetCategory.valueOf("INTAKE_OUTPUT"))
                .isEqualTo(FlowsheetCategory.INTAKE_OUTPUT);
        assertThat(FlowsheetCategory.valueOf("ASSESSMENT"))
                .isEqualTo(FlowsheetCategory.ASSESSMENT);
        assertThat(FlowsheetCategory.valueOf("INTERVENTION"))
                .isEqualTo(FlowsheetCategory.INTERVENTION);
    }
}
