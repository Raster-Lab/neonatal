package com.nicusystem.photo;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CreatePatientPhotoRequest} record.
 */
class CreatePatientPhotoRequestTest {

    @Test
    void constructor_withAllFields_shouldReturnCorrectValues() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant capturedAt = Instant.parse("2024-06-15T10:30:00Z");
        final byte[] data = {1, 2, 3};

        // When
        final CreatePatientPhotoRequest request = new CreatePatientPhotoRequest(
                patientId, "photo.jpg", "image/jpeg", 3L,
                data, "Front view", capturedAt, "Nurse Jane");

        // Then
        assertThat(request.patientId()).isEqualTo(patientId);
        assertThat(request.fileName()).isEqualTo("photo.jpg");
        assertThat(request.contentType()).isEqualTo("image/jpeg");
        assertThat(request.fileSize()).isEqualTo(3L);
        assertThat(request.photoData()).isEqualTo(data);
        assertThat(request.description()).isEqualTo("Front view");
        assertThat(request.capturedAt()).isEqualTo(capturedAt);
        assertThat(request.capturedBy()).isEqualTo("Nurse Jane");
    }

    @Test
    void constructor_withNullOptionalFields_shouldAllowNulls() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant capturedAt = Instant.now();
        final byte[] data = {10, 20};

        // When
        final CreatePatientPhotoRequest request = new CreatePatientPhotoRequest(
                patientId, "photo.png", "image/png", 2L,
                data, null, capturedAt, null);

        // Then
        assertThat(request.description()).isNull();
        assertThat(request.capturedBy()).isNull();
    }

    @Test
    void equality_sameRequests_shouldBeEqual() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant capturedAt = Instant.parse("2024-06-15T10:30:00Z");
        final byte[] data = {5, 6, 7};

        // When
        final CreatePatientPhotoRequest req1 = new CreatePatientPhotoRequest(
                patientId, "photo.jpg", "image/jpeg", 3L,
                data, "desc", capturedAt, "nurse");
        final CreatePatientPhotoRequest req2 = new CreatePatientPhotoRequest(
                patientId, "photo.jpg", "image/jpeg", 3L,
                data, "desc", capturedAt, "nurse");

        // Then
        assertThat(req1).isEqualTo(req2);
        assertThat(req1.hashCode()).isEqualTo(req2.hashCode());
    }
}
