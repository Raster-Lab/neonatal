package com.nicusystem.fluid;

/**
 * Enumeration of fluid categories for detailed intake/output classification.
 */
public enum FluidCategory {
    /** Intravenous fluid intake. */
    IV_FLUID,
    /** Oral or enteral feed intake. */
    ORAL_FEED,
    /** Blood product intake. */
    BLOOD_PRODUCT,
    /** Urine output. */
    URINE,
    /** Stool output. */
    STOOL,
    /** Drain output. */
    DRAIN,
    /** Emesis output. */
    EMESIS,
    /** Other fluid category. */
    OTHER
}
