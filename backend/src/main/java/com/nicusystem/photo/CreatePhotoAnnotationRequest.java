package com.nicusystem.photo;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request record for creating a new photo annotation.
 */
public record CreatePhotoAnnotationRequest(
        @NotNull UUID photoId,
        @NotNull AnnotationType annotationType,
        String content,
        @NotNull Double xCoordinate,
        @NotNull Double yCoordinate,
        Double width,
        Double height,
        String color,
        @NotNull String annotatedBy
) {}
