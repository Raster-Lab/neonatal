package com.nicusystem.photo;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request record for creating a new patient photo.
 *
 * <p>Contains all required and optional fields needed to store a photo
 * for a neonatal patient. Fields annotated with {@link NotNull} are mandatory.</p>
 *
 * @param patientId   UUID of the patient (required)
 * @param fileName    original filename (required)
 * @param contentType MIME type of the image (required)
 * @param fileSize    size in bytes (required)
 * @param photoData   binary image data (required)
 * @param description optional caption or description
 * @param capturedAt  when the photo was taken (required)
 * @param capturedBy  who took the photo
 */
public record CreatePatientPhotoRequest(
        @NotNull UUID patientId,
        @NotNull String fileName,
        @NotNull String contentType,
        @NotNull Long fileSize,
        @NotNull byte[] photoData,
        String description,
        @NotNull Instant capturedAt,
        String capturedBy
) {}
