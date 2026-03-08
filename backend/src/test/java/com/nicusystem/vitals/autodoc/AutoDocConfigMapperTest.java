package com.nicusystem.vitals.autodoc;

import java.util.UUID;

import com.nicusystem.vitals.VitalSignType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AutoDocConfigMapperTest {

    private final AutoDocConfigMapper mapper = new AutoDocConfigMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final AutoDocConfig entity = new AutoDocConfig();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setVitalType(VitalSignType.HEART_RATE);
        entity.setInterval(AutoDocInterval.HOURLY);
        entity.setEnabled(true);
        entity.setNotes("Auto-doc for HR");

        // When
        final AutoDocConfigDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.vitalType()).isEqualTo(VitalSignType.HEART_RATE);
        assertThat(dto.interval()).isEqualTo(AutoDocInterval.HOURLY);
        assertThat(dto.enabled()).isTrue();
        assertThat(dto.notes()).isEqualTo("Auto-doc for HR");
    }

    @Test
    void toDto_nullNotes_shouldMapAsNull() {
        // Given
        final AutoDocConfig entity = new AutoDocConfig();
        entity.setId(UUID.randomUUID());
        entity.setPatientId(UUID.randomUUID());
        entity.setVitalType(VitalSignType.SPO2);
        entity.setInterval(AutoDocInterval.EVERY_30_MINUTES);
        entity.setEnabled(false);
        entity.setNotes(null);

        // When
        final AutoDocConfigDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.notes()).isNull();
        assertThat(dto.enabled()).isFalse();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateAutoDocConfigRequest request = new CreateAutoDocConfigRequest(
                patientId, VitalSignType.TEMPERATURE,
                AutoDocInterval.EVERY_4_HOURS, "Temperature monitoring");

        // When
        final AutoDocConfig entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getVitalType()).isEqualTo(VitalSignType.TEMPERATURE);
        assertThat(entity.getInterval()).isEqualTo(AutoDocInterval.EVERY_4_HOURS);
        assertThat(entity.getNotes()).isEqualTo("Temperature monitoring");
    }

    @Test
    void toEntity_nullNotes_shouldMapAsNull() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateAutoDocConfigRequest request = new CreateAutoDocConfigRequest(
                patientId, VitalSignType.HEART_RATE,
                AutoDocInterval.EVERY_2_HOURS, null);

        // When
        final AutoDocConfig entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getNotes()).isNull();
    }
}
