package com.nicusystem.patient;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MotherDtoTest {

    @Test
    void record_shouldStoreAndReturnAllFields() {
        // Given
        final UUID id = UUID.randomUUID();

        // When
        final MotherDto dto = new MotherDto(
                id, "Jane", "Doe", "A", "positive",
                "Regular checkups", "Prenatal vitamins", "None", true);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.firstName()).isEqualTo("Jane");
        assertThat(dto.lastName()).isEqualTo("Doe");
        assertThat(dto.bloodType()).isEqualTo("A");
        assertThat(dto.rhFactor()).isEqualTo("positive");
        assertThat(dto.prenatalCare()).isEqualTo("Regular checkups");
        assertThat(dto.medications()).isEqualTo("Prenatal vitamins");
        assertThat(dto.infections()).isEqualTo("None");
        assertThat(dto.active()).isTrue();
    }

    @Test
    void equals_sameValues_shouldBeEqual() {
        // Given
        final UUID id = UUID.randomUUID();
        final MotherDto dto1 = new MotherDto(
                id, "Jane", "Doe", null, null, null, null, null, true);
        final MotherDto dto2 = new MotherDto(
                id, "Jane", "Doe", null, null, null, null, null, true);

        // When & Then
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void toString_shouldContainClassName() {
        // Given
        final MotherDto dto = new MotherDto(
                null, null, null, null, null, null, null, null, false);

        // When & Then
        assertThat(dto.toString()).contains("MotherDto");
    }
}
