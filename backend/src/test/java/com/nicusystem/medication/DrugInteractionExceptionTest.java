package com.nicusystem.medication;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for DrugInteractionException.
 */
class DrugInteractionExceptionTest {

    @Test
    void constructor_setsMessage() {
        // Given
        final String message = "Contraindicated drug interaction between Warfarin and Aspirin: Increased bleeding risk";

        // When
        final DrugInteractionException ex = new DrugInteractionException(message);

        // Then
        assertThat(ex.getMessage()).isEqualTo(message);
        assertThat(ex).isInstanceOf(RuntimeException.class);
    }
}
