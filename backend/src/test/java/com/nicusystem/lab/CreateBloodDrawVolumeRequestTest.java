package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateBloodDrawVolumeRequest record.
 */
class CreateBloodDrawVolumeRequestTest {

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final UUID labOrderId = UUID.randomUUID();
        final Instant now = Instant.now();

        // When
        final CreateBloodDrawVolumeRequest request = new CreateBloodDrawVolumeRequest(
                patientId, labOrderId, now, 300.0, "Nurse Jones", "difficult draw");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.labOrderId()).isEqualTo(labOrderId);
        assertThat(request.drawnAt()).isEqualTo(now);
        assertThat(request.volumeUl()).isEqualTo(300.0);
        assertThat(request.drawnBy()).isEqualTo("Nurse Jones");
        assertThat(request.notes()).isEqualTo("difficult draw");
    }

    @Test
    void shouldCreateRequestWithNullOptionalFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();

        // When
        final CreateBloodDrawVolumeRequest request = new CreateBloodDrawVolumeRequest(
                patientId, null, now, 100.0, null, null);

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.labOrderId()).isNull();
        assertThat(request.drawnAt()).isEqualTo(now);
        assertThat(request.volumeUl()).isEqualTo(100.0);
        assertThat(request.drawnBy()).isNull();
        assertThat(request.notes()).isNull();
    }
}
