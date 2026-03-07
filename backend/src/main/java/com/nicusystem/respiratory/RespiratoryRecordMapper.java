package com.nicusystem.respiratory;

import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between {@link RespiratoryRecord} entities and DTOs.
 */
@Component
public class RespiratoryRecordMapper {

    /**
     * Converts a {@link RespiratoryRecord} entity to a {@link RespiratoryRecordDto}.
     *
     * @param entity the respiratory record entity
     * @return the corresponding DTO
     */
    public RespiratoryRecordDto toDto(final RespiratoryRecord entity) {
        return new RespiratoryRecordDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getSupportMode(),
                entity.getFio2Percent(),
                entity.getPeep(),
                entity.getPip(),
                entity.getRatePerMin(),
                entity.getTiSeconds(),
                entity.getMapCmh2o(),
                entity.getFlowLpm(),
                entity.getRecordedAt(),
                entity.getRecordedBy(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a {@link CreateRespiratoryRecordRequest} to a new {@link RespiratoryRecord} entity.
     *
     * @param request the creation request
     * @return a new, unpersisted respiratory record entity
     */
    public RespiratoryRecord toEntity(final CreateRespiratoryRecordRequest request) {
        final RespiratoryRecord entity = new RespiratoryRecord();
        entity.setPatientId(request.patientId());
        entity.setSupportMode(request.supportMode());
        entity.setFio2Percent(request.fio2Percent());
        entity.setPeep(request.peep());
        entity.setPip(request.pip());
        entity.setRatePerMin(request.ratePerMin());
        entity.setTiSeconds(request.tiSeconds());
        entity.setMapCmh2o(request.mapCmh2o());
        entity.setFlowLpm(request.flowLpm());
        entity.setRecordedAt(request.recordedAt());
        entity.setRecordedBy(request.recordedBy());
        entity.setNotes(request.notes());
        return entity;
    }
}
