package com.nicusystem.medication;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MedicationMapperTest {

    private final MedicationMapper mapper = new MedicationMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant prescribedAt = Instant.parse("2024-01-15T10:30:00Z");

        final Medication entity = new Medication();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setName("Ampicillin");
        entity.setDosage(50.0);
        entity.setDosageUnit("mg/kg");
        entity.setRoute("IV");
        entity.setFrequency("q12h");
        entity.setStatus(MedicationStatus.ORDERED);
        entity.setPrescribedAt(prescribedAt);
        entity.setPrescribedBy("Dr. Smith");
        entity.setWeightAtPrescription(1500);
        entity.setNotes("Monitor renal function");
        entity.setHighAlert(true);

        // When
        final MedicationDto dto = mapper.toDto(entity);

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
        assertThat(dto.maxDoseMgKgPerDay()).isNull();
        assertThat(dto.renalAdjustmentFactor()).isNull();
        assertThat(dto.hepaticAdjustmentFactor()).isNull();
    }

    @Test
    void toDto_nullOptionalFields_shouldMapAsNull() {
        // Given
        final Medication entity = new Medication();
        entity.setId(UUID.randomUUID());
        entity.setPatientId(UUID.randomUUID());
        entity.setName("Caffeine Citrate");
        entity.setDosage(20.0);
        entity.setDosageUnit("mg/kg");
        entity.setRoute("oral");
        entity.setFrequency("daily");
        entity.setStatus(MedicationStatus.VERIFIED);
        entity.setPrescribedAt(null);
        entity.setPrescribedBy(null);
        entity.setWeightAtPrescription(null);
        entity.setNotes(null);
        entity.setHighAlert(false);

        // When
        final MedicationDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.prescribedAt()).isNull();
        assertThat(dto.prescribedBy()).isNull();
        assertThat(dto.weightAtPrescription()).isNull();
        assertThat(dto.notes()).isNull();
        assertThat(dto.highAlert()).isFalse();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant prescribedAt = Instant.parse("2024-01-15T10:30:00Z");
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "Gentamicin", 4.0, "mg/kg", "IV", "q24h",
                prescribedAt, "Dr. Jones", 2000,
                "Check trough levels", true, null, null, null);

        // When
        final Medication entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getName()).isEqualTo("Gentamicin");
        assertThat(entity.getDosage()).isEqualTo(4.0);
        assertThat(entity.getDosageUnit()).isEqualTo("mg/kg");
        assertThat(entity.getRoute()).isEqualTo("IV");
        assertThat(entity.getFrequency()).isEqualTo("q24h");
        assertThat(entity.getStatus()).isEqualTo(MedicationStatus.ORDERED);
        assertThat(entity.getPrescribedAt()).isEqualTo(prescribedAt);
        assertThat(entity.getPrescribedBy()).isEqualTo("Dr. Jones");
        assertThat(entity.getWeightAtPrescription()).isEqualTo(2000);
        assertThat(entity.getNotes()).isEqualTo("Check trough levels");
        assertThat(entity.isHighAlert()).isTrue();
        assertThat(entity.getMaxDoseMgKgPerDay()).isNull();
        assertThat(entity.getRenalAdjustmentFactor()).isNull();
        assertThat(entity.getHepaticAdjustmentFactor()).isNull();
    }

    @Test
    void toEntity_nullOptionalFields_shouldMapAsNull() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "Caffeine Citrate", 20.0, "mg/kg", "oral",
                "daily", null, null, null, null, false, null, null, null);

        // When
        final Medication entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPrescribedAt()).isNull();
        assertThat(entity.getPrescribedBy()).isNull();
        assertThat(entity.getWeightAtPrescription()).isNull();
        assertThat(entity.getNotes()).isNull();
        assertThat(entity.isHighAlert()).isFalse();
    }
}
