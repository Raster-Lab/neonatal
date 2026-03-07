package com.nicusystem.nutrition;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request record for creating a new breast milk inventory entry.
 *
 * <p>Fields annotated with {@link NotNull} or {@link NotBlank} are mandatory.
 * Boolean fields default to {@code false} when not provided.</p>
 *
 * @param patientId   UUID of the patient (required)
 * @param label       container label or barcode (required, non-blank)
 * @param volumeMl    volume in millilitres (required)
 * @param collectedAt timestamp when milk was collected (required)
 * @param expiresAt   expiry timestamp; null if not applicable
 * @param donorMilk   true if this is donor milk
 * @param fortified   true if fortifier has been added
 * @param notes       additional clinical notes
 */
public record CreateBreastMilkInventoryRequest(
        @NotNull UUID patientId,
        @NotBlank String label,
        @NotNull Double volumeMl,
        @NotNull Instant collectedAt,
        Instant expiresAt,
        boolean donorMilk,
        boolean fortified,
        String notes
) {}
