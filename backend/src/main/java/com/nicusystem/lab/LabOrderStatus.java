package com.nicusystem.lab;

/**
 * Enumeration of possible statuses for a laboratory order.
 */
public enum LabOrderStatus {

    /** Order has been placed but specimen not yet collected. */
    ORDERED,

    /** Specimen has been collected from the patient. */
    COLLECTED,

    /** Specimen is being processed in the laboratory. */
    IN_PROGRESS,

    /** Results have been reported. */
    RESULTED,

    /** Order was cancelled before completion. */
    CANCELLED
}
