package com.nicusystem.clinical;

/**
 * Enumeration of clinical note types for SOAP documentation.
 */
public enum NoteType {
    /** General progress note using SOAP format. */
    PROGRESS,
    /** Admission assessment note. */
    ADMISSION,
    /** Procedure note documenting a clinical procedure. */
    PROCEDURE,
    /** Discharge summary note. */
    DISCHARGE,
    /** Specialist consultation note. */
    CONSULTATION
}
