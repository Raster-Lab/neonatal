package com.nicusystem.consent;

/**
 * Status of a patient consent record.
 */
public enum ConsentStatus {
    /** Consent has been granted. */
    GRANTED,
    /** Consent has been denied. */
    DENIED,
    /** Consent is pending. */
    PENDING,
    /** Consent has been revoked. */
    REVOKED
}
