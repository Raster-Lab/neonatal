package com.nicusystem.respiratory;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateRespiratoryRecordRequest record.
 */
class CreateRespiratoryRecordRequestTest {

    @Test
    void shouldCreateRequestWithRequiredFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();

        // When
        final CreateRespiratoryRecordRequest request = new CreateRespiratoryRecordRequest(
                patientId, RespiratorySupport.CPAP, null, null, null,
                null, null, null, null, recordedAt, null, null);

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.supportMode()).isEqualTo(RespiratorySupport.CPAP);
        assertThat(request.recordedAt()).isEqualTo(recordedAt);
    }

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();

        // When
        final CreateRespiratoryRecordRequest request = new CreateRespiratoryRecordRequest(
                patientId, RespiratorySupport.CONVENTIONAL_VENTILATION, 60.0, 5.0, 22.0,
                40, 0.35, 10.0, 8.0, recordedAt, "dr1", "stable ventilation");

        // Then
        assertThat(request.fio2Percent()).isEqualTo(60.0);
        assertThat(request.peep()).isEqualTo(5.0);
        assertThat(request.pip()).isEqualTo(22.0);
        assertThat(request.ratePerMin()).isEqualTo(40);
        assertThat(request.tiSeconds()).isEqualTo(0.35);
        assertThat(request.mapCmh2o()).isEqualTo(10.0);
        assertThat(request.flowLpm()).isEqualTo(8.0);
        assertThat(request.recordedBy()).isEqualTo("dr1");
        assertThat(request.notes()).isEqualTo("stable ventilation");
    }
}
