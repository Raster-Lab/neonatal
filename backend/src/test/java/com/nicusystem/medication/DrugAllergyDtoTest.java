package com.nicusystem.medication;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for DrugAllergyDto record.
 */
class DrugAllergyDtoTest {

    @Test
    void record_shouldStoreAndReturnAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();

        // When
        final DrugAllergyDto dto = new DrugAllergyDto(
                id, patientId, "Penicillin", "Rash",
                AllergySeverity.MODERATE, "Mild skin rash", true);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.allergenName()).isEqualTo("Penicillin");
        assertThat(dto.reactionType()).isEqualTo("Rash");
        assertThat(dto.severity()).isEqualTo(AllergySeverity.MODERATE);
        assertThat(dto.notes()).isEqualTo("Mild skin rash");
        assertThat(dto.active()).isTrue();
    }

    @Test
    void equals_sameValues_shouldBeEqual() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final DrugAllergyDto dto1 = new DrugAllergyDto(
                id, patientId, "Penicillin", "Rash",
                AllergySeverity.MODERATE, null, true);
        final DrugAllergyDto dto2 = new DrugAllergyDto(
                id, patientId, "Penicillin", "Rash",
                AllergySeverity.MODERATE, null, true);

        // When & Then
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void toString_shouldContainClassName() {
        // Given
        final DrugAllergyDto dto = new DrugAllergyDto(
                null, null, null, null, null, null, false);

        // When & Then
        assertThat(dto.toString()).contains("DrugAllergyDto");
    }
}
