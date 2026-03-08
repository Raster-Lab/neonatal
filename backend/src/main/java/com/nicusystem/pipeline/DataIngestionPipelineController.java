package com.nicusystem.pipeline;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing real-time data ingestion pipelines.
 */
@RestController
@RequestMapping("/api/v1/pipelines")
@RequiredArgsConstructor
@Tag(name = "Data Ingestion Pipelines", description = "Real-time data ingestion pipeline management")
public class DataIngestionPipelineController {

    private final DataIngestionPipelineService dataIngestionPipelineService;

    /**
     * Creates a new data ingestion pipeline.
     *
     * @param request the pipeline creation request
     * @return the created pipeline with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new data ingestion pipeline")
    public ResponseEntity<DataIngestionPipelineDto> create(
            @Valid @RequestBody final CreateDataIngestionPipelineRequest request) {
        final DataIngestionPipelineDto created = dataIngestionPipelineService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a pipeline by ID.
     *
     * @param id the pipeline UUID
     * @return the pipeline DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get pipeline by ID")
    public ResponseEntity<DataIngestionPipelineDto> getById(@PathVariable final UUID id) {
        return ResponseEntity.ok(dataIngestionPipelineService.getById(id));
    }

    /**
     * Gets pipelines for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of pipelines
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get pipelines for a patient")
    public ResponseEntity<Page<DataIngestionPipelineDto>> getByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(dataIngestionPipelineService.getByPatient(patientId, pageable));
    }

    /**
     * Gets pipelines filtered by status.
     *
     * @param status the pipeline status
     * @return list of pipelines with the given status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get pipelines by status")
    public ResponseEntity<List<DataIngestionPipelineDto>> getByStatus(
            @PathVariable final PipelineStatus status) {
        return ResponseEntity.ok(dataIngestionPipelineService.getActiveByStatus(status));
    }

    /**
     * Updates the status of a pipeline.
     *
     * @param id     the pipeline UUID
     * @param status the new status
     * @return the updated pipeline DTO
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update pipeline status")
    public ResponseEntity<DataIngestionPipelineDto> updateStatus(
            @PathVariable final UUID id,
            @RequestParam final PipelineStatus status) {
        return ResponseEntity.ok(dataIngestionPipelineService.updateStatus(id, status));
    }
}
