package com.nicusystem.photo;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PhotoAnnotationDto} record.
 */
class PhotoAnnotationDtoTest {

    @Test
    void shouldCreateDtoWithAllFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID photoId = UUID.randomUUID();
        final Instant createdAt = Instant.now();
        final Instant updatedAt = Instant.now();

        // When
        final PhotoAnnotationDto dto = new PhotoAnnotationDto(
                id, photoId, AnnotationType.TEXT, "Rash observed",
                100.0, 200.0, 50.0, 30.0, "#FF0000", "Dr. Smith",
                createdAt, updatedAt);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.photoId()).isEqualTo(photoId);
        assertThat(dto.annotationType()).isEqualTo(AnnotationType.TEXT);
        assertThat(dto.content()).isEqualTo("Rash observed");
        assertThat(dto.xCoordinate()).isEqualTo(100.0);
        assertThat(dto.yCoordinate()).isEqualTo(200.0);
        assertThat(dto.width()).isEqualTo(50.0);
        assertThat(dto.height()).isEqualTo(30.0);
        assertThat(dto.color()).isEqualTo("#FF0000");
        assertThat(dto.annotatedBy()).isEqualTo("Dr. Smith");
        assertThat(dto.createdAt()).isEqualTo(createdAt);
        assertThat(dto.updatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void shouldCreateDtoWithNullOptionalFields() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID photoId = UUID.randomUUID();

        // When
        final PhotoAnnotationDto dto = new PhotoAnnotationDto(
                id, photoId, AnnotationType.ARROW, null,
                50.0, 75.0, null, null, null, "Nurse Jane",
                null, null);

        // Then
        assertThat(dto.content()).isNull();
        assertThat(dto.width()).isNull();
        assertThat(dto.height()).isNull();
        assertThat(dto.color()).isNull();
        assertThat(dto.createdAt()).isNull();
        assertThat(dto.updatedAt()).isNull();
    }

    @Test
    void equality_sameDtos_shouldBeEqual() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID photoId = UUID.randomUUID();
        final Instant now = Instant.parse("2024-06-15T10:30:00Z");

        // When
        final PhotoAnnotationDto dto1 = new PhotoAnnotationDto(
                id, photoId, AnnotationType.CIRCLE, "mark",
                10.0, 20.0, 30.0, 40.0, "#00FF00", "nurse",
                now, now);
        final PhotoAnnotationDto dto2 = new PhotoAnnotationDto(
                id, photoId, AnnotationType.CIRCLE, "mark",
                10.0, 20.0, 30.0, 40.0, "#00FF00", "nurse",
                now, now);

        // Then
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }
}
