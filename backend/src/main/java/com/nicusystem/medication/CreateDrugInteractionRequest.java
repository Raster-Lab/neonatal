package com.nicusystem.medication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a drug-drug interaction record.
 *
 * @param drug1Name           first drug name
 * @param drug2Name           second drug name
 * @param interactionSeverity severity level
 * @param description         description of the interaction
 * @param clinicalEffect      clinical effect description
 * @param management          management recommendation
 */
public record CreateDrugInteractionRequest(
        @NotBlank String drug1Name,
        @NotBlank String drug2Name,
        @NotNull DrugInteractionSeverity interactionSeverity,
        String description,
        String clinicalEffect,
        String management
) {
}
