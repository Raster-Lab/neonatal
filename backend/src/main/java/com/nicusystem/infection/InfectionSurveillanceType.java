package com.nicusystem.infection;

/** Types of healthcare-associated infection surveillance events. */
public enum InfectionSurveillanceType {
    /** Central line-associated bloodstream infection. */
    CLABSI,
    /** Ventilator-associated event. */
    VAE,
    /** Catheter-associated urinary tract infection. */
    CAUTI,
    /** Surgical site infection. */
    SURGICAL_SITE_INFECTION,
    /** Early-onset sepsis (within 72 hours of birth). */
    EARLY_ONSET_SEPSIS,
    /** Late-onset sepsis (after 72 hours of birth). */
    LATE_ONSET_SEPSIS
}
