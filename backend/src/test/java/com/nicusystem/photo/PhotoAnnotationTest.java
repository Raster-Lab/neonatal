package com.nicusystem.photo;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PhotoAnnotation} entity setters and getters.
 */
class PhotoAnnotationTest {

    @Test
    void shouldSetAndGetPhotoId() {
        // Given
        final PhotoAnnotation entity = new PhotoAnnotation();
        final UUID photoId = UUID.randomUUID();

        // When
        entity.setPhotoId(photoId);

        // Then
        assertThat(entity.getPhotoId()).isEqualTo(photoId);
    }

    @Test
    void shouldSetAndGetAnnotationType() {
        // Given
        final PhotoAnnotation entity = new PhotoAnnotation();

        // When
        entity.setAnnotationType(AnnotationType.ARROW);

        // Then
        assertThat(entity.getAnnotationType()).isEqualTo(AnnotationType.ARROW);
    }

    @Test
    void shouldSetAndGetContent() {
        // Given
        final PhotoAnnotation entity = new PhotoAnnotation();

        // When
        entity.setContent("Rash observed");

        // Then
        assertThat(entity.getContent()).isEqualTo("Rash observed");
    }

    @Test
    void shouldSetAndGetXCoordinate() {
        // Given
        final PhotoAnnotation entity = new PhotoAnnotation();

        // When
        entity.setXCoordinate(150.5);

        // Then
        assertThat(entity.getXCoordinate()).isEqualTo(150.5);
    }

    @Test
    void shouldSetAndGetYCoordinate() {
        // Given
        final PhotoAnnotation entity = new PhotoAnnotation();

        // When
        entity.setYCoordinate(200.0);

        // Then
        assertThat(entity.getYCoordinate()).isEqualTo(200.0);
    }

    @Test
    void shouldSetAndGetWidth() {
        // Given
        final PhotoAnnotation entity = new PhotoAnnotation();

        // When
        entity.setWidth(50.0);

        // Then
        assertThat(entity.getWidth()).isEqualTo(50.0);
    }

    @Test
    void shouldSetAndGetHeight() {
        // Given
        final PhotoAnnotation entity = new PhotoAnnotation();

        // When
        entity.setHeight(30.0);

        // Then
        assertThat(entity.getHeight()).isEqualTo(30.0);
    }

    @Test
    void shouldSetAndGetColor() {
        // Given
        final PhotoAnnotation entity = new PhotoAnnotation();

        // When
        entity.setColor("#FF0000");

        // Then
        assertThat(entity.getColor()).isEqualTo("#FF0000");
    }

    @Test
    void shouldSetAndGetAnnotatedBy() {
        // Given
        final PhotoAnnotation entity = new PhotoAnnotation();

        // When
        entity.setAnnotatedBy("Dr. Smith");

        // Then
        assertThat(entity.getAnnotatedBy()).isEqualTo("Dr. Smith");
    }

    @Test
    void defaultValues_shouldBeNull() {
        // Given / When
        final PhotoAnnotation entity = new PhotoAnnotation();

        // Then
        assertThat(entity.getPhotoId()).isNull();
        assertThat(entity.getAnnotationType()).isNull();
        assertThat(entity.getContent()).isNull();
        assertThat(entity.getXCoordinate()).isNull();
        assertThat(entity.getYCoordinate()).isNull();
        assertThat(entity.getWidth()).isNull();
        assertThat(entity.getHeight()).isNull();
        assertThat(entity.getColor()).isNull();
        assertThat(entity.getAnnotatedBy()).isNull();
    }
}
