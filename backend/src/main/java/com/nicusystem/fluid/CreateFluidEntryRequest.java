package com.nicusystem.fluid;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a new fluid entry.
 *
 * @param patientId   reference to the patient
 * @param entryType   type of fluid entry (intake or output)
 * @param category    category of fluid
 * @param volumeMl    volume in milliliters
 * @param description optional description
 * @param recordedAt  when the entry was recorded
 * @param recordedBy  who recorded the entry
 */
public record CreateFluidEntryRequest(
        @NotNull UUID patientId,
        @NotNull FluidEntryType entryType,
        @NotNull FluidCategory category,
        @NotNull Double volumeMl,
        String description,
        @NotNull Instant recordedAt,
        String recordedBy
) {
}
