package com.nicusystem.medication;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for DrugAllergyException.
 */
class DrugAllergyExceptionTest {

    @Test
    void constructor_setsMessageAndFields() {
        // Given
        final String message =
                "Patient has allergy to Penicillin which conflicts"
                        + " with medication Amoxicillin";
        final String allergenName = "Penicillin";
        final String medicationName = "Amoxicillin";

        // When
        final DrugAllergyException ex =
                new DrugAllergyException(
                        message, allergenName, medicationName);

        // Then
        assertThat(ex.getMessage()).isEqualTo(message);
        assertThat(ex.getAllergenName()).isEqualTo(allergenName);
        assertThat(ex.getMedicationName()).isEqualTo(medicationName);
        assertThat(ex).isInstanceOf(RuntimeException.class);
    }
}
