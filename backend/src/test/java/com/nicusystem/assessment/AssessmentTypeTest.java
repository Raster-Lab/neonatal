package com.nicusystem.assessment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for AssessmentType enum.
 */
class AssessmentTypeTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final AssessmentType[] values = AssessmentType.values();

        // Then
        assertThat(values).containsExactly(
                AssessmentType.ADMISSION,
                AssessmentType.SHIFT,
                AssessmentType.DAILY_ROUND,
                AssessmentType.DISCHARGE
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(AssessmentType.valueOf("ADMISSION")).isEqualTo(AssessmentType.ADMISSION);
        assertThat(AssessmentType.valueOf("SHIFT")).isEqualTo(AssessmentType.SHIFT);
        assertThat(AssessmentType.valueOf("DAILY_ROUND")).isEqualTo(AssessmentType.DAILY_ROUND);
        assertThat(AssessmentType.valueOf("DISCHARGE")).isEqualTo(AssessmentType.DISCHARGE);
    }
}
