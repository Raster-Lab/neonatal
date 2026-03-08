package com.nicusystem.procedure;

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
 * REST controller for procedure documentation management.
 */
@RestController
@RequestMapping("/api/v1/procedure-docs")
@RequiredArgsConstructor
@Tag(name = "Procedure Documentation",
        description = "Procedure documentation templates and records")
public class ProcedureDocumentationController {

    private final ProcedureDocumentationService procedureDocumentationService;

    /**
     * Creates a new procedure documentation record.
     *
     * @param request the procedure documentation data
     * @return the created procedure documentation with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new procedure documentation record")
    public ResponseEntity<ProcedureDocumentationDto> create(
            @Valid @RequestBody
            final CreateProcedureDocumentationRequest request) {
        final ProcedureDocumentationDto created =
                procedureDocumentationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a procedure documentation record by ID.
     *
     * @param id the procedure documentation UUID
     * @return the procedure documentation DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get procedure documentation by ID")
    public ResponseEntity<ProcedureDocumentationDto> getById(
            @PathVariable final UUID id) {
        return ResponseEntity.ok(procedureDocumentationService.getById(id));
    }

    /**
     * Gets procedure documentation records for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of procedure documentation records
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get procedure documentation for a patient")
    public ResponseEntity<Page<ProcedureDocumentationDto>> getByPatient(
            @PathVariable final UUID patientId,
            final Pageable pageable) {
        return ResponseEntity.ok(
                procedureDocumentationService.getByPatient(
                        patientId, pageable));
    }

    /**
     * Gets procedure documentation records for a patient filtered by type.
     *
     * @param patientId the patient UUID
     * @param type      the procedure type
     * @param pageable  pagination information
     * @return page of procedure documentation records
     */
    @GetMapping("/patient/{patientId}/type/{type}")
    @Operation(summary = "Get procedure documentation for a patient by type")
    public ResponseEntity<Page<ProcedureDocumentationDto>>
            getByPatientAndType(
                    @PathVariable final UUID patientId,
                    @PathVariable final ProcedureType type,
                    final Pageable pageable) {
        return ResponseEntity.ok(
                procedureDocumentationService.getByPatientAndType(
                        patientId, type, pageable));
    }
}
