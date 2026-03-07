package com.nicusystem.nutrition;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link FeedingType} enum.
 */
class FeedingTypeTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final FeedingType[] values = FeedingType.values();

        // Then
        assertThat(values).containsExactly(
                FeedingType.BREAST_MILK,
                FeedingType.DONOR_MILK,
                FeedingType.FORMULA,
                FeedingType.FORTIFIED_BREAST_MILK,
                FeedingType.MIXED
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(FeedingType.valueOf("BREAST_MILK")).isEqualTo(FeedingType.BREAST_MILK);
        assertThat(FeedingType.valueOf("DONOR_MILK")).isEqualTo(FeedingType.DONOR_MILK);
        assertThat(FeedingType.valueOf("FORMULA")).isEqualTo(FeedingType.FORMULA);
        assertThat(FeedingType.valueOf("FORTIFIED_BREAST_MILK"))
                .isEqualTo(FeedingType.FORTIFIED_BREAST_MILK);
        assertThat(FeedingType.valueOf("MIXED")).isEqualTo(FeedingType.MIXED);
    }
}
