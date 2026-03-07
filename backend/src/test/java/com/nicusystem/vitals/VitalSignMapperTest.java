package com.nicusystem.vitals;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VitalSignMapperTest {

    private final VitalSignMapper mapper = new VitalSignMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.parse("2024-01-15T10:30:00Z");

        final VitalSign entity = new VitalSign();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setVitalType(VitalSignType.TEMPERATURE);
        entity.setValue(36.8);
        entity.setUnit("°C");
        entity.setRecordedAt(recordedAt);
        entity.setTemperatureSite(TemperatureSite.AXILLARY);
        entity.setManualEntry(true);
        entity.setNotes("Normal temperature");

        // When
        final VitalSignDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.vitalType()).isEqualTo(VitalSignType.TEMPERATURE);
        assertThat(dto.value()).isEqualTo(36.8);
        assertThat(dto.unit()).isEqualTo("°C");
        assertThat(dto.recordedAt()).isEqualTo(recordedAt);
        assertThat(dto.temperatureSite()).isEqualTo(TemperatureSite.AXILLARY);
        assertThat(dto.manualEntry()).isTrue();
        assertThat(dto.notes()).isEqualTo("Normal temperature");
    }

    @Test
    void toDto_nullTemperatureSite_shouldMapAsNull() {
        // Given
        final VitalSign entity = new VitalSign();
        entity.setId(UUID.randomUUID());
        entity.setPatientId(UUID.randomUUID());
        entity.setVitalType(VitalSignType.HEART_RATE);
        entity.setValue(140.0);
        entity.setUnit("bpm");
        entity.setRecordedAt(Instant.now());
        entity.setTemperatureSite(null);
        entity.setManualEntry(false);
        entity.setNotes(null);

        // When
        final VitalSignDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.temperatureSite()).isNull();
        assertThat(dto.notes()).isNull();
        assertThat(dto.manualEntry()).isFalse();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.parse("2024-01-15T10:30:00Z");
        final CreateVitalSignRequest request = new CreateVitalSignRequest(
                patientId, VitalSignType.TEMPERATURE, 37.2, "°C",
                recordedAt, TemperatureSite.RECTAL, true, "Slightly elevated");

        // When
        final VitalSign entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getVitalType()).isEqualTo(VitalSignType.TEMPERATURE);
        assertThat(entity.getValue()).isEqualTo(37.2);
        assertThat(entity.getUnit()).isEqualTo("°C");
        assertThat(entity.getRecordedAt()).isEqualTo(recordedAt);
        assertThat(entity.getTemperatureSite()).isEqualTo(TemperatureSite.RECTAL);
        assertThat(entity.isManualEntry()).isTrue();
        assertThat(entity.getNotes()).isEqualTo("Slightly elevated");
    }

    @Test
    void toEntity_nullOptionalFields_shouldMapAsNull() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.parse("2024-01-15T10:30:00Z");
        final CreateVitalSignRequest request = new CreateVitalSignRequest(
                patientId, VitalSignType.SPO2, 98.0, "%",
                recordedAt, null, false, null);

        // When
        final VitalSign entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getTemperatureSite()).isNull();
        assertThat(entity.isManualEntry()).isFalse();
        assertThat(entity.getNotes()).isNull();
    }
}
