package com.nicusystem.medication;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateMedicationRequestTest {

    @Test
    void record_shouldStoreAndReturnAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant prescribedAt = Instant.parse("2024-01-15T10:30:00Z");

        // When
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "Gentamicin", 4.0, "mg/kg", "IV", "q24h",
                prescribedAt, "Dr. Jones", 2000,
                "Check trough levels", true);

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.name()).isEqualTo("Gentamicin");
        assertThat(request.dosage()).isEqualTo(4.0);
        assertThat(request.dosageUnit()).isEqualTo("mg/kg");
        assertThat(request.route()).isEqualTo("IV");
        assertThat(request.frequency()).isEqualTo("q24h");
        assertThat(request.prescribedAt()).isEqualTo(prescribedAt);
        assertThat(request.prescribedBy()).isEqualTo("Dr. Jones");
        assertThat(request.weightAtPrescription()).isEqualTo(2000);
        assertThat(request.notes()).isEqualTo("Check trough levels");
        assertThat(request.highAlert()).isTrue();
    }

    @Test
    void record_withNullOptionalFields_shouldStoreNull() {
        // Given
        final UUID patientId = UUID.randomUUID();

        // When
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "Caffeine Citrate", 20.0, "mg/kg", "oral",
                "daily", null, null, null, null, false);

        // Then
        assertThat(request.prescribedAt()).isNull();
        assertThat(request.prescribedBy()).isNull();
        assertThat(request.weightAtPrescription()).isNull();
        assertThat(request.notes()).isNull();
        assertThat(request.highAlert()).isFalse();
    }

    @Test
    void equals_sameValues_shouldBeEqual() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant prescribedAt = Instant.parse("2024-01-15T10:30:00Z");
        final CreateMedicationRequest req1 = new CreateMedicationRequest(
                patientId, "Ampicillin", 50.0, "mg/kg", "IV", "q12h",
                prescribedAt, "Dr. Smith", 1500, null, false);
        final CreateMedicationRequest req2 = new CreateMedicationRequest(
                patientId, "Ampicillin", 50.0, "mg/kg", "IV", "q12h",
                prescribedAt, "Dr. Smith", 1500, null, false);

        // When & Then
        assertThat(req1).isEqualTo(req2);
        assertThat(req1.hashCode()).isEqualTo(req2.hashCode());
    }

    @Test
    void toString_shouldContainClassName() {
        // Given
        final CreateMedicationRequest request = new CreateMedicationRequest(
                UUID.randomUUID(), "Ampicillin", 50.0, "mg/kg", "IV",
                "q12h", Instant.now(), null, null, null, false);

        // When & Then
        assertThat(request.toString()).contains("CreateMedicationRequest");
    }
}
