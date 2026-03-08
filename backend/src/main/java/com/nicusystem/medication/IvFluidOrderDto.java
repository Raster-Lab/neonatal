package com.nicusystem.medication;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for IV fluid order data.
 *
 * @param id the unique identifier
 * @param patientId the patient identifier
 * @param fluidType the type of IV fluid
 * @param baseSolution the base solution
 * @param concentration the concentration value
 * @param concentrationUnit the unit of concentration
 * @param rate the infusion rate
 * @param rateUnit the unit of rate
 * @param totalVolume the total volume ordered
 * @param startTime the start time
 * @param endTime the end time
 * @param status the order status
 * @param orderedBy the ordering clinician
 * @param notes additional notes
 * @param createdAt the creation timestamp
 * @param updatedAt the last update timestamp
 */
public record IvFluidOrderDto(
        UUID id,
        UUID patientId,
        String fluidType,
        String baseSolution,
        Double concentration,
        String concentrationUnit,
        Double rate,
        String rateUnit,
        Double totalVolume,
        Instant startTime,
        Instant endTime,
        IvFluidStatus status,
        String orderedBy,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
}
