package com.nicusystem.photo;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CreatePhotoAnnotationRequest} record.
 */
class CreatePhotoAnnotationRequestTest {

    @Test
    void shouldCreateRequestWithRequiredFields() {
        // Given
        final UUID photoId = UUID.randomUUID();

        // When
        final CreatePhotoAnnotationRequest request = new CreatePhotoAnnotationRequest(
                photoId, AnnotationType.TEXT, null, 100.0, 200.0,
                null, null, null, "Dr. Smith");

        // Then
        assertThat(request.photoId()).isEqualTo(photoId);
        assertThat(request.annotationType()).isEqualTo(AnnotationType.TEXT);
        assertThat(request.xCoordinate()).isEqualTo(100.0);
        assertThat(request.yCoordinate()).isEqualTo(200.0);
        assertThat(request.annotatedBy()).isEqualTo("Dr. Smith");
    }

    @Test
    void shouldCreateRequestWithAllFields() {
        // Given
        final UUID photoId = UUID.randomUUID();

        // When
        final CreatePhotoAnnotationRequest request = new CreatePhotoAnnotationRequest(
                photoId, AnnotationType.RECTANGLE, "Wound area",
                50.0, 75.0, 120.0, 80.0, "#FF0000", "Nurse Jane");

        // Then
        assertThat(request.content()).isEqualTo("Wound area");
        assertThat(request.width()).isEqualTo(120.0);
        assertThat(request.height()).isEqualTo(80.0);
        assertThat(request.color()).isEqualTo("#FF0000");
    }

    @Test
    void equality_sameRequests_shouldBeEqual() {
        // Given
        final UUID photoId = UUID.randomUUID();

        // When
        final CreatePhotoAnnotationRequest req1 = new CreatePhotoAnnotationRequest(
                photoId, AnnotationType.CIRCLE, "mark",
                10.0, 20.0, 30.0, 40.0, "#00FF00", "nurse");
        final CreatePhotoAnnotationRequest req2 = new CreatePhotoAnnotationRequest(
                photoId, AnnotationType.CIRCLE, "mark",
                10.0, 20.0, 30.0, 40.0, "#00FF00", "nurse");

        // Then
        assertThat(req1).isEqualTo(req2);
        assertThat(req1.hashCode()).isEqualTo(req2.hashCode());
    }
}
