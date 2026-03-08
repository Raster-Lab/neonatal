package com.nicusystem.nirs;

/**
 * Enumeration of near-infrared spectroscopy (NIRS) sensor placement sites.
 *
 * <p>Each value identifies a body region where regional oxygen saturation (rSO2)
 * can be measured in a neonatal patient.</p>
 */
public enum NirsSite {

    /** Left cerebral hemisphere. */
    LEFT_CEREBRAL,

    /** Right cerebral hemisphere. */
    RIGHT_CEREBRAL,

    /** Somatic (peripheral tissue). */
    SOMATIC,

    /** Renal region. */
    RENAL,

    /** Mesenteric (splanchnic) region. */
    MESENTERIC
}
