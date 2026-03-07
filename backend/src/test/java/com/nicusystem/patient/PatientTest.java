package com.nicusystem.patient;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PatientTest {

    @Test
    void noArgConstructor_shouldCreateInstanceWithActiveTrue() {
        // Given & When
        final Patient patient = new Patient();

        // Then
        assertThat(patient.isActive()).isTrue();
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        // Given
        final Patient patient = new Patient();
        final UUID motherId = UUID.randomUUID();
        final Instant now = Instant.now();

        // When
        patient.setMrn("NICU-00001");
        patient.setFirstName("Baby");
        patient.setLastName("Doe");
        patient.setGender(Gender.MALE);
        patient.setDateOfBirth(now);
        patient.setBirthWeightGrams(3200);
        patient.setBirthLengthCm(50.0);
        patient.setHeadCircumferenceCm(34.0);
        patient.setGestationalAgeWeeks(38);
        patient.setGestationalAgeDays(3);
        patient.setDeliveryType(DeliveryType.VAGINAL);
        patient.setApgarOneMinute(7);
        patient.setApgarFiveMinute(9);
        patient.setApgarTenMinute(10);
        patient.setMotherId(motherId);
        patient.setActive(false);
        patient.setAdmissionDate(now);
        patient.setBedNumber("NICU-A1");

        // Then
        assertThat(patient.getMrn()).isEqualTo("NICU-00001");
        assertThat(patient.getFirstName()).isEqualTo("Baby");
        assertThat(patient.getLastName()).isEqualTo("Doe");
        assertThat(patient.getGender()).isEqualTo(Gender.MALE);
        assertThat(patient.getDateOfBirth()).isEqualTo(now);
        assertThat(patient.getBirthWeightGrams()).isEqualTo(3200);
        assertThat(patient.getBirthLengthCm()).isEqualTo(50.0);
        assertThat(patient.getHeadCircumferenceCm()).isEqualTo(34.0);
        assertThat(patient.getGestationalAgeWeeks()).isEqualTo(38);
        assertThat(patient.getGestationalAgeDays()).isEqualTo(3);
        assertThat(patient.getDeliveryType()).isEqualTo(DeliveryType.VAGINAL);
        assertThat(patient.getApgarOneMinute()).isEqualTo(7);
        assertThat(patient.getApgarFiveMinute()).isEqualTo(9);
        assertThat(patient.getApgarTenMinute()).isEqualTo(10);
        assertThat(patient.getMotherId()).isEqualTo(motherId);
        assertThat(patient.isActive()).isFalse();
        assertThat(patient.getAdmissionDate()).isEqualTo(now);
        assertThat(patient.getBedNumber()).isEqualTo("NICU-A1");
    }
}
