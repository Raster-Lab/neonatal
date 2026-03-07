package com.nicusystem.consent;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import com.nicusystem.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing a patient consent record in the NICU.
 */
@Entity
@Table(name = "patient_consent")
@Getter
@Setter
@NoArgsConstructor
public class PatientConsent extends BaseEntity {

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The type of consent. */
    @Enumerated(EnumType.STRING)
    @Column(name = "consent_type", nullable = false)
    private ConsentType consentType;

    /** The status of the consent. */
    @Enumerated(EnumType.STRING)
    @Column(name = "consent_status", nullable = false)
    private ConsentStatus consentStatus;

    /** Name of the person who signed the consent. */
    @Column(name = "signed_by")
    private String signedBy;

    /** Relationship of the signer to the patient. */
    @Column(name = "relationship")
    private String relationship;

    /** When the consent was signed. */
    @Column(name = "signed_at")
    private Instant signedAt;

    /** Optional expiry date for the consent. */
    @Column(name = "expires_at")
    private Instant expiresAt;

    /** Optional notes about the consent. */
    @Column(columnDefinition = "TEXT")
    private String notes;

    /** Whether this consent record is active (soft delete). */
    @Column(nullable = false)
    private boolean active = true;
}
