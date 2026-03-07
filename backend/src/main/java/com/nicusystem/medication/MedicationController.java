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
 * REST controller for medication and pharmacy management.
 */
@RestController
@RequestMapping("/api/v1/medications")
@RequiredArgsConstructor
@Tag(name = "Medications", description = "Medication and pharmacy management")
public class MedicationController {

    private final MedicationService medicationService;

    /**
     * Creates a new medication order.
     *
     * @param request the medication creation request
     * @return the created medication with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new medication order")
    public ResponseEntity<MedicationDto> createMedication(
            @Valid @RequestBody final CreateMedicationRequest request) {
        final MedicationDto created =
                medicationService.createMedication(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a medication by ID.
     *
     * @param id the medication UUID
     * @return the medication DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get medication by ID")
    public ResponseEntity<MedicationDto> getMedication(
            @PathVariable final UUID id) {
        return ResponseEntity.ok(medicationService.getMedicationById(id));
    }

    /**
     * Gets medications for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of medications
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get medications for a patient")
    public ResponseEntity<Page<MedicationDto>> getMedicationsByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(
                medicationService.getMedicationsByPatient(
                        patientId, pageable));
    }

    /**
     * Gets medications for a patient filtered by status.
     *
     * @param patientId the patient UUID
     * @param status    the medication status
     * @param pageable  pagination information
     * @return page of medications
     */
    @GetMapping("/patient/{patientId}/status/{status}")
    @Operation(summary = "Get medications for a patient by status")
    public ResponseEntity<Page<MedicationDto>> getMedicationsByPatientAndStatus(
            @PathVariable final UUID patientId,
            @PathVariable final MedicationStatus status,
            final Pageable pageable) {
        return ResponseEntity.ok(
                medicationService.getMedicationsByPatientAndStatus(
                        patientId, status, pageable));
    }

    /**
     * Updates the status of a medication order.
     *
     * @param id     the medication UUID
     * @param status the new status
     * @return the updated medication DTO
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update medication status")
    public ResponseEntity<MedicationDto> updateStatus(
            @PathVariable final UUID id,
            @RequestParam final MedicationStatus status) {
        return ResponseEntity.ok(
                medicationService.updateMedicationStatus(id, status));
    }
}
