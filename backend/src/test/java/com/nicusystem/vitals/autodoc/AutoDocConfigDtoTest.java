package com.nicusystem.vitals.autodoc;

import java.util.UUID;

import com.nicusystem.vitals.VitalSignType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AutoDocConfigDtoTest {

    @Test
    void record_shouldStoreAndReturnAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();

        // When
        final AutoDocConfigDto dto = new AutoDocConfigDto(
                id, patientId, VitalSignType.SPO2,
                AutoDocInterval.EVERY_30_MINUTES, true, "Monitor oxygen");

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.vitalType()).isEqualTo(VitalSignType.SPO2);
        assertThat(dto.interval()).isEqualTo(AutoDocInterval.EVERY_30_MINUTES);
        assertThat(dto.enabled()).isTrue();
        assertThat(dto.notes()).isEqualTo("Monitor oxygen");
    }

    @Test
    void equals_sameValues_shouldBeEqual() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final AutoDocConfigDto dto1 = new AutoDocConfigDto(
                id, patientId, VitalSignType.HEART_RATE,
                AutoDocInterval.HOURLY, true, null);
        final AutoDocConfigDto dto2 = new AutoDocConfigDto(
                id, patientId, VitalSignType.HEART_RATE,
                AutoDocInterval.HOURLY, true, null);

        // When & Then
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void toString_shouldContainClassName() {
        // Given
        final AutoDocConfigDto dto = new AutoDocConfigDto(
                null, null, null, null, false, null);

        // When & Then
        assertThat(dto.toString()).contains("AutoDocConfigDto");
    }
}
