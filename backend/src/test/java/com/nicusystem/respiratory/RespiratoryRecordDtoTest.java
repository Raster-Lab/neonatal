package com.nicusystem.respiratory;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for RespiratoryRecordDto record.
 */
class RespiratoryRecordDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final Instant createdAt = Instant.now();
        final Instant updatedAt = Instant.now();

        // When
        final RespiratoryRecordDto dto = new RespiratoryRecordDto(
                id, patientId, RespiratorySupport.CPAP, 40.0, 5.0, 18.0,
                30, 0.4, 8.0, 6.0, recordedAt, "nurse1", "stable", createdAt, updatedAt);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.supportMode()).isEqualTo(RespiratorySupport.CPAP);
        assertThat(dto.fio2Percent()).isEqualTo(40.0);
        assertThat(dto.peep()).isEqualTo(5.0);
        assertThat(dto.pip()).isEqualTo(18.0);
        assertThat(dto.ratePerMin()).isEqualTo(30);
        assertThat(dto.tiSeconds()).isEqualTo(0.4);
        assertThat(dto.mapCmh2o()).isEqualTo(8.0);
        assertThat(dto.flowLpm()).isEqualTo(6.0);
        assertThat(dto.recordedAt()).isEqualTo(recordedAt);
        assertThat(dto.recordedBy()).isEqualTo("nurse1");
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
        final RespiratoryRecordDto dto = new RespiratoryRecordDto(
                id, patientId, RespiratorySupport.ROOM_AIR, null, null, null,
                null, null, null, null, recordedAt, null, null, null, null);

        // Then
        assertThat(dto.fio2Percent()).isNull();
        assertThat(dto.peep()).isNull();
        assertThat(dto.pip()).isNull();
        assertThat(dto.ratePerMin()).isNull();
        assertThat(dto.tiSeconds()).isNull();
        assertThat(dto.mapCmh2o()).isNull();
        assertThat(dto.flowLpm()).isNull();
        assertThat(dto.recordedBy()).isNull();
        assertThat(dto.notes()).isNull();
    }
}
