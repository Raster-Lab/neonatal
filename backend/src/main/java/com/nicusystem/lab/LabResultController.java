package com.nicusystem.lab;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for laboratory result management.
 */
@RestController
@RequestMapping("/api/v1/lab-results")
@RequiredArgsConstructor
@Tag(name = "Lab Results", description = "Laboratory result management")
public class LabResultController {

    private final LabResultService labResultService;

    /**
     * Records a new laboratory result.
     *
     * @param request the lab result creation data
     * @return the recorded lab result with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Record a new laboratory result")
    public ResponseEntity<LabResultDto> recordLabResult(
            @Valid @RequestBody final CreateLabResultRequest request) {
        final LabResultDto created = labResultService.recordLabResult(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a lab result by ID.
     *
     * @param id the lab result UUID
     * @return the lab result DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get laboratory result by ID")
    public ResponseEntity<LabResultDto> getLabResultById(@PathVariable final UUID id) {
        return ResponseEntity.ok(labResultService.getLabResultById(id));
    }

    /**
     * Gets all lab results for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of lab results
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get laboratory results for a patient")
    public ResponseEntity<Page<LabResultDto>> getLabResultsByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(labResultService.getLabResultsByPatient(patientId, pageable));
    }

    /**
     * Gets all lab results for a specific lab order.
     *
     * @param labOrderId the lab order UUID
     * @return list of lab results for the order
     */
    @GetMapping("/order/{labOrderId}")
    @Operation(summary = "Get laboratory results for a lab order")
    public ResponseEntity<List<LabResultDto>> getLabResultsByLabOrder(
            @PathVariable final UUID labOrderId) {
        return ResponseEntity.ok(labResultService.getLabResultsByLabOrder(labOrderId));
    }

    /**
     * Gets critical lab results for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of critical lab results
     */
    @GetMapping("/patient/{patientId}/critical")
    @Operation(summary = "Get critical laboratory results for a patient")
    public ResponseEntity<Page<LabResultDto>> getCriticalLabResultsByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(
                labResultService.getCriticalLabResultsByPatient(patientId, pageable));
    }
}
