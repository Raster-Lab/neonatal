package com.nicusystem.aeeg;

import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between {@link AeegRecord} entities and DTOs.
 */
@Component
public class AeegRecordMapper {

    /**
     * Converts an {@link AeegRecord} entity to an {@link AeegRecordDto}.
     *
     * @param entity the aEEG record entity
     * @return the corresponding DTO
     */
    public AeegRecordDto toDto(final AeegRecord entity) {
        return new AeegRecordDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getClassification(),
                entity.getUpperMarginAmplitude(),
                entity.getLowerMarginAmplitude(),
                entity.getBandwidth(),
                entity.isSeizureDetected(),
                entity.getSeizureDurationSeconds(),
                entity.getRecordingStartTime(),
                entity.getRecordingEndTime(),
                entity.getDeviceIdentifier(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a {@link CreateAeegRecordRequest} to a new {@link AeegRecord} entity.
     *
     * @param request the creation request
     * @return a new, unpersisted aEEG record entity
     */
    public AeegRecord toEntity(final CreateAeegRecordRequest request) {
        final AeegRecord entity = new AeegRecord();
        entity.setPatientId(request.patientId());
        entity.setClassification(request.classification());
        entity.setUpperMarginAmplitude(request.upperMarginAmplitude());
        entity.setLowerMarginAmplitude(request.lowerMarginAmplitude());
        entity.setBandwidth(request.bandwidth());
        entity.setSeizureDetected(request.seizureDetected());
        entity.setSeizureDurationSeconds(request.seizureDurationSeconds());
        entity.setRecordingStartTime(request.recordingStartTime());
        entity.setRecordingEndTime(request.recordingEndTime());
        entity.setDeviceIdentifier(request.deviceIdentifier());
        entity.setNotes(request.notes());
        return entity;
    }
}
