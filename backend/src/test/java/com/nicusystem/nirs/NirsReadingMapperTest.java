package com.nicusystem.nirs;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for NirsReadingMapper.
 */
class NirsReadingMapperTest {

    private final NirsReadingMapper mapper = new NirsReadingMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final NirsReading entity = new NirsReading();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setSite(NirsSite.LEFT_CEREBRAL);
        entity.setRso2Value(72.5);
        entity.setBaseline(75.0);
        entity.setRecordedAt(recordedAt);
        entity.setDeviceIdentifier("INVOS-5100C-001");
        entity.setNotes("stable");

        // When
        final NirsReadingDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.site()).isEqualTo(NirsSite.LEFT_CEREBRAL);
        assertThat(dto.rso2Value()).isEqualTo(72.5);
        assertThat(dto.baseline()).isEqualTo(75.0);
        assertThat(dto.recordedAt()).isEqualTo(recordedAt);
        assertThat(dto.deviceIdentifier()).isEqualTo("INVOS-5100C-001");
        assertThat(dto.notes()).isEqualTo("stable");
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final CreateNirsReadingRequest request = new CreateNirsReadingRequest(
                patientId, NirsSite.RENAL, 65.0, 70.0,
                recordedAt, "NIRO-200NX", "renal monitoring");

        // When
        final NirsReading entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getSite()).isEqualTo(NirsSite.RENAL);
        assertThat(entity.getRso2Value()).isEqualTo(65.0);
        assertThat(entity.getBaseline()).isEqualTo(70.0);
        assertThat(entity.getRecordedAt()).isEqualTo(recordedAt);
        assertThat(entity.getDeviceIdentifier()).isEqualTo("NIRO-200NX");
        assertThat(entity.getNotes()).isEqualTo("renal monitoring");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final CreateNirsReadingRequest request = new CreateNirsReadingRequest(
                patientId, NirsSite.SOMATIC, 68.0, null, recordedAt, null, null);

        // When
        final NirsReading entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getBaseline()).isNull();
        assertThat(entity.getDeviceIdentifier()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
