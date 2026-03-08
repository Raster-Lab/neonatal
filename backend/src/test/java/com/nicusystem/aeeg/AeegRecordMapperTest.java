package com.nicusystem.aeeg;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for AeegRecordMapper.
 */
class AeegRecordMapperTest {

    private final AeegRecordMapper mapper = new AeegRecordMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final AeegRecord entity = new AeegRecord();
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(3600);
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setClassification(AeegClassification.CONTINUOUS_NORMAL_VOLTAGE);
        entity.setUpperMarginAmplitude(25.0);
        entity.setLowerMarginAmplitude(7.0);
        entity.setBandwidth(18.0);
        entity.setSeizureDetected(false);
        entity.setSeizureDurationSeconds(null);
        entity.setRecordingStartTime(startTime);
        entity.setRecordingEndTime(endTime);
        entity.setDeviceIdentifier("BRM3-001");
        entity.setNotes("normal trace");

        // When
        final AeegRecordDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.classification()).isEqualTo(AeegClassification.CONTINUOUS_NORMAL_VOLTAGE);
        assertThat(dto.upperMarginAmplitude()).isEqualTo(25.0);
        assertThat(dto.lowerMarginAmplitude()).isEqualTo(7.0);
        assertThat(dto.bandwidth()).isEqualTo(18.0);
        assertThat(dto.seizureDetected()).isFalse();
        assertThat(dto.seizureDurationSeconds()).isNull();
        assertThat(dto.recordingStartTime()).isEqualTo(startTime);
        assertThat(dto.recordingEndTime()).isEqualTo(endTime);
        assertThat(dto.deviceIdentifier()).isEqualTo("BRM3-001");
        assertThat(dto.notes()).isEqualTo("normal trace");
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(3600);
        final CreateAeegRecordRequest request = new CreateAeegRecordRequest(
                patientId, AeegClassification.SEIZURE,
                40.0, 3.0, 37.0, true, 120,
                startTime, endTime, "BRM3-001", "seizure event");

        // When
        final AeegRecord entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getClassification()).isEqualTo(AeegClassification.SEIZURE);
        assertThat(entity.getUpperMarginAmplitude()).isEqualTo(40.0);
        assertThat(entity.getLowerMarginAmplitude()).isEqualTo(3.0);
        assertThat(entity.getBandwidth()).isEqualTo(37.0);
        assertThat(entity.isSeizureDetected()).isTrue();
        assertThat(entity.getSeizureDurationSeconds()).isEqualTo(120);
        assertThat(entity.getRecordingStartTime()).isEqualTo(startTime);
        assertThat(entity.getRecordingEndTime()).isEqualTo(endTime);
        assertThat(entity.getDeviceIdentifier()).isEqualTo("BRM3-001");
        assertThat(entity.getNotes()).isEqualTo("seizure event");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(3600);
        final CreateAeegRecordRequest request = new CreateAeegRecordRequest(
                patientId, AeegClassification.DISCONTINUOUS,
                15.0, 5.0, 10.0, false, null,
                startTime, endTime, null, null);

        // When
        final AeegRecord entity = mapper.toEntity(request);

        // Then
        assertThat(entity.isSeizureDetected()).isFalse();
        assertThat(entity.getSeizureDurationSeconds()).isNull();
        assertThat(entity.getDeviceIdentifier()).isNull();
        assertThat(entity.getNotes()).isNull();
    }
}
