package com.nicusystem.vitals;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between VitalSignAlarmThreshold entities and DTOs.
 */
@Component
public class VitalSignAlarmThresholdMapper {

    /**
     * Converts a VitalSignAlarmThreshold entity to a VitalSignAlarmThresholdDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public VitalSignAlarmThresholdDto toDto(final VitalSignAlarmThreshold entity) {
        return new VitalSignAlarmThresholdDto(
                entity.getId(),
                entity.getVitalType(),
                entity.getMinimumGestationalAgeWeeks(),
                entity.getMaximumGestationalAgeWeeks(),
                entity.getMinimumWeightGrams(),
                entity.getMaximumWeightGrams(),
                entity.getLowAlarmValue(),
                entity.getHighAlarmValue(),
                entity.getCriticalLowValue(),
                entity.getCriticalHighValue(),
                entity.getUnit(),
                entity.getDescription(),
                entity.isActive()
        );
    }

    /**
     * Converts a CreateVitalSignAlarmThresholdRequest to a VitalSignAlarmThreshold entity.
     *
     * @param request the creation request
     * @return the VitalSignAlarmThreshold entity
     */
    public VitalSignAlarmThreshold toEntity(
            final CreateVitalSignAlarmThresholdRequest request) {
        final VitalSignAlarmThreshold threshold = new VitalSignAlarmThreshold();
        threshold.setVitalType(request.vitalType());
        threshold.setMinimumGestationalAgeWeeks(request.minimumGestationalAgeWeeks());
        threshold.setMaximumGestationalAgeWeeks(request.maximumGestationalAgeWeeks());
        threshold.setMinimumWeightGrams(request.minimumWeightGrams());
        threshold.setMaximumWeightGrams(request.maximumWeightGrams());
        threshold.setLowAlarmValue(request.lowAlarmValue());
        threshold.setHighAlarmValue(request.highAlarmValue());
        threshold.setCriticalLowValue(request.criticalLowValue());
        threshold.setCriticalHighValue(request.criticalHighValue());
        threshold.setUnit(request.unit());
        threshold.setDescription(request.description());
        return threshold;
    }
}
