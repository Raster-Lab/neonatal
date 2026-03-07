package com.nicusystem.nutrition;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request record for creating a new feeding order.
 *
 * <p>Contains all required and optional fields needed to place a feeding order
 * for a neonatal patient. Fields annotated with {@link NotNull} are mandatory.</p>
 *
 * @param patientId       UUID of the patient (required)
 * @param feedingType     type of feeding to be administered (required)
 * @param feedingRoute    route of delivery (required)
 * @param volumeMl        volume per feed in millilitres (required)
 * @param frequencyHours  hours between feeds (required)
 * @param startedAt       when the order starts; null defaults to immediate
 * @param orderedBy       name or identifier of the ordering clinician
 * @param notes           additional clinical notes
 */
public record CreateFeedingOrderRequest(
        @NotNull UUID patientId,
        @NotNull FeedingType feedingType,
        @NotNull FeedingRoute feedingRoute,
        @NotNull Double volumeMl,
        @NotNull Integer frequencyHours,
        Instant startedAt,
        String orderedBy,
        String notes
) {}
