package com.nicusystem.nirs;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for CreateNirsReadingRequest record.
 */
class CreateNirsReadingRequestTest {

    @Test
    void shouldCreateRequestWithRequiredFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();

        // When
        final CreateNirsReadingRequest request = new CreateNirsReadingRequest(
                patientId, NirsSite.LEFT_CEREBRAL, 72.5, null, recordedAt, null, null);

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.site()).isEqualTo(NirsSite.LEFT_CEREBRAL);
        assertThat(request.rso2Value()).isEqualTo(72.5);
        assertThat(request.recordedAt()).isEqualTo(recordedAt);
    }

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();

        // When
        final CreateNirsReadingRequest request = new CreateNirsReadingRequest(
                patientId, NirsSite.RIGHT_CEREBRAL, 70.0, 75.0,
                recordedAt, "INVOS-5100C-001", "routine monitoring");

        // Then
        assertThat(request.baseline()).isEqualTo(75.0);
        assertThat(request.deviceIdentifier()).isEqualTo("INVOS-5100C-001");
        assertThat(request.notes()).isEqualTo("routine monitoring");
    }
}
