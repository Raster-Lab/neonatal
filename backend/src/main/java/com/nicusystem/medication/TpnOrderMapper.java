package com.nicusystem.medication;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between TpnOrder entities and DTOs.
 */
@Component
public class TpnOrderMapper {

    /**
     * Converts a TpnOrder entity to a DTO.
     *
     * @param entity the TPN order entity
     * @return the TPN order DTO
     */
    public TpnOrderDto toDto(final TpnOrder entity) {
        return new TpnOrderDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getAminoAcidsPercent(),
                entity.getDextrosePercent(),
                entity.getLipidsPercent(),
                entity.getSodiumMeqPerL(),
                entity.getPotassiumMeqPerL(),
                entity.getCalciumMgPerDl(),
                entity.getMagnesiumMeqPerL(),
                entity.getPhosphorusMmolPerL(),
                entity.isTraceElementsIncluded(),
                entity.isMultivitaminsIncluded(),
                entity.getGir(),
                entity.getTotalVolumeMl(),
                entity.getInfusionRateMlPerHr(),
                entity.getCycleHours(),
                entity.getDayNumber(),
                entity.getStatus(),
                entity.getOrderedBy(),
                entity.getNotes(),
                entity.getWeightGrams(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a CreateTpnOrderRequest to a TpnOrder entity.
     *
     * @param request the create request
     * @return the TPN order entity
     */
    public TpnOrder toEntity(final CreateTpnOrderRequest request) {
        final TpnOrder entity = new TpnOrder();
        entity.setPatientId(request.patientId());
        entity.setAminoAcidsPercent(request.aminoAcidsPercent());
        entity.setDextrosePercent(request.dextrosePercent());
        entity.setLipidsPercent(request.lipidsPercent());
        entity.setSodiumMeqPerL(request.sodiumMeqPerL());
        entity.setPotassiumMeqPerL(request.potassiumMeqPerL());
        entity.setCalciumMgPerDl(request.calciumMgPerDl());
        entity.setMagnesiumMeqPerL(request.magnesiumMeqPerL());
        entity.setPhosphorusMmolPerL(request.phosphorusMmolPerL());
        entity.setTraceElementsIncluded(request.traceElementsIncluded());
        entity.setMultivitaminsIncluded(request.multivitaminsIncluded());
        entity.setGir(request.gir());
        entity.setTotalVolumeMl(request.totalVolumeMl());
        entity.setInfusionRateMlPerHr(request.infusionRateMlPerHr());
        entity.setCycleHours(request.cycleHours());
        entity.setDayNumber(request.dayNumber());
        entity.setOrderedBy(request.orderedBy());
        entity.setNotes(request.notes());
        entity.setWeightGrams(request.weightGrams());
        return entity;
    }
}
