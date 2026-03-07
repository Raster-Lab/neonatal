package com.nicusystem.infection;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between IsolationPrecaution entities and DTOs.
 */
@Component
public class IsolationPrecautionMapper {

    /**
     * Converts an IsolationPrecaution entity to a DTO.
     *
     * @param entity the entity to convert
     * @return the DTO
     */
    public IsolationPrecautionDto toDto(final IsolationPrecaution entity) {
        return new IsolationPrecautionDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getPrecautionType(),
                entity.getInitiatedAt(),
                entity.getDiscontinuedAt(),
                entity.getInitiatedBy(),
                entity.getIndication(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a CreateIsolationPrecautionRequest to an entity.
     *
     * @param request the creation request
     * @return the entity
     */
    public IsolationPrecaution toEntity(final CreateIsolationPrecautionRequest request) {
        final IsolationPrecaution precaution = new IsolationPrecaution();
        precaution.setPatientId(request.patientId());
        precaution.setPrecautionType(request.precautionType());
        precaution.setInitiatedAt(request.initiatedAt());
        precaution.setInitiatedBy(request.initiatedBy());
        precaution.setIndication(request.indication());
        precaution.setNotes(request.notes());
        return precaution;
    }
}
