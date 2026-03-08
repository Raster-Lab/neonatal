package com.nicusystem.medication;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between DrugAllergy entities and DTOs.
 */
@Component
public class DrugAllergyMapper {

    /**
     * Converts a DrugAllergy entity to a DrugAllergyDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public DrugAllergyDto toDto(final DrugAllergy entity) {
        return new DrugAllergyDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getAllergenName(),
                entity.getReactionType(),
                entity.getSeverity(),
                entity.getNotes(),
                entity.isActive()
        );
    }

    /**
     * Converts a CreateDrugAllergyRequest to a DrugAllergy entity.
     *
     * @param request the creation request
     * @return the DrugAllergy entity
     */
    public DrugAllergy toEntity(final CreateDrugAllergyRequest request) {
        final DrugAllergy allergy = new DrugAllergy();
        allergy.setPatientId(request.patientId());
        allergy.setAllergenName(request.allergenName());
        allergy.setReactionType(request.reactionType());
        allergy.setSeverity(request.severity());
        allergy.setNotes(request.notes());
        allergy.setActive(true);
        return allergy;
    }
}
