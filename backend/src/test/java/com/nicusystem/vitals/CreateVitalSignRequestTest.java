package com.nicusystem.vitals;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateVitalSignRequestTest {

    @Test
    void record_shouldStoreAndReturnAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.parse("2024-01-15T10:30:00Z");

        // When
        final CreateVitalSignRequest request = new CreateVitalSignRequest(
                patientId, VitalSignType.SPO2, 98.0, "%",
                recordedAt, null, false, "Stable");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.vitalType()).isEqualTo(VitalSignType.SPO2);
        assertThat(request.value()).isEqualTo(98.0);
        assertThat(request.unit()).isEqualTo("%");
        assertThat(request.recordedAt()).isEqualTo(recordedAt);
        assertThat(request.temperatureSite()).isNull();
        assertThat(request.manualEntry()).isFalse();
        assertThat(request.notes()).isEqualTo("Stable");
    }

    @Test
    void record_withTemperatureSite_shouldStoreField() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.parse("2024-01-15T10:30:00Z");

        // When
        final CreateVitalSignRequest request = new CreateVitalSignRequest(
                patientId, VitalSignType.TEMPERATURE, 36.5, "°C",
                recordedAt, TemperatureSite.RECTAL, true, "Post-feed");

        // Then
        assertThat(request.temperatureSite()).isEqualTo(TemperatureSite.RECTAL);
        assertThat(request.manualEntry()).isTrue();
    }

    @Test
    void equals_sameValues_shouldBeEqual() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.parse("2024-01-15T10:30:00Z");
        final CreateVitalSignRequest req1 = new CreateVitalSignRequest(
                patientId, VitalSignType.HEART_RATE, 140.0, "bpm",
                recordedAt, null, false, null);
        final CreateVitalSignRequest req2 = new CreateVitalSignRequest(
                patientId, VitalSignType.HEART_RATE, 140.0, "bpm",
                recordedAt, null, false, null);

        // When & Then
        assertThat(req1).isEqualTo(req2);
        assertThat(req1.hashCode()).isEqualTo(req2.hashCode());
    }

    @Test
    void toString_shouldContainClassName() {
        // Given
        final CreateVitalSignRequest request = new CreateVitalSignRequest(
                UUID.randomUUID(), VitalSignType.HEART_RATE, 140.0, "bpm",
                Instant.now(), null, false, null);

        // When & Then
        assertThat(request.toString()).contains("CreateVitalSignRequest");
    }
}
