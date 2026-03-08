package com.nicusystem.nirs;

import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between {@link NirsReading} entities and DTOs.
 */
@Component
public class NirsReadingMapper {

    /**
     * Converts a {@link NirsReading} entity to a {@link NirsReadingDto}.
     *
     * @param entity the NIRS reading entity
     * @return the corresponding DTO
     */
    public NirsReadingDto toDto(final NirsReading entity) {
        return new NirsReadingDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getSite(),
                entity.getRso2Value(),
                entity.getBaseline(),
                entity.getRecordedAt(),
                entity.getDeviceIdentifier(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a {@link CreateNirsReadingRequest} to a new {@link NirsReading} entity.
     *
     * @param request the creation request
     * @return a new, unpersisted NIRS reading entity
     */
    public NirsReading toEntity(final CreateNirsReadingRequest request) {
        final NirsReading entity = new NirsReading();
        entity.setPatientId(request.patientId());
        entity.setSite(request.site());
        entity.setRso2Value(request.rso2Value());
        entity.setBaseline(request.baseline());
        entity.setRecordedAt(request.recordedAt());
        entity.setDeviceIdentifier(request.deviceIdentifier());
        entity.setNotes(request.notes());
        return entity;
    }
}
