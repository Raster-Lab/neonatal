package com.nicusystem.photo;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PatientPhoto} entity setters and getters.
 */
class PatientPhotoTest {

    @Test
    void setPatientId_shouldReturnSameValue() {
        // Given
        final PatientPhoto entity = new PatientPhoto();
        final UUID patientId = UUID.randomUUID();

        // When
        entity.setPatientId(patientId);

        // Then
        assertThat(entity.getPatientId()).isEqualTo(patientId);
    }

    @Test
    void setFileName_shouldReturnSameValue() {
        // Given
        final PatientPhoto entity = new PatientPhoto();

        // When
        entity.setFileName("photo.jpg");

        // Then
        assertThat(entity.getFileName()).isEqualTo("photo.jpg");
    }

    @Test
    void setContentType_shouldReturnSameValue() {
        // Given
        final PatientPhoto entity = new PatientPhoto();

        // When
        entity.setContentType("image/jpeg");

        // Then
        assertThat(entity.getContentType()).isEqualTo("image/jpeg");
    }

    @Test
    void setFileSize_shouldReturnSameValue() {
        // Given
        final PatientPhoto entity = new PatientPhoto();

        // When
        entity.setFileSize(1024L);

        // Then
        assertThat(entity.getFileSize()).isEqualTo(1024L);
    }

    @Test
    void setPhotoData_shouldReturnSameValue() {
        // Given
        final PatientPhoto entity = new PatientPhoto();
        final byte[] data = {1, 2, 3, 4, 5};

        // When
        entity.setPhotoData(data);

        // Then
        assertThat(entity.getPhotoData()).isEqualTo(data);
    }

    @Test
    void setDescription_shouldReturnSameValue() {
        // Given
        final PatientPhoto entity = new PatientPhoto();

        // When
        entity.setDescription("Front view");

        // Then
        assertThat(entity.getDescription()).isEqualTo("Front view");
    }

    @Test
    void setCapturedAt_shouldReturnSameValue() {
        // Given
        final PatientPhoto entity = new PatientPhoto();
        final Instant capturedAt = Instant.parse("2024-06-15T10:30:00Z");

        // When
        entity.setCapturedAt(capturedAt);

        // Then
        assertThat(entity.getCapturedAt()).isEqualTo(capturedAt);
    }

    @Test
    void setCapturedBy_shouldReturnSameValue() {
        // Given
        final PatientPhoto entity = new PatientPhoto();

        // When
        entity.setCapturedBy("Nurse Jane");

        // Then
        assertThat(entity.getCapturedBy()).isEqualTo("Nurse Jane");
    }

    @Test
    void defaultValues_shouldBeNull() {
        // Given / When
        final PatientPhoto entity = new PatientPhoto();

        // Then
        assertThat(entity.getPatientId()).isNull();
        assertThat(entity.getFileName()).isNull();
        assertThat(entity.getContentType()).isNull();
        assertThat(entity.getFileSize()).isNull();
        assertThat(entity.getPhotoData()).isNull();
        assertThat(entity.getDescription()).isNull();
        assertThat(entity.getCapturedAt()).isNull();
        assertThat(entity.getCapturedBy()).isNull();
    }
}
