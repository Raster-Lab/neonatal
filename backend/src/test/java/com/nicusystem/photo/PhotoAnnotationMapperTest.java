package com.nicusystem.photo;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PhotoAnnotationMapper}.
 */
class PhotoAnnotationMapperTest {

    private final PhotoAnnotationMapper mapper = new PhotoAnnotationMapper();

    @Test
    void toDto_shouldMapAllFields() {
        // Given
        final PhotoAnnotation entity = new PhotoAnnotation();
        final UUID id = UUID.randomUUID();
        final UUID photoId = UUID.randomUUID();
        entity.setId(id);
        entity.setPhotoId(photoId);
        entity.setAnnotationType(AnnotationType.TEXT);
        entity.setContent("Rash observed");
        entity.setXCoordinate(100.0);
        entity.setYCoordinate(200.0);
        entity.setWidth(50.0);
        entity.setHeight(30.0);
        entity.setColor("#FF0000");
        entity.setAnnotatedBy("Dr. Smith");

        // When
        final PhotoAnnotationDto dto = mapper.toDto(entity);

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
    }

    @Test
    void toEntity_shouldMapAllFields() {
        // Given
        final UUID photoId = UUID.randomUUID();
        final CreatePhotoAnnotationRequest request = new CreatePhotoAnnotationRequest(
                photoId, AnnotationType.RECTANGLE, "Wound area",
                50.0, 75.0, 120.0, 80.0, "#00FF00", "Nurse Jane");

        // When
        final PhotoAnnotation entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getPhotoId()).isEqualTo(photoId);
        assertThat(entity.getAnnotationType()).isEqualTo(AnnotationType.RECTANGLE);
        assertThat(entity.getContent()).isEqualTo("Wound area");
        assertThat(entity.getXCoordinate()).isEqualTo(50.0);
        assertThat(entity.getYCoordinate()).isEqualTo(75.0);
        assertThat(entity.getWidth()).isEqualTo(120.0);
        assertThat(entity.getHeight()).isEqualTo(80.0);
        assertThat(entity.getColor()).isEqualTo("#00FF00");
        assertThat(entity.getAnnotatedBy()).isEqualTo("Nurse Jane");
    }

    @Test
    void toEntity_shouldHandleNullOptionalFields() {
        // Given
        final UUID photoId = UUID.randomUUID();
        final CreatePhotoAnnotationRequest request = new CreatePhotoAnnotationRequest(
                photoId, AnnotationType.ARROW, null,
                10.0, 20.0, null, null, null, "Dr. Smith");

        // When
        final PhotoAnnotation entity = mapper.toEntity(request);

        // Then
        assertThat(entity.getContent()).isNull();
        assertThat(entity.getWidth()).isNull();
        assertThat(entity.getHeight()).isNull();
        assertThat(entity.getColor()).isNull();
    }
}
