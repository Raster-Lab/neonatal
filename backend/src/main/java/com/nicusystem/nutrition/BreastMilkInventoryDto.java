package com.nicusystem.nutrition;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object representing a breast milk inventory entry.
 *
 * <p>Used to expose breast milk inventory data to API consumers without
 * exposing the underlying JPA entity.</p>
 *
 * @param id          unique identifier of the inventory entry
 * @param patientId   UUID of the patient this milk belongs to
 * @param label       label or barcode identifier on the container
 * @param volumeMl    volume in millilitres
 * @param collectedAt timestamp of collection
 * @param expiresAt   expiry timestamp, or null if not set
 * @param donorMilk   true if this is donor milk; false for mother's own milk
 * @param fortified   true if fortifier has been added
 * @param notes       additional clinical notes
 * @param createdAt   audit timestamp of creation
 * @param updatedAt   audit timestamp of last update
 */
public record BreastMilkInventoryDto(
        UUID id,
        UUID patientId,
        String label,
        Double volumeMl,
        Instant collectedAt,
        Instant expiresAt,
        boolean donorMilk,
        boolean fortified,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {}
