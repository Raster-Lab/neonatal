package com.nicusystem.photo;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PatientPhotoMapper}.
 */
class PatientPhotoMapperTest {

    private final PatientPhotoMapper mapper = new PatientPhotoMapper();

    @Test
    void toDto_shouldMapAllFieldsExceptPhotoData() {
        // Given
        final PatientPhoto entity = new PatientPhoto();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();
        entity.setPatientId(patientId);
        entity.setFileName("photo.jpg");
        entity.setContentType("image/jpeg");
        entity.setFileSize(2048L);
        entity.setPhotoData(new byte[]{1, 2, 3});
        entity.setDescription("Front view");
        entity.setCapturedAt(now);
        entity.setCapturedBy("Nurse Jane");

        // When
        final PatientPhotoDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.patientId()).isEqualTo(patientId);
        assertThat(dto.fileName()).isEqualTo("photo.jpg");
        assertThat(dto.contentType()).isEqualTo("image/jpeg");
        assertThat(dto.fileSize()).isEqualTo(2048L);
        assertThat(dto.description()).isEqualTo("Front view");
        assertThat(dto.capturedAt()).isEqualTo(now);
        assertThat(dto.capturedBy()).isEqualTo("Nurse Jane");
    }

    @Test
    void toDto_withNullOptionalFields_shouldMapNulls() {
        // Given
        final PatientPhoto entity = new PatientPhoto();
        final UUID patientId = UUID.randomUUID();
        final Instant now = Instant.now();
        entity.setPatientId(patientId);
        entity.setFileName("photo.png");
        entity.setContentType("image/png");
        entity.setFileSize(1024L);
        entity.setCapturedAt(now);

        // When
        final PatientPhotoDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto.description()).isNull();
        assertThat(dto.capturedBy()).isNull();
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant capturedAt = Instant.parse("2024-06-15T10:30:00Z");
        final byte[] data = {10, 20, 30};
        final CreatePatientPhotoRequest request = new CreatePatientPhotoRequest(
                patientId, "photo.jpg", "image/jpeg", 3L,
                data, "Front view", capturedAt, "Nurse Jane");

        // When
        final PatientPhoto entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
        assertThat(entity.getFileName()).isEqualTo("photo.jpg");
        assertThat(entity.getContentType()).isEqualTo("image/jpeg");
        assertThat(entity.getFileSize()).isEqualTo(3L);
        assertThat(entity.getPhotoData()).isEqualTo(data);
        assertThat(entity.getDescription()).isEqualTo("Front view");
        assertThat(entity.getCapturedAt()).isEqualTo(capturedAt);
        assertThat(entity.getCapturedBy()).isEqualTo("Nurse Jane");
    }

    @Test
    void toEntity_withNullOptionalFields_shouldMapNulls() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant capturedAt = Instant.now();
        final byte[] data = {1};
        final CreatePatientPhotoRequest request = new CreatePatientPhotoRequest(
                patientId, "photo.png", "image/png", 1L,
                data, null, capturedAt, null);

        // When
        final PatientPhoto entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getDescription()).isNull();
        assertThat(entity.getCapturedBy()).isNull();
    }
}
