package com.nicusystem.medication;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between IvFluidOrder entities and DTOs.
 */
@Component
public class IvFluidOrderMapper {

    /**
     * Converts an IvFluidOrder entity to a DTO.
     *
     * @param entity the IV fluid order entity
     * @return the IV fluid order DTO
     */
    public IvFluidOrderDto toDto(final IvFluidOrder entity) {
        return new IvFluidOrderDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getFluidType(),
                entity.getBaseSolution(),
                entity.getConcentration(),
                entity.getConcentrationUnit(),
                entity.getRate(),
                entity.getRateUnit(),
                entity.getTotalVolume(),
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
     * Converts a CreateIvFluidOrderRequest to an IvFluidOrder entity.
     *
     * @param request the create request
     * @return the IV fluid order entity
     */
    public IvFluidOrder toEntity(final CreateIvFluidOrderRequest request) {
        final IvFluidOrder entity = new IvFluidOrder();
        entity.setPatientId(request.patientId());
        entity.setFluidType(request.fluidType());
        entity.setBaseSolution(request.baseSolution());
        entity.setConcentration(request.concentration());
        entity.setConcentrationUnit(request.concentrationUnit());
        entity.setRate(request.rate());
        entity.setRateUnit(request.rateUnit());
        entity.setTotalVolume(request.totalVolume());
        entity.setStartTime(request.startTime());
        entity.setOrderedBy(request.orderedBy());
        entity.setNotes(request.notes());
        return entity;
    }
}
