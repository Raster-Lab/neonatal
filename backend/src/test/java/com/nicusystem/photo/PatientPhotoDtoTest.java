package com.nicusystem.photo;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PatientPhotoDto} record.
 */
class PatientPhotoDtoTest {

    @Test
    void constructor_withAllFields_shouldReturnCorrectValues() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();

        // When
        final PatientPhotoDto dto = new PatientPhotoDto(
                id, patientId, "photo.jpg", "image/jpeg", 2048L,
                "Front view", now, "Nurse Jane", now, now);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.fileName()).isEqualTo("photo.jpg");
        assertThat(dto.contentType()).isEqualTo("image/jpeg");
        assertThat(dto.fileSize()).isEqualTo(2048L);
        assertThat(dto.description()).isEqualTo("Front view");
        assertThat(dto.capturedAt()).isEqualTo(now);
        assertThat(dto.capturedBy()).isEqualTo("Nurse Jane");
        assertThat(dto.createdAt()).isEqualTo(now);
        assertThat(dto.updatedAt()).isEqualTo(now);
    }

    @Test
    void constructor_withNullOptionalFields_shouldAllowNulls() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();

        // When
        final PatientPhotoDto dto = new PatientPhotoDto(
                id, patientId, "photo.png", "image/png", 1024L,
                null, now, null, null, null);

        // Then
        assertThat(dto.description()).isNull();
        assertThat(dto.capturedBy()).isNull();
        assertThat(dto.createdAt()).isNull();
        assertThat(dto.updatedAt()).isNull();
    }

    @Test
    void equality_sameDtos_shouldBeEqual() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.parse("2024-06-15T10:30:00Z");

        // When
        final PatientPhotoDto dto1 = new PatientPhotoDto(
                id, patientId, "photo.jpg", "image/jpeg", 2048L,
                "desc", now, "nurse", now, now);
        final PatientPhotoDto dto2 = new PatientPhotoDto(
                id, patientId, "photo.jpg", "image/jpeg", 2048L,
                "desc", now, "nurse", now, now);

        // Then
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }
}
