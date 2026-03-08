package com.nicusystem.photo;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object representing a patient photo.
 *
 * <p>Used to transfer photo metadata from the service layer to callers,
 * including API responses. The binary photo data is intentionally excluded
 * to keep JSON payloads small; use the dedicated data endpoint to retrieve
 * the actual image bytes.</p>
 *
 * @param id          unique identifier of the photo
 * @param patientId   UUID of the patient
 * @param fileName    original filename
 * @param contentType MIME type of the image
 * @param fileSize    size in bytes
 * @param description optional caption or description
 * @param capturedAt  when the photo was taken
 * @param capturedBy  who took the photo
 * @param createdAt   audit timestamp of creation
 * @param updatedAt   audit timestamp of last update
 */
public record PatientPhotoDto(
        UUID id,
        UUID patientId,
        String fileName,
        String contentType,
        Long fileSize,
        String description,
        Instant capturedAt,
        String capturedBy,
        Instant createdAt,
        Instant updatedAt
) {}
