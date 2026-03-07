package com.nicusystem.vitals;

/**
 * Types of vital signs monitored in the NICU.
 */
public enum VitalSignType {
    /** Heart rate in beats per minute. */
    HEART_RATE,
    /** Respiratory rate in breaths per minute. */
    RESPIRATORY_RATE,
    /** Oxygen saturation percentage. */
    SPO2,
    /** Blood pressure systolic in mmHg. */
    BLOOD_PRESSURE_SYSTOLIC,
    /** Blood pressure diastolic in mmHg. */
    BLOOD_PRESSURE_DIASTOLIC,
    /** Temperature in degrees Celsius. */
    TEMPERATURE,
    /** Mean arterial pressure in mmHg. */
    MEAN_ARTERIAL_PRESSURE,
    /** End-tidal CO2 in mmHg. */
    END_TIDAL_CO2,
    /** Perfusion index percentage. */
    PERFUSION_INDEX
}
