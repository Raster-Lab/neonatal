package com.nicusystem.flowsheet;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between FlowsheetEntry entities and DTOs.
 */
@Component
public class FlowsheetEntryMapper {

    /**
     * Converts a FlowsheetEntry entity to a FlowsheetEntryDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public FlowsheetEntryDto toDto(final FlowsheetEntry entity) {
        return new FlowsheetEntryDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getCategory(),
                entity.getEntryTime(),
                entity.getFieldName(),
                entity.getFieldValue(),
                entity.getDocumentedBy(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a CreateFlowsheetEntryRequest to a FlowsheetEntry entity.
     *
     * @param request the creation request
     * @return the FlowsheetEntry entity
     */
    public FlowsheetEntry toEntity(
            final CreateFlowsheetEntryRequest request) {
        final FlowsheetEntry entry = new FlowsheetEntry();
        entry.setPatientId(request.patientId());
        entry.setCategory(request.category());
        entry.setEntryTime(request.entryTime());
        entry.setFieldName(request.fieldName());
        entry.setFieldValue(request.fieldValue());
        entry.setDocumentedBy(request.documentedBy());
        entry.setNotes(request.notes());
        return entry;
    }
}
