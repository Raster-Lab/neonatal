package com.nicusystem.transfer;

/**
 * Enum representing whether a patient transfer is internal or external.
 */
public enum PatientTransferType {
    /** Transfer within the same facility (unit to unit). */
    INTERNAL,
    /** Transfer to a different facility. */
    EXTERNAL
}
