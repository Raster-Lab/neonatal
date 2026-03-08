package com.nicusystem.pipeline;

/**
 * Status of a data ingestion pipeline connection.
 */
public enum PipelineStatus {

    /** Pipeline is actively receiving data. */
    ACTIVE,

    /** Pipeline is temporarily paused. */
    PAUSED,

    /** Pipeline encountered an error. */
    ERROR,

    /** Pipeline is disconnected from the device. */
    DISCONNECTED
}
