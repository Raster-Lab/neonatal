package com.nicusystem.pipeline;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a new data ingestion pipeline.
 *
 * @param patientId               reference to the patient
 * @param dataSource              type of bedside monitor
 * @param deviceIdentifier        unique device identifier
 * @param status                  initial pipeline status
 * @param connectionEndpoint      network endpoint for device connection
 * @param pollingIntervalSeconds  interval between data polling cycles
 * @param lastDataReceivedAt      timestamp of last data received (optional)
 * @param notes                   optional notes
 */
public record CreateDataIngestionPipelineRequest(
        @NotNull UUID patientId,
        @NotNull MonitorDataSource dataSource,
        @NotNull String deviceIdentifier,
        @NotNull PipelineStatus status,
        @NotNull String connectionEndpoint,
        @NotNull Integer pollingIntervalSeconds,
        Instant lastDataReceivedAt,
        String notes
) {
}
