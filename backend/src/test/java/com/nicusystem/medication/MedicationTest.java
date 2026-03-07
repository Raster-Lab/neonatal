package com.nicusystem.medication;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MedicationTest {

    @Test
    void noArgConstructor_shouldCreateInstance() {
        // Given & When
        final Medication medication = new Medication();

        // Then
        assertThat(medication).isNotNull();
        assertThat(medication.isHighAlert()).isFalse();
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        // Given
        final Medication medication = new Medication();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();

        // When
        medication.setPatientId(patientId);
        medication.setName("Ampicillin");
        medication.setDosage(50.0);
        medication.setDosageUnit("mg/kg");
        medication.setRoute("IV");
        medication.setFrequency("q12h");
        medication.setStatus(MedicationStatus.ORDERED);
        medication.setPrescribedAt(now);
        medication.setPrescribedBy("Dr. Smith");
        medication.setWeightAtPrescription(1500);
        medication.setNotes("Monitor renal function");
        medication.setHighAlert(true);

        // Then
        assertThat(medication.getPatientId()).isEqualTo(patientId);
        assertThat(medication.getName()).isEqualTo("Ampicillin");
        assertThat(medication.getDosage()).isEqualTo(50.0);
        assertThat(medication.getDosageUnit()).isEqualTo("mg/kg");
        assertThat(medication.getRoute()).isEqualTo("IV");
        assertThat(medication.getFrequency()).isEqualTo("q12h");
        assertThat(medication.getStatus()).isEqualTo(MedicationStatus.ORDERED);
        assertThat(medication.getPrescribedAt()).isEqualTo(now);
        assertThat(medication.getPrescribedBy()).isEqualTo("Dr. Smith");
        assertThat(medication.getWeightAtPrescription()).isEqualTo(1500);
        assertThat(medication.getNotes()).isEqualTo("Monitor renal function");
        assertThat(medication.isHighAlert()).isTrue();

        // New fields
        medication.setMaxDoseMgKgPerDay(30.0);
        medication.setRenalAdjustmentFactor(0.75);
        medication.setHepaticAdjustmentFactor(0.5);
        assertThat(medication.getMaxDoseMgKgPerDay()).isEqualTo(30.0);
        assertThat(medication.getRenalAdjustmentFactor()).isEqualTo(0.75);
        assertThat(medication.getHepaticAdjustmentFactor()).isEqualTo(0.5);
    }

    @Test
    void settersAndGetters_nullOptionalFields_shouldAcceptNull() {
        // Given
        final Medication medication = new Medication();

        // When
        medication.setPrescribedAt(null);
        medication.setPrescribedBy(null);
        medication.setWeightAtPrescription(null);
        medication.setNotes(null);

        // Then
        assertThat(medication.getPrescribedAt()).isNull();
        assertThat(medication.getPrescribedBy()).isNull();
        assertThat(medication.getWeightAtPrescription()).isNull();
        assertThat(medication.getNotes()).isNull();

        // New fields default to null
        assertThat(medication.getMaxDoseMgKgPerDay()).isNull();
        assertThat(medication.getRenalAdjustmentFactor()).isNull();
        assertThat(medication.getHepaticAdjustmentFactor()).isNull();
    }
}
