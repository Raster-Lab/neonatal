package com.nicusystem.medication;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MedicationDtoTest {

    @Test
    void record_shouldStoreAndReturnAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant prescribedAt = Instant.parse("2024-01-15T10:30:00Z");

        // When
        final MedicationDto dto = new MedicationDto(
                id, patientId, "Ampicillin", 50.0, "mg/kg", "IV", "q12h",
                MedicationStatus.ORDERED, prescribedAt, "Dr. Smith",
                1500, "Monitor renal function", true);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.name()).isEqualTo("Ampicillin");
        assertThat(dto.dosage()).isEqualTo(50.0);
        assertThat(dto.dosageUnit()).isEqualTo("mg/kg");
        assertThat(dto.route()).isEqualTo("IV");
        assertThat(dto.frequency()).isEqualTo("q12h");
        assertThat(dto.status()).isEqualTo(MedicationStatus.ORDERED);
        assertThat(dto.prescribedAt()).isEqualTo(prescribedAt);
        assertThat(dto.prescribedBy()).isEqualTo("Dr. Smith");
        assertThat(dto.weightAtPrescription()).isEqualTo(1500);
        assertThat(dto.notes()).isEqualTo("Monitor renal function");
        assertThat(dto.highAlert()).isTrue();
    }

    @Test
    void equals_sameValues_shouldBeEqual() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant prescribedAt = Instant.parse("2024-01-15T10:30:00Z");
        final MedicationDto dto1 = new MedicationDto(
                id, patientId, "Ampicillin", 50.0, "mg/kg", "IV", "q12h",
                MedicationStatus.ORDERED, prescribedAt, "Dr. Smith",
                1500, null, false);
        final MedicationDto dto2 = new MedicationDto(
                id, patientId, "Ampicillin", 50.0, "mg/kg", "IV", "q12h",
                MedicationStatus.ORDERED, prescribedAt, "Dr. Smith",
                1500, null, false);

        // When & Then
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void toString_shouldContainClassName() {
        // Given
        final MedicationDto dto = new MedicationDto(
                null, null, null, null, null, null, null,
                null, null, null, null, null, false);

        // When & Then
        assertThat(dto.toString()).contains("MedicationDto");
    }
}
