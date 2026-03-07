package com.nicusystem.respiratory;

/**
 * Enumeration of respiratory support modes used in neonatal intensive care.
 *
 * <p>Values represent the spectrum of respiratory interventions from room air
 * to the most invasive extracorporeal support, ordered by escalating level of support.</p>
 */
public enum RespiratorySupport {

    /** Room air, no supplemental support. */
    ROOM_AIR,

    /** Nasal cannula with low-flow oxygen. */
    NASAL_CANNULA,

    /** High-flow nasal cannula (HFNC). */
    HIGH_FLOW_NASAL_CANNULA,

    /** Continuous positive airway pressure. */
    CPAP,

    /** Non-invasive positive pressure ventilation. */
    NIPPV,

    /** Conventional mechanical ventilation (CMV/SIMV/AC/PSV). */
    CONVENTIONAL_VENTILATION,

    /** High-frequency oscillatory ventilation (HFOV). */
    HIGH_FREQUENCY_OSCILLATORY,

    /** High-frequency jet ventilation (HFJV). */
    HIGH_FREQUENCY_JET,

    /** Inhaled nitric oxide (iNO) therapy. */
    INHALED_NITRIC_OXIDE,

    /** Extracorporeal membrane oxygenation. */
    ECMO
}
