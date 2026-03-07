package com.nicusystem.consent;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for PatientConsent entity.
 *
 * @param id            unique identifier
 * @param patientId     reference to the patient
 * @param consentType   type of consent
 * @param consentStatus status of the consent
 * @param signedBy      name of the person who signed
 * @param relationship  relationship of the signer to the patient
 * @param signedAt      when the consent was signed
 * @param expiresAt     optional expiry date
 * @param notes         optional notes
 * @param active        whether this consent record is active
 */
public record PatientConsentDto(
        UUID id,
        UUID patientId,
        ConsentType consentType,
        ConsentStatus consentStatus,
        String signedBy,
        String relationship,
        Instant signedAt,
        Instant expiresAt,
        String notes,
        boolean active
) {
}
