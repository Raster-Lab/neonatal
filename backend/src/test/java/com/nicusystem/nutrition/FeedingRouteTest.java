package com.nicusystem.nutrition;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link FeedingRoute} enum.
 */
class FeedingRouteTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final FeedingRoute[] values = FeedingRoute.values();

        // Then
        assertThat(values).containsExactly(
                FeedingRoute.ORAL,
                FeedingRoute.NASOGASTRIC,
                FeedingRoute.OROGASTRIC,
                FeedingRoute.GASTROSTOMY,
                FeedingRoute.NASOJEJUNAL,
                FeedingRoute.TRANSPYLORIC
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(FeedingRoute.valueOf("ORAL")).isEqualTo(FeedingRoute.ORAL);
        assertThat(FeedingRoute.valueOf("NASOGASTRIC")).isEqualTo(FeedingRoute.NASOGASTRIC);
        assertThat(FeedingRoute.valueOf("OROGASTRIC")).isEqualTo(FeedingRoute.OROGASTRIC);
        assertThat(FeedingRoute.valueOf("GASTROSTOMY")).isEqualTo(FeedingRoute.GASTROSTOMY);
        assertThat(FeedingRoute.valueOf("NASOJEJUNAL")).isEqualTo(FeedingRoute.NASOJEJUNAL);
        assertThat(FeedingRoute.valueOf("TRANSPYLORIC")).isEqualTo(FeedingRoute.TRANSPYLORIC);
    }
}
