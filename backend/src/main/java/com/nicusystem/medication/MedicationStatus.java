package com.nicusystem.medication;

/**
 * Enumeration of possible medication statuses in the NICU workflow.
 */
public enum MedicationStatus {

    /** Medication has been ordered by a provider. */
    ORDERED,

    /** Medication has been verified by pharmacy. */
    VERIFIED,

    /** Medication has been administered to the patient. */
    ADMINISTERED,

    /** Medication administration is temporarily held. */
    HELD,

    /** Medication has been discontinued. */
    DISCONTINUED
}
