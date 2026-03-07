package com.nicusystem.infection;

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
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for isolation precaution management.
 */
@RestController
@RequestMapping("/api/v1/isolation-precautions")
@RequiredArgsConstructor
@Tag(name = "Isolation Precautions", description = "Patient isolation precaution management")
public class IsolationPrecautionController {

    private final IsolationPrecautionService service;

    /**
     * Creates a new isolation precaution.
     *
     * @param request the creation request
     * @return the created precaution with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new isolation precaution")
    public ResponseEntity<IsolationPrecautionDto> createPrecaution(
            @Valid @RequestBody final CreateIsolationPrecautionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createPrecaution(request));
    }

    /**
     * Retrieves an isolation precaution by ID.
     *
     * @param id the precaution UUID
     * @return the precaution DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get isolation precaution by ID")
    public ResponseEntity<IsolationPrecautionDto> getPrecaution(
            @PathVariable final UUID id) {
        return ResponseEntity.ok(service.getPrecautionById(id));
    }

    /**
     * Gets all isolation precautions for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination info
     * @return page of precautions
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get isolation precautions for a patient")
    public ResponseEntity<Page<IsolationPrecautionDto>> getPrecautionsByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(service.getPrecautionsByPatient(patientId, pageable));
    }

    /**
     * Gets isolation precautions for a patient filtered by type.
     *
     * @param patientId      the patient UUID
     * @param precautionType the precaution type
     * @param pageable       pagination info
     * @return page of precautions
     */
    @GetMapping("/patient/{patientId}/type/{precautionType}")
    @Operation(summary = "Get isolation precautions by patient and type")
    public ResponseEntity<Page<IsolationPrecautionDto>> getPrecautionsByPatientAndType(
            @PathVariable final UUID patientId,
            @PathVariable final IsolationPrecautionType precautionType,
            final Pageable pageable) {
        return ResponseEntity.ok(
                service.getPrecautionsByPatientAndType(patientId, precautionType, pageable));
    }

    /**
     * Discontinues an isolation precaution.
     *
     * @param id the precaution UUID
     * @return the updated precaution DTO
     */
    @PatchMapping("/{id}/discontinue")
    @Operation(summary = "Discontinue an isolation precaution")
    public ResponseEntity<IsolationPrecautionDto> discontinuePrecaution(
            @PathVariable final UUID id) {
        return ResponseEntity.ok(service.discontinuePrecaution(id));
    }
}
