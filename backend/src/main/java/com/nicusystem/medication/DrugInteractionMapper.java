package com.nicusystem.medication;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between DrugInteraction entities and DTOs.
 */
@Component
public class DrugInteractionMapper {

    /**
     * Converts a DrugInteraction entity to a DrugInteractionDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public DrugInteractionDto toDto(final DrugInteraction entity) {
        return new DrugInteractionDto(
                entity.getId(),
                entity.getDrug1Name(),
                entity.getDrug2Name(),
                entity.getInteractionSeverity(),
                entity.getDescription(),
                entity.getClinicalEffect(),
                entity.getManagement(),
                entity.isActive()
        );
    }

    /**
     * Converts a CreateDrugInteractionRequest to a DrugInteraction entity.
     *
     * @param request the creation request
     * @return the DrugInteraction entity
     */
    public DrugInteraction toEntity(final CreateDrugInteractionRequest request) {
        final DrugInteraction interaction = new DrugInteraction();
        interaction.setDrug1Name(request.drug1Name());
        interaction.setDrug2Name(request.drug2Name());
        interaction.setInteractionSeverity(request.interactionSeverity());
        interaction.setDescription(request.description());
        interaction.setClinicalEffect(request.clinicalEffect());
        interaction.setManagement(request.management());
        interaction.setActive(true);
        return interaction;
    }
}
