package com.nicusystem.medication;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for DrugInteractionSeverity enum.
 */
class DrugInteractionSeverityTest {

    @Test
    void values_containsAllExpectedSeverities() {
        // When
        final DrugInteractionSeverity[] values = DrugInteractionSeverity.values();

        // Then
        assertThat(values).containsExactlyInAnyOrder(
                DrugInteractionSeverity.CONTRAINDICATED,
                DrugInteractionSeverity.MAJOR,
                DrugInteractionSeverity.MODERATE,
                DrugInteractionSeverity.MINOR);
    }

    @Test
    void valueOf_returnsCorrectEnum() {
        assertThat(DrugInteractionSeverity.valueOf("CONTRAINDICATED"))
                .isEqualTo(DrugInteractionSeverity.CONTRAINDICATED);
        assertThat(DrugInteractionSeverity.valueOf("MAJOR"))
                .isEqualTo(DrugInteractionSeverity.MAJOR);
        assertThat(DrugInteractionSeverity.valueOf("MODERATE"))
                .isEqualTo(DrugInteractionSeverity.MODERATE);
        assertThat(DrugInteractionSeverity.valueOf("MINOR"))
                .isEqualTo(DrugInteractionSeverity.MINOR);
    }
}
