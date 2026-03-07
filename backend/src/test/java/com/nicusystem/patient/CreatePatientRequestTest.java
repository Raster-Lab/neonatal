package com.nicusystem.patient;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreatePatientRequestTest {

    @Test
    void record_shouldStoreAndReturnAllFields() {
        // Given
        final Instant dob = Instant.parse("2024-01-15T10:30:00Z");
        final Instant admission = Instant.parse("2024-01-15T11:00:00Z");
        final UUID motherId = UUID.randomUUID();

        // When
        final CreatePatientRequest request = new CreatePatientRequest(
                "Baby", "Doe", Gender.MALE, dob,
                3200, 50.0, 34.0, 38, 3,
                DeliveryType.VAGINAL, 7, 9, 10,
                motherId, admission, "A1", null, null, null);

        // Then
        assertThat(request.firstName()).isEqualTo("Baby");
        assertThat(request.lastName()).isEqualTo("Doe");
        assertThat(request.gender()).isEqualTo(Gender.MALE);
        assertThat(request.dateOfBirth()).isEqualTo(dob);
        assertThat(request.birthWeightGrams()).isEqualTo(3200);
        assertThat(request.birthLengthCm()).isEqualTo(50.0);
        assertThat(request.headCircumferenceCm()).isEqualTo(34.0);
        assertThat(request.gestationalAgeWeeks()).isEqualTo(38);
        assertThat(request.gestationalAgeDays()).isEqualTo(3);
        assertThat(request.deliveryType()).isEqualTo(DeliveryType.VAGINAL);
        assertThat(request.apgarOneMinute()).isEqualTo(7);
        assertThat(request.apgarFiveMinute()).isEqualTo(9);
        assertThat(request.apgarTenMinute()).isEqualTo(10);
        assertThat(request.motherId()).isEqualTo(motherId);
        assertThat(request.admissionDate()).isEqualTo(admission);
        assertThat(request.bedNumber()).isEqualTo("A1");
    }

    @Test
    void equals_sameValues_shouldBeEqual() {
        // Given
        final Instant dob = Instant.parse("2024-01-15T10:30:00Z");
        final CreatePatientRequest req1 = new CreatePatientRequest(
                "Baby", "Doe", Gender.MALE, dob,
                null, null, null, null, null,
                null, null, null, null,
                null, null, null, null, null, null);
        final CreatePatientRequest req2 = new CreatePatientRequest(
                "Baby", "Doe", Gender.MALE, dob,
                null, null, null, null, null,
                null, null, null, null,
                null, null, null, null, null, null);

        // When & Then
        assertThat(req1).isEqualTo(req2);
        assertThat(req1.hashCode()).isEqualTo(req2.hashCode());
    }

    @Test
    void toString_shouldContainClassName() {
        // Given
        final CreatePatientRequest request = new CreatePatientRequest(
                "Baby", "Doe", Gender.MALE, Instant.now(),
                null, null, null, null, null,
                null, null, null, null,
                null, null, null, null, null, null);

        // When & Then
        assertThat(request.toString()).contains("CreatePatientRequest");
    }
}
