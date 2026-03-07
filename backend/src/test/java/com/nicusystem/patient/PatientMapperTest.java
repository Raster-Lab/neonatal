package com.nicusystem.patient;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PatientMapperTest {

    private final PatientMapper mapper = new PatientMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID motherId = UUID.randomUUID();
        final Instant dob = Instant.parse("2024-01-15T10:30:00Z");
        final Instant admission = Instant.parse("2024-01-15T11:00:00Z");

        final Patient patient = new Patient();
        patient.setId(id);
        patient.setMrn("NICU-00001");
        patient.setFirstName("Baby");
        patient.setLastName("Doe");
        patient.setGender(Gender.FEMALE);
        patient.setDateOfBirth(dob);
        patient.setBirthWeightGrams(3200);
        patient.setBirthLengthCm(50.0);
        patient.setHeadCircumferenceCm(34.0);
        patient.setGestationalAgeWeeks(38);
        patient.setGestationalAgeDays(3);
        patient.setDeliveryType(DeliveryType.C_SECTION);
        patient.setApgarOneMinute(7);
        patient.setApgarFiveMinute(9);
        patient.setApgarTenMinute(10);
        patient.setMotherId(motherId);
        patient.setActive(true);
        patient.setAdmissionDate(admission);
        patient.setBedNumber("A1");

        // When
        final PatientDto dto = mapper.toDto(patient);

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
        assertThat(dto.bedNumber()).isEqualTo("A1");
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID motherId = UUID.randomUUID();
        final Instant dob = Instant.parse("2024-01-15T10:30:00Z");
        final Instant admission = Instant.parse("2024-01-15T11:00:00Z");
        final CreatePatientRequest request = new CreatePatientRequest(
                "Baby", "Doe", Gender.MALE, dob,
                3200, 50.0, 34.0, 38, 3,
                DeliveryType.VAGINAL, 7, 9, 10,
                motherId, admission, "B2");

        // When
        final Patient patient = mapper.toEntity(request, "NICU-99999");

        // Then
        assertThat(patient.getMrn()).isEqualTo("NICU-99999");
        assertThat(patient.getFirstName()).isEqualTo("Baby");
        assertThat(patient.getLastName()).isEqualTo("Doe");
        assertThat(patient.getGender()).isEqualTo(Gender.MALE);
        assertThat(patient.getDateOfBirth()).isEqualTo(dob);
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
        assertThat(patient.getAdmissionDate()).isEqualTo(admission);
        assertThat(patient.getBedNumber()).isEqualTo("B2");
    }

    @Test
    void toMotherDto_shouldMapAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final Mother mother = new Mother();
        mother.setId(id);
        mother.setFirstName("Jane");
        mother.setLastName("Doe");
        mother.setBloodType("A");
        mother.setRhFactor("positive");
        mother.setPrenatalCare("Regular checkups");
        mother.setMedications("Prenatal vitamins");
        mother.setInfections("None");
        mother.setActive(true);

        // When
        final MotherDto dto = mapper.toMotherDto(mother);

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
    void toMotherEntity_shouldMapAllFields() {
        // Given
        final CreateMotherRequest request = new CreateMotherRequest(
                "Jane", "Doe", "B", "negative",
                "Prenatal visits", "Iron supplements", "GBS");

        // When
        final Mother mother = mapper.toMotherEntity(request);

        // Then
        assertThat(mother.getFirstName()).isEqualTo("Jane");
        assertThat(mother.getLastName()).isEqualTo("Doe");
        assertThat(mother.getBloodType()).isEqualTo("B");
        assertThat(mother.getRhFactor()).isEqualTo("negative");
        assertThat(mother.getPrenatalCare()).isEqualTo("Prenatal visits");
        assertThat(mother.getMedications()).isEqualTo("Iron supplements");
        assertThat(mother.getInfections()).isEqualTo("GBS");
    }
}
