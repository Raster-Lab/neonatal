package com.nicusystem.waveform;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import com.nicusystem.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing real-time waveform data for a neonatal patient.
 */
@Entity
@Table(name = "waveform_data")
@Getter
@Setter
@NoArgsConstructor
public class WaveformData extends BaseEntity {

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The type of waveform. */
    @Enumerated(EnumType.STRING)
    @Column(name = "waveform_type", nullable = false)
    private WaveformType waveformType;

    /** JSON array of numeric data points. */
    @Column(name = "data_points", columnDefinition = "TEXT", nullable = false)
    private String dataPoints;

    /** Sampling rate in Hertz. */
    @Column(name = "sampling_rate_hz", nullable = false)
    private Double samplingRateHz;

    /** Start time of the waveform segment. */
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    /** End time of the waveform segment. */
    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    /** Identifier of the monitoring device. */
    @Column(name = "device_identifier", length = 100)
    private String deviceIdentifier;

    /** Unit of measurement for the data points. */
    @Column(name = "unit", nullable = false)
    private String unit;

    /** Optional notes about the waveform data. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
