package com.nicusystem.growth;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between GrowthMeasurement entities and DTOs.
 */
@Component
public class GrowthMeasurementMapper {

    /**
     * Converts a GrowthMeasurement entity to a GrowthMeasurementDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public GrowthMeasurementDto toDto(final GrowthMeasurement entity) {
        return new GrowthMeasurementDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getMeasurementType(),
                entity.getValue(),
                entity.getPercentile(),
                entity.getZScore(),
                entity.getCorrectedAgeWeeks(),
                entity.getMeasuredAt(),
                entity.getNotes()
        );
    }

    /**
     * Converts a CreateGrowthMeasurementRequest to a GrowthMeasurement entity.
     *
     * @param request the creation request
     * @return the GrowthMeasurement entity
     */
    public GrowthMeasurement toEntity(final CreateGrowthMeasurementRequest request) {
        final GrowthMeasurement measurement = new GrowthMeasurement();
        measurement.setPatientId(request.patientId());
        measurement.setMeasurementType(request.measurementType());
        measurement.setValue(request.value());
        measurement.setPercentile(request.percentile());
        measurement.setZScore(request.zScore());
        measurement.setCorrectedAgeWeeks(request.correctedAgeWeeks());
        measurement.setMeasuredAt(request.measuredAt());
        measurement.setNotes(request.notes());
        return measurement;
    }
}
