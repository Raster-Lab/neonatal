package com.nicusystem.consent;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a new patient consent record.
 *
 * @param patientId     reference to the patient
 * @param consentType   type of consent
 * @param consentStatus status of the consent
 * @param signedBy      name of the person who signed
 * @param relationship  relationship of the signer to the patient
 * @param signedAt      when the consent was signed
 * @param expiresAt     optional expiry date
 * @param notes         optional notes
 */
public record CreatePatientConsentRequest(
        @NotNull UUID patientId,
        @NotNull ConsentType consentType,
        @NotNull ConsentStatus consentStatus,
        String signedBy,
        String relationship,
        Instant signedAt,
        Instant expiresAt,
        String notes
) {
}
