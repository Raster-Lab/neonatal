package com.nicusystem.pipeline;

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
 * JPA entity representing a real-time data ingestion pipeline for bedside monitors.
 */
@Entity
@Table(name = "data_ingestion_pipeline")
@Getter
@Setter
@NoArgsConstructor
public class DataIngestionPipeline extends BaseEntity {

    /** Reference to the patient being monitored. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The type of bedside monitor data source. */
    @Enumerated(EnumType.STRING)
    @Column(name = "data_source", nullable = false)
    private MonitorDataSource dataSource;

    /** Unique identifier for the physical device. */
    @Column(name = "device_identifier", nullable = false, length = 100)
    private String deviceIdentifier;

    /** Current status of the pipeline connection. */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PipelineStatus status;

    /** Network endpoint for the device connection. */
    @Column(name = "connection_endpoint", nullable = false)
    private String connectionEndpoint;

    /** Interval in seconds between data polling cycles. */
    @Column(name = "polling_interval_seconds", nullable = false)
    private Integer pollingIntervalSeconds;

    /** Timestamp of the last data received from the device. */
    @Column(name = "last_data_received_at")
    private Instant lastDataReceivedAt;

    /** Optional notes about the pipeline configuration. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
