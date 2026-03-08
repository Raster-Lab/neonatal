package com.nicusystem.vitals.autodoc;

/**
 * Configurable intervals for automated vital signs documentation.
 */
public enum AutoDocInterval {
    /** Document every 15 minutes. */
    EVERY_15_MINUTES,
    /** Document every 30 minutes. */
    EVERY_30_MINUTES,
    /** Document every hour. */
    HOURLY,
    /** Document every 2 hours. */
    EVERY_2_HOURS,
    /** Document every 4 hours. */
    EVERY_4_HOURS
}
