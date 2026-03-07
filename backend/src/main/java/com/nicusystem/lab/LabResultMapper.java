package com.nicusystem.lab;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between LabResult entities and DTOs.
 */
@Component
public class LabResultMapper {

    /**
     * Converts a LabResult entity to a LabResultDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public LabResultDto toDto(final LabResult entity) {
        return new LabResultDto(
                entity.getId(),
                entity.getLabOrderId(),
                entity.getPatientId(),
                entity.getTestName(),
                entity.getResultValue(),
                entity.getUnit(),
                entity.getReferenceRangeLow(),
                entity.getReferenceRangeHigh(),
                entity.isCritical(),
                entity.isAbnormal(),
                entity.getResultedAt(),
                entity.getResultedBy(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a CreateLabResultRequest to a LabResult entity.
     *
     * @param request the creation request
     * @return the LabResult entity
     */
    public LabResult toEntity(final CreateLabResultRequest request) {
        final LabResult result = new LabResult();
        result.setLabOrderId(request.labOrderId());
        result.setPatientId(request.patientId());
        result.setTestName(request.testName());
        result.setResultValue(request.resultValue());
        result.setUnit(request.unit());
        result.setReferenceRangeLow(request.referenceRangeLow());
        result.setReferenceRangeHigh(request.referenceRangeHigh());
        result.setCritical(request.isCritical());
        result.setAbnormal(request.isAbnormal());
        result.setResultedAt(request.resultedAt());
        result.setResultedBy(request.resultedBy());
        result.setNotes(request.notes());
        return result;
    }
}
