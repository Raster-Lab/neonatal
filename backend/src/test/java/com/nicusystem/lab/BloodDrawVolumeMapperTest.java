package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for BloodDrawVolumeMapper.
 */
class BloodDrawVolumeMapperTest {

    private final BloodDrawVolumeMapper mapper = new BloodDrawVolumeMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final BloodDrawVolume entity = new BloodDrawVolume();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final UUID labOrderId = UUID.randomUUID();
        final Instant now = Instant.now();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setLabOrderId(labOrderId);
        entity.setDrawnAt(now);
        entity.setVolumeUl(300.0);
        entity.setDrawnBy("Nurse Jones");
        entity.setNotes("difficult draw");

        // When
        final BloodDrawVolumeDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.labOrderId()).isEqualTo(labOrderId);
        assertThat(dto.drawnAt()).isEqualTo(now);
        assertThat(dto.volumeUl()).isEqualTo(300.0);
        assertThat(dto.drawnBy()).isEqualTo("Nurse Jones");
        assertThat(dto.notes()).isEqualTo("difficult draw");
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final UUID labOrderId = UUID.randomUUID();
        final Instant now = Instant.now();
        final CreateBloodDrawVolumeRequest request = new CreateBloodDrawVolumeRequest(
                patientId, labOrderId, now, 200.0, "Tech", "notes");

        // When
        final BloodDrawVolume entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getLabOrderId()).isEqualTo(labOrderId);
        assertThat(entity.getDrawnAt()).isEqualTo(now);
        assertThat(entity.getVolumeUl()).isEqualTo(200.0);
        assertThat(entity.getDrawnBy()).isEqualTo("Tech");
        assertThat(entity.getNotes()).isEqualTo("notes");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();
        final CreateBloodDrawVolumeRequest request = new CreateBloodDrawVolumeRequest(
                patientId, null, now, 100.0, null, null);

        // When
        final BloodDrawVolume entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getLabOrderId()).isNull();
        assertThat(entity.getDrawnBy()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
