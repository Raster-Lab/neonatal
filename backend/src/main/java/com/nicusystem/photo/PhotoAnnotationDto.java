package com.nicusystem.photo;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object representing a photo annotation.
 *
 * <p>Used to transfer annotation data from the service layer to callers,
 * including API responses.</p>
 */
public record PhotoAnnotationDto(
        UUID id,
        UUID photoId,
        AnnotationType annotationType,
        String content,
        Double xCoordinate,
        Double yCoordinate,
        Double width,
        Double height,
        String color,
        String annotatedBy,
        Instant createdAt,
        Instant updatedAt
) {}
