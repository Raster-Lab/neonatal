package com.nicusystem.medication;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between ContinuousInfusion entities and DTOs.
 */
@Component
public class ContinuousInfusionMapper {

    /**
     * Converts a ContinuousInfusion entity to a DTO.
     *
     * @param entity the continuous infusion entity
     * @return the continuous infusion DTO
     */
    public ContinuousInfusionDto toDto(final ContinuousInfusion entity) {
        return new ContinuousInfusionDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getDrugName(),
                entity.getConcentration(),
                entity.getConcentrationUnit(),
                entity.getRate(),
                entity.getRateUnit(),
                entity.getDosePerKgPerMin(),
                entity.getWeightGrams(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getStatus(),
                entity.getOrderedBy(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a CreateContinuousInfusionRequest to a ContinuousInfusion entity.
     *
     * @param request the create request
     * @return the continuous infusion entity
     */
    public ContinuousInfusion toEntity(final CreateContinuousInfusionRequest request) {
        final ContinuousInfusion entity = new ContinuousInfusion();
        entity.setPatientId(request.patientId());
        entity.setDrugName(request.drugName());
        entity.setConcentration(request.concentration());
        entity.setConcentrationUnit(request.concentrationUnit());
        entity.setRate(request.rate());
        entity.setRateUnit(request.rateUnit());
        entity.setDosePerKgPerMin(request.dosePerKgPerMin());
        entity.setWeightGrams(request.weightGrams());
        entity.setStartTime(request.startTime());
        entity.setOrderedBy(request.orderedBy());
        entity.setNotes(request.notes());
        return entity;
    }
}
