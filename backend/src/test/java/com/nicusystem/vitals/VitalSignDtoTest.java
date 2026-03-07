package com.nicusystem.vitals;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VitalSignDtoTest {

    @Test
    void record_shouldStoreAndReturnAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.parse("2024-01-15T10:30:00Z");

        // When
        final VitalSignDto dto = new VitalSignDto(
                id, patientId, VitalSignType.TEMPERATURE, 36.8,
                "°C", recordedAt, TemperatureSite.AXILLARY, true, "Normal");

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.vitalType()).isEqualTo(VitalSignType.TEMPERATURE);
        assertThat(dto.value()).isEqualTo(36.8);
        assertThat(dto.unit()).isEqualTo("°C");
        assertThat(dto.recordedAt()).isEqualTo(recordedAt);
        assertThat(dto.temperatureSite()).isEqualTo(TemperatureSite.AXILLARY);
        assertThat(dto.manualEntry()).isTrue();
        assertThat(dto.notes()).isEqualTo("Normal");
    }

    @Test
    void equals_sameValues_shouldBeEqual() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.parse("2024-01-15T10:30:00Z");
        final VitalSignDto dto1 = new VitalSignDto(
                id, patientId, VitalSignType.HEART_RATE, 140.0,
                "bpm", recordedAt, null, false, null);
        final VitalSignDto dto2 = new VitalSignDto(
                id, patientId, VitalSignType.HEART_RATE, 140.0,
                "bpm", recordedAt, null, false, null);

        // When & Then
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void toString_shouldContainClassName() {
        // Given
        final VitalSignDto dto = new VitalSignDto(
                null, null, null, null, null, null, null, false, null);

        // When & Then
        assertThat(dto.toString()).contains("VitalSignDto");
    }
}
