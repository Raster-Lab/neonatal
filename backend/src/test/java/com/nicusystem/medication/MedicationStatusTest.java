package com.nicusystem.medication;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MedicationStatusTest {

    @Test
    void values_shouldContainAllStatuses() {
        // Given & When
        final MedicationStatus[] values = MedicationStatus.values();

        // Then
        assertThat(values).containsExactly(
                MedicationStatus.ORDERED,
                MedicationStatus.VERIFIED,
                MedicationStatus.ADMINISTERED,
                MedicationStatus.HELD,
                MedicationStatus.DISCONTINUED
        );
    }

    @Test
    void valueOf_validName_shouldReturnEnum() {
        assertThat(MedicationStatus.valueOf("ORDERED"))
                .isEqualTo(MedicationStatus.ORDERED);
        assertThat(MedicationStatus.valueOf("VERIFIED"))
                .isEqualTo(MedicationStatus.VERIFIED);
        assertThat(MedicationStatus.valueOf("ADMINISTERED"))
                .isEqualTo(MedicationStatus.ADMINISTERED);
        assertThat(MedicationStatus.valueOf("HELD"))
                .isEqualTo(MedicationStatus.HELD);
        assertThat(MedicationStatus.valueOf("DISCONTINUED"))
                .isEqualTo(MedicationStatus.DISCONTINUED);
    }
}
