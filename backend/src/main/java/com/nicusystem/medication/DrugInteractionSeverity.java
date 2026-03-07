package com.nicusystem.medication;

/**
 * Severity levels for drug-drug interactions.
 */
public enum DrugInteractionSeverity {
    /** Combination is absolutely contraindicated. */
    CONTRAINDICATED,
    /** Combination may be life-threatening or cause permanent damage. */
    MAJOR,
    /** Combination may cause a moderate effect. */
    MODERATE,
    /** Combination has minimal clinical significance. */
    MINOR
}
