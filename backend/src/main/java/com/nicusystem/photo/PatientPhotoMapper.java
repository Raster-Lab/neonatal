package com.nicusystem.photo;

import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between {@link PatientPhoto} entities and DTOs.
 */
@Component
public class PatientPhotoMapper {

    /**
     * Converts a {@link PatientPhoto} entity to a {@link PatientPhotoDto}.
     *
     * <p>The binary photo data is intentionally excluded from the DTO.</p>
     *
     * @param entity the patient photo entity
     * @return the corresponding DTO
     */
    public PatientPhotoDto toDto(final PatientPhoto entity) {
        return new PatientPhotoDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getFileName(),
                entity.getContentType(),
                entity.getFileSize(),
                entity.getDescription(),
                entity.getCapturedAt(),
                entity.getCapturedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a {@link CreatePatientPhotoRequest} to a new {@link PatientPhoto} entity.
     *
     * @param request the creation request
     * @return a new, unpersisted patient photo entity
     */
    public PatientPhoto toEntity(final CreatePatientPhotoRequest request) {
        final PatientPhoto entity = new PatientPhoto();
        entity.setPatientId(request.patientId());
        entity.setFileName(request.fileName());
        entity.setContentType(request.contentType());
        entity.setFileSize(request.fileSize());
        entity.setPhotoData(request.photoData());
        entity.setDescription(request.description());
        entity.setCapturedAt(request.capturedAt());
        entity.setCapturedBy(request.capturedBy());
        return entity;
    }
}
