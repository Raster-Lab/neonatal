package com.nicusystem.pipeline;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for DataIngestionPipeline entity.
 *
 * @param id                      unique identifier
 * @param patientId               reference to the patient
 * @param dataSource              type of bedside monitor
 * @param deviceIdentifier        unique device identifier
 * @param status                  current pipeline status
 * @param connectionEndpoint      network endpoint for device connection
 * @param pollingIntervalSeconds  interval between data polling cycles
 * @param lastDataReceivedAt      timestamp of last data received
 * @param notes                   optional notes
 */
public record DataIngestionPipelineDto(
        UUID id,
        UUID patientId,
        MonitorDataSource dataSource,
        String deviceIdentifier,
        PipelineStatus status,
        String connectionEndpoint,
        Integer pollingIntervalSeconds,
        Instant lastDataReceivedAt,
        String notes
) {
}
