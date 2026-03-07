package com.nicusystem.consent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ConsentStatus enum.
 */
class ConsentStatusTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final ConsentStatus[] values = ConsentStatus.values();

        // Then
        assertThat(values).containsExactly(
                ConsentStatus.GRANTED,
                ConsentStatus.DENIED,
                ConsentStatus.PENDING,
                ConsentStatus.REVOKED
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(ConsentStatus.valueOf("GRANTED")).isEqualTo(ConsentStatus.GRANTED);
        assertThat(ConsentStatus.valueOf("DENIED")).isEqualTo(ConsentStatus.DENIED);
        assertThat(ConsentStatus.valueOf("PENDING")).isEqualTo(ConsentStatus.PENDING);
        assertThat(ConsentStatus.valueOf("REVOKED")).isEqualTo(ConsentStatus.REVOKED);
    }
}
