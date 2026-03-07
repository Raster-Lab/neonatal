package com.nicusystem.nutrition;

import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between {@link BreastMilkInventory} entities and DTOs.
 */
@Component
public class BreastMilkInventoryMapper {

    /**
     * Converts a {@link BreastMilkInventory} entity to a {@link BreastMilkInventoryDto}.
     *
     * @param entity the breast milk inventory entity
     * @return the corresponding DTO
     */
    public BreastMilkInventoryDto toDto(final BreastMilkInventory entity) {
        return new BreastMilkInventoryDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getLabel(),
                entity.getVolumeMl(),
                entity.getCollectedAt(),
                entity.getExpiresAt(),
                entity.isDonorMilk(),
                entity.isFortified(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a {@link CreateBreastMilkInventoryRequest} to a new
     * {@link BreastMilkInventory} entity.
     *
     * @param request the creation request
     * @return a new, unpersisted breast milk inventory entity
     */
    public BreastMilkInventory toEntity(final CreateBreastMilkInventoryRequest request) {
        final BreastMilkInventory entity = new BreastMilkInventory();
        entity.setPatientId(request.patientId());
        entity.setLabel(request.label());
        entity.setVolumeMl(request.volumeMl());
        entity.setCollectedAt(request.collectedAt());
        entity.setExpiresAt(request.expiresAt());
        entity.setDonorMilk(request.donorMilk());
        entity.setFortified(request.fortified());
        entity.setNotes(request.notes());
        return entity;
    }
}
