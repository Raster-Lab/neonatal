package com.nicusystem.pipeline;

/**
 * Types of bedside monitors used in the NICU for real-time data ingestion.
 */
public enum MonitorDataSource {

    /** Cardiac monitor for heart rhythm and rate. */
    CARDIAC_MONITOR,

    /** Pulse oximeter for SpO2 measurement. */
    PULSE_OXIMETER,

    /** Ventilator for respiratory support data. */
    VENTILATOR,

    /** Infusion pump for IV fluid and medication delivery. */
    INFUSION_PUMP,

    /** Temperature probe for continuous thermal monitoring. */
    TEMPERATURE_PROBE,

    /** Blood pressure monitor for hemodynamic data. */
    BLOOD_PRESSURE_MONITOR,

    /** Capnograph for end-tidal CO2 monitoring. */
    CAPNOGRAPH,

    /** EEG monitor for brain activity monitoring. */
    EEG_MONITOR,

    /** Near-infrared spectroscopy monitor for tissue oxygenation. */
    NIRS_MONITOR
}
