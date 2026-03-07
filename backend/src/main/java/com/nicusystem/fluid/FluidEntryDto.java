package com.nicusystem.fluid;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for FluidEntry entity.
 *
 * @param id          unique identifier
 * @param patientId   reference to the patient
 * @param entryType   type of fluid entry (intake or output)
 * @param category    category of fluid
 * @param volumeMl    volume in milliliters
 * @param description optional description
 * @param recordedAt  when the entry was recorded
 * @param recordedBy  who recorded the entry
 */
public record FluidEntryDto(
        UUID id,
        UUID patientId,
        FluidEntryType entryType,
        FluidCategory category,
        Double volumeMl,
        String description,
        Instant recordedAt,
        String recordedBy
) {
}
