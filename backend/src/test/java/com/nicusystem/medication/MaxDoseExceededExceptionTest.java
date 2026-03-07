package com.nicusystem.medication;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for MaxDoseExceededException.
 */
class MaxDoseExceededExceptionTest {

    @Test
    void constructor_setsMessage() {
        // Given
        final String message = "Dose 50.00 mg/kg exceeds maximum allowed dose of 30.00 mg/kg for medication Gentamicin";

        // When
        final MaxDoseExceededException ex = new MaxDoseExceededException(message);

        // Then
        assertThat(ex.getMessage()).isEqualTo(message);
        assertThat(ex).isInstanceOf(RuntimeException.class);
    }
}
