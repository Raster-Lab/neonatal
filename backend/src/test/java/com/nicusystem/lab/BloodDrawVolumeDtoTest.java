package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for BloodDrawVolumeDto record.
 */
class BloodDrawVolumeDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final UUID labOrderId = UUID.randomUUID();
        final Instant now = Instant.now();

        // When
        final BloodDrawVolumeDto dto = new BloodDrawVolumeDto(
                id, patientId, labOrderId, now, 300.0, "Nurse Jones", "difficult draw", now, now);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.labOrderId()).isEqualTo(labOrderId);
        assertThat(dto.drawnAt()).isEqualTo(now);
        assertThat(dto.volumeUl()).isEqualTo(300.0);
        assertThat(dto.drawnBy()).isEqualTo("Nurse Jones");
        assertThat(dto.notes()).isEqualTo("difficult draw");
        assertThat(dto.createdAt()).isEqualTo(now);
        assertThat(dto.updatedAt()).isEqualTo(now);
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();

        // When
        final BloodDrawVolumeDto dto = new BloodDrawVolumeDto(
                id, patientId, null, now, 200.0, null, null, now, now);

        // Then
        assertThat(dto.labOrderId()).isNull();
        assertThat(dto.drawnBy()).isNull();
        assertThat(dto.notes()).isNull();
    }
}
