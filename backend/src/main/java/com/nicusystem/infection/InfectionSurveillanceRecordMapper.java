package com.nicusystem.infection;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between InfectionSurveillanceRecord entities and DTOs.
 */
@Component
public class InfectionSurveillanceRecordMapper {

    /**
     * Converts an InfectionSurveillanceRecord entity to a DTO.
     *
     * @param entity the entity to convert
     * @return the DTO
     */
    public InfectionSurveillanceRecordDto toDto(final InfectionSurveillanceRecord entity) {
        return new InfectionSurveillanceRecordDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getSurveillanceType(),
                entity.getEventDate(),
                entity.isConfirmed(),
                entity.getPathogen(),
                entity.getAntibioticDays(),
                entity.getCentralLineDays(),
                entity.getVentilatorDays(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a CreateInfectionSurveillanceRecordRequest to an entity.
     *
     * @param request the creation request
     * @return the entity
     */
    public InfectionSurveillanceRecord toEntity(
            final CreateInfectionSurveillanceRecordRequest request) {
        final InfectionSurveillanceRecord record = new InfectionSurveillanceRecord();
        record.setPatientId(request.patientId());
        record.setSurveillanceType(request.surveillanceType());
        record.setEventDate(request.eventDate());
        record.setConfirmed(request.confirmed());
        record.setPathogen(request.pathogen());
        record.setAntibioticDays(request.antibioticDays());
        record.setCentralLineDays(request.centralLineDays());
        record.setVentilatorDays(request.ventilatorDays());
        record.setNotes(request.notes());
        return record;
    }
}
