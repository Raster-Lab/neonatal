package com.nicusystem.medication;

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
 * REST controller for continuous infusion management.
 */
@RestController
@RequestMapping("/api/v1/continuous-infusions")
@RequiredArgsConstructor
@Tag(name = "Continuous Infusions",
        description = "Continuous infusion management")
public class ContinuousInfusionController {

    private final ContinuousInfusionService continuousInfusionService;

    /**
     * Creates a new continuous infusion order.
     *
     * @param request the creation request
     * @return the created infusion with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new continuous infusion")
    public ResponseEntity<ContinuousInfusionDto> createInfusion(
            @Valid @RequestBody
            final CreateContinuousInfusionRequest request) {
        final ContinuousInfusionDto created =
                continuousInfusionService.createInfusion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a continuous infusion by ID.
     *
     * @param id the infusion UUID
     * @return the infusion DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get continuous infusion by ID")
    public ResponseEntity<ContinuousInfusionDto> getInfusion(
            @PathVariable final UUID id) {
        return ResponseEntity.ok(
                continuousInfusionService.getInfusionById(id));
    }

    /**
     * Gets continuous infusions for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of infusions
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get continuous infusions for a patient")
    public ResponseEntity<Page<ContinuousInfusionDto>>
            getInfusionsByPatient(
                    @PathVariable final UUID patientId,
                    final Pageable pageable) {
        return ResponseEntity.ok(
                continuousInfusionService.getInfusionsByPatient(
                        patientId, pageable));
    }

    /**
     * Gets continuous infusions by patient and status.
     *
     * @param patientId the patient UUID
     * @param status    the infusion status
     * @param pageable  pagination information
     * @return page of infusions
     */
    @GetMapping("/patient/{patientId}/status/{status}")
    @Operation(summary = "Get continuous infusions by patient and status")
    public ResponseEntity<Page<ContinuousInfusionDto>>
            getInfusionsByPatientAndStatus(
                    @PathVariable final UUID patientId,
                    @PathVariable final InfusionStatus status,
                    final Pageable pageable) {
        return ResponseEntity.ok(
                continuousInfusionService.getInfusionsByPatientAndStatus(
                        patientId, status, pageable));
    }

    /**
     * Updates the status of a continuous infusion.
     *
     * @param id     the infusion UUID
     * @param status the new status
     * @return the updated infusion DTO
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update continuous infusion status")
    public ResponseEntity<ContinuousInfusionDto> updateStatus(
            @PathVariable final UUID id,
            @RequestParam final InfusionStatus status) {
        return ResponseEntity.ok(
                continuousInfusionService.updateInfusionStatus(
                        id, status));
    }

    /**
     * Adjusts the rate of a continuous infusion.
     *
     * @param id      the infusion UUID
     * @param newRate the new rate
     * @return the updated infusion DTO
     */
    @PatchMapping("/{id}/rate")
    @Operation(summary = "Adjust continuous infusion rate")
    public ResponseEntity<ContinuousInfusionDto> adjustRate(
            @PathVariable final UUID id,
            @RequestParam final Double newRate) {
        return ResponseEntity.ok(
                continuousInfusionService.adjustRate(id, newRate));
    }
}
