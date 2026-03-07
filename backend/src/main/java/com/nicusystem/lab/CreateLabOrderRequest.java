package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request record for creating a new laboratory order.
 *
 * @param patientId        the patient UUID (required)
 * @param panelType        the laboratory panel type (required)
 * @param orderedAt        when the order was placed
 * @param orderedBy        clinician placing the order
 * @param specimenVolumeUl expected specimen volume in microliters
 * @param notes            optional notes
 */
public record CreateLabOrderRequest(
        @NotNull UUID patientId,
        @NotNull LabPanelType panelType,
        Instant orderedAt,
        String orderedBy,
        Double specimenVolumeUl,
        String notes
) {
}
