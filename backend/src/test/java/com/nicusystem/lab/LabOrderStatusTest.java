package com.nicusystem.lab;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for LabOrderStatus enum.
 */
class LabOrderStatusTest {

    @Test
    void shouldHaveExpectedValues() {
        // Given / When
        final LabOrderStatus[] values = LabOrderStatus.values();

        // Then
        assertThat(values).containsExactly(
                LabOrderStatus.ORDERED,
                LabOrderStatus.COLLECTED,
                LabOrderStatus.IN_PROGRESS,
                LabOrderStatus.RESULTED,
                LabOrderStatus.CANCELLED
        );
    }

    @Test
    void valueOf_shouldReturnCorrectValue() {
        // Given / When / Then
        assertThat(LabOrderStatus.valueOf("ORDERED")).isEqualTo(LabOrderStatus.ORDERED);
        assertThat(LabOrderStatus.valueOf("COLLECTED")).isEqualTo(LabOrderStatus.COLLECTED);
        assertThat(LabOrderStatus.valueOf("IN_PROGRESS")).isEqualTo(LabOrderStatus.IN_PROGRESS);
        assertThat(LabOrderStatus.valueOf("RESULTED")).isEqualTo(LabOrderStatus.RESULTED);
        assertThat(LabOrderStatus.valueOf("CANCELLED")).isEqualTo(LabOrderStatus.CANCELLED);
    }
}
