package com.nicusystem.respiratory;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for RespiratoryRecordMapper.
 */
class RespiratoryRecordMapperTest {

    private final RespiratoryRecordMapper mapper = new RespiratoryRecordMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final RespiratoryRecord entity = new RespiratoryRecord();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setSupportMode(RespiratorySupport.CONVENTIONAL_VENTILATION);
        entity.setFio2Percent(60.0);
        entity.setPeep(5.0);
        entity.setPip(22.0);
        entity.setRatePerMin(40);
        entity.setTiSeconds(0.35);
        entity.setMapCmh2o(10.0);
        entity.setFlowLpm(8.0);
        entity.setRecordedAt(recordedAt);
        entity.setRecordedBy("nurse1");
        entity.setNotes("routine check");

        // When
        final RespiratoryRecordDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.supportMode()).isEqualTo(RespiratorySupport.CONVENTIONAL_VENTILATION);
        assertThat(dto.fio2Percent()).isEqualTo(60.0);
        assertThat(dto.peep()).isEqualTo(5.0);
        assertThat(dto.pip()).isEqualTo(22.0);
        assertThat(dto.ratePerMin()).isEqualTo(40);
        assertThat(dto.tiSeconds()).isEqualTo(0.35);
        assertThat(dto.mapCmh2o()).isEqualTo(10.0);
        assertThat(dto.flowLpm()).isEqualTo(8.0);
        assertThat(dto.recordedAt()).isEqualTo(recordedAt);
        assertThat(dto.recordedBy()).isEqualTo("nurse1");
        assertThat(dto.notes()).isEqualTo("routine check");
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final CreateRespiratoryRecordRequest request = new CreateRespiratoryRecordRequest(
                patientId, RespiratorySupport.CPAP, 40.0, 5.0, null,
                null, null, 8.0, null, recordedAt, "nurse2", "CPAP trial");

        // When
        final RespiratoryRecord entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getSupportMode()).isEqualTo(RespiratorySupport.CPAP);
        assertThat(entity.getFio2Percent()).isEqualTo(40.0);
        assertThat(entity.getPeep()).isEqualTo(5.0);
        assertThat(entity.getPip()).isNull();
        assertThat(entity.getRatePerMin()).isNull();
        assertThat(entity.getTiSeconds()).isNull();
        assertThat(entity.getMapCmh2o()).isEqualTo(8.0);
        assertThat(entity.getFlowLpm()).isNull();
        assertThat(entity.getRecordedAt()).isEqualTo(recordedAt);
        assertThat(entity.getRecordedBy()).isEqualTo("nurse2");
        assertThat(entity.getNotes()).isEqualTo("CPAP trial");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final CreateRespiratoryRecordRequest request = new CreateRespiratoryRecordRequest(
                patientId, RespiratorySupport.ROOM_AIR, null, null, null,
                null, null, null, null, recordedAt, null, null);

        // When
        final RespiratoryRecord entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getFio2Percent()).isNull();
        assertThat(entity.getPeep()).isNull();
        assertThat(entity.getPip()).isNull();
        assertThat(entity.getRatePerMin()).isNull();
        assertThat(entity.getTiSeconds()).isNull();
        assertThat(entity.getMapCmh2o()).isNull();
        assertThat(entity.getFlowLpm()).isNull();
        assertThat(entity.getRecordedBy()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
