package com.nicusystem.aeeg;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateAeegRecordRequest record.
 */
class CreateAeegRecordRequestTest {

    @Test
    void shouldCreateRequestWithRequiredFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(3600);

        // When
        final CreateAeegRecordRequest request = new CreateAeegRecordRequest(
                patientId, AeegClassification.CONTINUOUS_NORMAL_VOLTAGE,
                25.0, 7.0, 18.0, false, null, startTime, endTime, null, null);

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.classification()).isEqualTo(AeegClassification.CONTINUOUS_NORMAL_VOLTAGE);
        assertThat(request.upperMarginAmplitude()).isEqualTo(25.0);
        assertThat(request.lowerMarginAmplitude()).isEqualTo(7.0);
        assertThat(request.bandwidth()).isEqualTo(18.0);
        assertThat(request.recordingStartTime()).isEqualTo(startTime);
        assertThat(request.recordingEndTime()).isEqualTo(endTime);
    }

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(3600);

        // When
        final CreateAeegRecordRequest request = new CreateAeegRecordRequest(
                patientId, AeegClassification.SEIZURE,
                40.0, 3.0, 37.0, true, 120,
                startTime, endTime, "BRM3-001", "seizure detected");

        // Then
        assertThat(request.seizureDetected()).isTrue();
        assertThat(request.seizureDurationSeconds()).isEqualTo(120);
        assertThat(request.deviceIdentifier()).isEqualTo("BRM3-001");
        assertThat(request.notes()).isEqualTo("seizure detected");
    }
}
