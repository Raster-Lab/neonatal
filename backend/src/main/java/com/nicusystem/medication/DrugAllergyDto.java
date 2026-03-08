package com.nicusystem.medication;

import java.util.UUID;

/**
 * Data transfer object for DrugAllergy entity.
 *
 * @param id           unique identifier
 * @param patientId    patient identifier
 * @param allergenName name of the allergen
 * @param reactionType type of allergic reaction
 * @param severity     severity level
 * @param notes        additional notes
 * @param active       whether the record is active
 */
public record DrugAllergyDto(
        UUID id,
        UUID patientId,
        String allergenName,
        String reactionType,
        AllergySeverity severity,
        String notes,
        boolean active
) {
}
