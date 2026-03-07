package com.nicusystem.insurance;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for InsuranceType enum.
 */
class InsuranceTypeTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final InsuranceType[] values = InsuranceType.values();

        // Then
        assertThat(values).containsExactly(
                InsuranceType.PRIMARY,
                InsuranceType.SECONDARY,
                InsuranceType.TERTIARY
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(InsuranceType.valueOf("PRIMARY")).isEqualTo(InsuranceType.PRIMARY);
        assertThat(InsuranceType.valueOf("SECONDARY")).isEqualTo(InsuranceType.SECONDARY);
        assertThat(InsuranceType.valueOf("TERTIARY")).isEqualTo(InsuranceType.TERTIARY);
    }
}
