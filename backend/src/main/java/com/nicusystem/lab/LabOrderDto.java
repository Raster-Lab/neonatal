package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for LabOrder entity.
 *
 * @param id               unique identifier
 * @param patientId        reference to the patient
 * @param panelType        the laboratory panel type
 * @param status           current order status
 * @param orderedAt        when the order was placed
 * @param orderedBy        clinician who placed the order
 * @param collectedAt      when the specimen was collected
 * @param collectedBy      staff member who collected the specimen
 * @param specimenVolumeUl specimen volume in microliters
 * @param notes            optional notes
 * @param createdAt        audit: record creation time
 * @param updatedAt        audit: last update time
 */
public record LabOrderDto(
        UUID id,
        UUID patientId,
        LabPanelType panelType,
        LabOrderStatus status,
        Instant orderedAt,
        String orderedBy,
        Instant collectedAt,
        String collectedBy,
        Double specimenVolumeUl,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
}
