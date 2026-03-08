package com.nicusystem.procedure;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between ProcedureDocumentation entities and DTOs.
 */
@Component
public class ProcedureDocumentationMapper {

    /**
     * Converts a ProcedureDocumentation entity to a ProcedureDocumentationDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public ProcedureDocumentationDto toDto(final ProcedureDocumentation entity) {
        return new ProcedureDocumentationDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getProcedureType(),
                entity.getPerformedBy(),
                entity.getAssistedBy(),
                entity.getIndication(),
                entity.getTechnique(),
                entity.getFindings(),
                entity.getComplications(),
                entity.getNotes(),
                entity.getPerformedAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a CreateProcedureDocumentationRequest to a ProcedureDocumentation entity.
     *
     * @param request the creation request
     * @return the ProcedureDocumentation entity
     */
    public ProcedureDocumentation toEntity(
            final CreateProcedureDocumentationRequest request) {
        final ProcedureDocumentation doc = new ProcedureDocumentation();
        doc.setPatientId(request.patientId());
        doc.setProcedureType(request.procedureType());
        doc.setPerformedBy(request.performedBy());
        doc.setAssistedBy(request.assistedBy());
        doc.setIndication(request.indication());
        doc.setTechnique(request.technique());
        doc.setFindings(request.findings());
        doc.setComplications(request.complications());
        doc.setNotes(request.notes());
        doc.setPerformedAt(request.performedAt());
        return doc;
    }
}
