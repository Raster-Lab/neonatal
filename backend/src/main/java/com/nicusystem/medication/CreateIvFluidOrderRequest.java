package com.nicusystem.medication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

/**
 * Request to create a new IV fluid order.
 *
 * @param patientId the patient identifier
 * @param fluidType the type of IV fluid
 * @param baseSolution the base solution
 * @param concentration the concentration value
 * @param concentrationUnit the unit of concentration
 * @param rate the infusion rate
 * @param rateUnit the unit of rate
 * @param totalVolume the total volume ordered
 * @param startTime the start time
 * @param orderedBy the ordering clinician
 * @param notes additional notes
 */
public record CreateIvFluidOrderRequest(
        @NotNull UUID patientId,
        @NotBlank String fluidType,
        String baseSolution,
        Double concentration,
        String concentrationUnit,
        @NotNull Double rate,
        @NotBlank String rateUnit,
        Double totalVolume,
        Instant startTime,
        String orderedBy,
        String notes
) {
}
