package com.nicusystem.medication;

import java.util.UUID;

/**
 * Data transfer object for DrugInteraction entity.
 *
 * @param id                   unique identifier
 * @param drug1Name            first drug name
 * @param drug2Name            second drug name
 * @param interactionSeverity  severity level
 * @param description          description of the interaction
 * @param clinicalEffect       clinical effect description
 * @param management           management recommendation
 * @param active               whether the record is active
 */
public record DrugInteractionDto(
        UUID id,
        String drug1Name,
        String drug2Name,
        DrugInteractionSeverity interactionSeverity,
        String description,
        String clinicalEffect,
        String management,
        boolean active
) {
}
