package com.nicusystem.procedure;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ProcedureType enum.
 */
class ProcedureTypeTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final ProcedureType[] values = ProcedureType.values();

        // Then
        assertThat(values).containsExactly(
                ProcedureType.INTUBATION,
                ProcedureType.LINE_PLACEMENT,
                ProcedureType.LUMBAR_PUNCTURE,
                ProcedureType.UMBILICAL_LINE,
                ProcedureType.CHEST_TUBE,
                ProcedureType.EXCHANGE_TRANSFUSION
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(ProcedureType.valueOf("INTUBATION"))
                .isEqualTo(ProcedureType.INTUBATION);
        assertThat(ProcedureType.valueOf("LINE_PLACEMENT"))
                .isEqualTo(ProcedureType.LINE_PLACEMENT);
        assertThat(ProcedureType.valueOf("LUMBAR_PUNCTURE"))
                .isEqualTo(ProcedureType.LUMBAR_PUNCTURE);
        assertThat(ProcedureType.valueOf("UMBILICAL_LINE"))
                .isEqualTo(ProcedureType.UMBILICAL_LINE);
        assertThat(ProcedureType.valueOf("CHEST_TUBE"))
                .isEqualTo(ProcedureType.CHEST_TUBE);
        assertThat(ProcedureType.valueOf("EXCHANGE_TRANSFUSION"))
                .isEqualTo(ProcedureType.EXCHANGE_TRANSFUSION);
    }
}
