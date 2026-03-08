package com.nicusystem.nirs;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for NirsReadingDto record.
 */
class NirsReadingDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final Instant createdAt = Instant.now();
        final Instant updatedAt = Instant.now();

        // When
        final NirsReadingDto dto = new NirsReadingDto(
                id, patientId, NirsSite.LEFT_CEREBRAL, 72.5, 75.0,
                recordedAt, "INVOS-5100C-001", "stable", createdAt, updatedAt);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.site()).isEqualTo(NirsSite.LEFT_CEREBRAL);
        assertThat(dto.rso2Value()).isEqualTo(72.5);
        assertThat(dto.baseline()).isEqualTo(75.0);
        assertThat(dto.recordedAt()).isEqualTo(recordedAt);
        assertThat(dto.deviceIdentifier()).isEqualTo("INVOS-5100C-001");
        assertThat(dto.notes()).isEqualTo("stable");
        assertThat(dto.createdAt()).isEqualTo(createdAt);
        assertThat(dto.updatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();

        // When
        final NirsReadingDto dto = new NirsReadingDto(
                id, patientId, NirsSite.SOMATIC, 68.0, null,
                recordedAt, null, null, null, null);

        // Then
        assertThat(dto.baseline()).isNull();
        assertThat(dto.deviceIdentifier()).isNull();
        assertThat(dto.notes()).isNull();
        assertThat(dto.createdAt()).isNull();
        assertThat(dto.updatedAt()).isNull();
    }
}
