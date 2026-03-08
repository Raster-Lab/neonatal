package com.nicusystem.waveform;

/**
 * Types of real-time waveform data captured in the NICU.
 */
public enum WaveformType {

    /** Electrocardiogram waveform. */
    ECG,

    /** Pulse oximetry plethysmograph waveform. */
    PULSE_OXIMETRY,

    /** Respiratory waveform. */
    RESPIRATORY,

    /** Invasive or non-invasive blood pressure waveform. */
    BLOOD_PRESSURE,

    /** Capnography (end-tidal CO2) waveform. */
    CAPNOGRAPHY
}
