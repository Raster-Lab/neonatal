package com.nicusystem.nutrition;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object representing a feeding order.
 *
 * <p>Used to transfer feeding order data from the service layer to callers,
 * including API responses.</p>
 *
 * @param id              unique identifier of the feeding order
 * @param patientId       UUID of the patient
 * @param feedingType     type of feeding
 * @param feedingRoute    route of feeding delivery
 * @param volumeMl        volume per feed in millilitres
 * @param frequencyHours  hours between feeds
 * @param startedAt       when the order was started
 * @param discontinuedAt  when the order was discontinued, or null if active
 * @param orderedBy       clinician who placed the order
 * @param notes           additional clinical notes
 * @param createdAt       audit timestamp of creation
 * @param updatedAt       audit timestamp of last update
 */
public record FeedingOrderDto(
        UUID id,
        UUID patientId,
        FeedingType feedingType,
        FeedingRoute feedingRoute,
        Double volumeMl,
        Integer frequencyHours,
        Instant startedAt,
        Instant discontinuedAt,
        String orderedBy,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {}
