package com.nicusystem.fluid;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between FluidEntry entities and DTOs.
 */
@Component
public class FluidEntryMapper {

    /**
     * Converts a FluidEntry entity to a FluidEntryDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public FluidEntryDto toDto(final FluidEntry entity) {
        return new FluidEntryDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getEntryType(),
                entity.getCategory(),
                entity.getVolumeMl(),
                entity.getDescription(),
                entity.getRecordedAt(),
                entity.getRecordedBy()
        );
    }

    /**
     * Converts a CreateFluidEntryRequest to a FluidEntry entity.
     *
     * @param request the creation request
     * @return the FluidEntry entity
     */
    public FluidEntry toEntity(final CreateFluidEntryRequest request) {
        final FluidEntry entry = new FluidEntry();
        entry.setPatientId(request.patientId());
        entry.setEntryType(request.entryType());
        entry.setCategory(request.category());
        entry.setVolumeMl(request.volumeMl());
        entry.setDescription(request.description());
        entry.setRecordedAt(request.recordedAt());
        entry.setRecordedBy(request.recordedBy());
        return entry;
    }
}
