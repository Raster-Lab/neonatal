package com.nicusystem.patient;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PatientDtoTest {

    @Test
    void record_shouldStoreAndReturnAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID motherId = UUID.randomUUID();
        final Instant dob = Instant.parse("2024-01-15T10:30:00Z");
        final Instant admission = Instant.parse("2024-01-15T11:00:00Z");

        // When
        final PatientDto dto = new PatientDto(
                id, "NICU-00001", "Baby", "Doe", Gender.FEMALE, dob,
                3200, 50.0, 34.0, 38, 3, DeliveryType.C_SECTION,
                7, 9, 10, motherId, true, admission, "NICU-A1");

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.mrn()).isEqualTo("NICU-00001");
        assertThat(dto.firstName()).isEqualTo("Baby");
        assertThat(dto.lastName()).isEqualTo("Doe");
        assertThat(dto.gender()).isEqualTo(Gender.FEMALE);
        assertThat(dto.dateOfBirth()).isEqualTo(dob);
        assertThat(dto.birthWeightGrams()).isEqualTo(3200);
        assertThat(dto.birthLengthCm()).isEqualTo(50.0);
        assertThat(dto.headCircumferenceCm()).isEqualTo(34.0);
        assertThat(dto.gestationalAgeWeeks()).isEqualTo(38);
        assertThat(dto.gestationalAgeDays()).isEqualTo(3);
        assertThat(dto.deliveryType()).isEqualTo(DeliveryType.C_SECTION);
        assertThat(dto.apgarOneMinute()).isEqualTo(7);
        assertThat(dto.apgarFiveMinute()).isEqualTo(9);
        assertThat(dto.apgarTenMinute()).isEqualTo(10);
        assertThat(dto.motherId()).isEqualTo(motherId);
        assertThat(dto.active()).isTrue();
        assertThat(dto.admissionDate()).isEqualTo(admission);
        assertThat(dto.bedNumber()).isEqualTo("NICU-A1");
    }

    @Test
    void equals_sameValues_shouldBeEqual() {
        // Given
        final UUID id = UUID.randomUUID();
        final Instant dob = Instant.parse("2024-01-15T10:30:00Z");
        final PatientDto dto1 = new PatientDto(
                id, "M", "F", "L", Gender.MALE, dob,
                null, null, null, null, null, null,
                null, null, null, null, true, null, null);
        final PatientDto dto2 = new PatientDto(
                id, "M", "F", "L", Gender.MALE, dob,
                null, null, null, null, null, null,
                null, null, null, null, true, null, null);

        // When & Then
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void toString_shouldContainClassName() {
        // Given
        final PatientDto dto = new PatientDto(
                null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, false, null, null);

        // When & Then
        assertThat(dto.toString()).contains("PatientDto");
    }
}
