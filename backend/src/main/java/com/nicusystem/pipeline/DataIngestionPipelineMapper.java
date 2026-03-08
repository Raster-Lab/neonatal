package com.nicusystem.pipeline;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between DataIngestionPipeline entities and DTOs.
 */
@Component
public class DataIngestionPipelineMapper {

    /**
     * Converts a DataIngestionPipeline entity to a DataIngestionPipelineDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public DataIngestionPipelineDto toDto(final DataIngestionPipeline entity) {
        return new DataIngestionPipelineDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getDataSource(),
                entity.getDeviceIdentifier(),
                entity.getStatus(),
                entity.getConnectionEndpoint(),
                entity.getPollingIntervalSeconds(),
                entity.getLastDataReceivedAt(),
                entity.getNotes()
        );
    }

    /**
     * Converts a CreateDataIngestionPipelineRequest to a DataIngestionPipeline entity.
     *
     * @param request the creation request
     * @return the DataIngestionPipeline entity
     */
    public DataIngestionPipeline toEntity(final CreateDataIngestionPipelineRequest request) {
        final DataIngestionPipeline pipeline = new DataIngestionPipeline();
        pipeline.setPatientId(request.patientId());
        pipeline.setDataSource(request.dataSource());
        pipeline.setDeviceIdentifier(request.deviceIdentifier());
        pipeline.setStatus(request.status());
        pipeline.setConnectionEndpoint(request.connectionEndpoint());
        pipeline.setPollingIntervalSeconds(request.pollingIntervalSeconds());
        pipeline.setLastDataReceivedAt(request.lastDataReceivedAt());
        pipeline.setNotes(request.notes());
        return pipeline;
    }
}
