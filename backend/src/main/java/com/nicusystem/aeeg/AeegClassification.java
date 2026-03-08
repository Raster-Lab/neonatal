package com.nicusystem.aeeg;

/**
 * Enumeration of amplitude-integrated EEG background pattern classifications.
 *
 * <p>Used to categorise aEEG trace patterns in neonatal brain monitoring.</p>
 */
public enum AeegClassification {

    /** Continuous normal voltage pattern. */
    CONTINUOUS_NORMAL_VOLTAGE,

    /** Discontinuous pattern. */
    DISCONTINUOUS,

    /** Burst suppression pattern. */
    BURST_SUPPRESSION,

    /** Low voltage pattern. */
    LOW_VOLTAGE,

    /** Flat trace pattern. */
    FLAT_TRACE,

    /** Seizure activity detected. */
    SEIZURE
}
