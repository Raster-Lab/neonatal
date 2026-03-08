package com.nicusystem.pipeline;

import java.util.List;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing real-time data ingestion pipelines for bedside monitors.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DataIngestionPipelineService {

    private final DataIngestionPipelineRepository dataIngestionPipelineRepository;
    private final DataIngestionPipelineMapper dataIngestionPipelineMapper;

    /**
     * Creates a new data ingestion pipeline.
     *
     * @param request the pipeline creation request
     * @return the created pipeline DTO
     */
    @Transactional
    public DataIngestionPipelineDto create(final CreateDataIngestionPipelineRequest request) {
        final DataIngestionPipeline pipeline = dataIngestionPipelineMapper.toEntity(request);
        final DataIngestionPipeline saved = dataIngestionPipelineRepository.save(pipeline);
        log.info("Pipeline created: source={}, patientId={}", request.dataSource(), request.patientId());
        return dataIngestionPipelineMapper.toDto(saved);
    }

    /**
     * Retrieves a pipeline by ID.
     *
     * @param id the pipeline UUID
     * @return the pipeline DTO
     */
    @Transactional(readOnly = true)
    public DataIngestionPipelineDto getById(final UUID id) {
        return dataIngestionPipelineRepository.findById(id)
                .map(dataIngestionPipelineMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "DataIngestionPipeline", id.toString()));
    }

    /**
     * Gets pipelines for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of pipeline DTOs
     */
    @Transactional(readOnly = true)
    public Page<DataIngestionPipelineDto> getByPatient(final UUID patientId, final Pageable pageable) {
        return dataIngestionPipelineRepository
                .findByPatientIdOrderByCreatedAtDesc(patientId, pageable)
                .map(dataIngestionPipelineMapper::toDto);
    }

    /**
     * Gets pipelines filtered by status.
     *
     * @param status the pipeline status
     * @return list of pipeline DTOs with the given status
     */
    @Transactional(readOnly = true)
    public List<DataIngestionPipelineDto> getActiveByStatus(final PipelineStatus status) {
        return dataIngestionPipelineRepository.findByStatus(status)
                .stream()
                .map(dataIngestionPipelineMapper::toDto)
                .toList();
    }

    /**
     * Updates the status of a pipeline.
     *
     * @param id     the pipeline UUID
     * @param status the new status
     * @return the updated pipeline DTO
     */
    @Transactional
    public DataIngestionPipelineDto updateStatus(final UUID id, final PipelineStatus status) {
        final DataIngestionPipeline pipeline = dataIngestionPipelineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "DataIngestionPipeline", id.toString()));
        pipeline.setStatus(status);
        final DataIngestionPipeline saved = dataIngestionPipelineRepository.save(pipeline);
        log.info("Pipeline status updated: id={}, status={}", id, status);
        return dataIngestionPipelineMapper.toDto(saved);
    }
}
