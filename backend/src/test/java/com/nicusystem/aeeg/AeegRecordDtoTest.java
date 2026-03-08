package com.nicusystem.aeeg;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for AeegRecordDto record.
 */
class AeegRecordDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(3600);
        final Instant createdAt = Instant.now();
        final Instant updatedAt = Instant.now();

        // When
        final AeegRecordDto dto = new AeegRecordDto(
                id, patientId, AeegClassification.CONTINUOUS_NORMAL_VOLTAGE,
                25.0, 7.0, 18.0, false, null,
                startTime, endTime, "BRM3-001", "normal", createdAt, updatedAt);

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
        assertThat(dto.notes()).isEqualTo("normal");
        assertThat(dto.createdAt()).isEqualTo(createdAt);
        assertThat(dto.updatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(3600);

        // When
        final AeegRecordDto dto = new AeegRecordDto(
                id, patientId, AeegClassification.SEIZURE,
                40.0, 3.0, 37.0, true, 120,
                startTime, endTime, null, null, null, null);

        // Then
        assertThat(dto.seizureDetected()).isTrue();
        assertThat(dto.seizureDurationSeconds()).isEqualTo(120);
        assertThat(dto.deviceIdentifier()).isNull();
        assertThat(dto.notes()).isNull();
        assertThat(dto.createdAt()).isNull();
        assertThat(dto.updatedAt()).isNull();
    }
}
