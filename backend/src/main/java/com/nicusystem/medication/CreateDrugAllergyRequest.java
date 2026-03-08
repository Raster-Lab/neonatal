package com.nicusystem.medication;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a drug allergy record.
 *
 * @param patientId    patient identifier
 * @param allergenName name of the allergen
 * @param reactionType type of allergic reaction
 * @param severity     severity level
 * @param notes        additional notes
 */
public record CreateDrugAllergyRequest(
        @NotNull UUID patientId,
        @NotBlank String allergenName,
        @NotBlank String reactionType,
        @NotNull AllergySeverity severity,
        String notes
) {
}
