package com.nicusystem.transfer;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for patient transfer tracking.
 */
@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
@Tag(name = "Transfer", description = "Patient transfer tracking")
public class PatientTransferController {

    private final PatientTransferService patientTransferService;

    /**
     * Records a new patient transfer.
     *
     * @param request the transfer creation request
     * @return the created transfer with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Record a patient transfer")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PatientTransferDto> createTransfer(
            @Valid @RequestBody final CreatePatientTransferRequest request) {
        final PatientTransferDto created = patientTransferService.createTransfer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a transfer by ID.
     *
     * @param id the transfer UUID
     * @return the transfer DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get transfer by ID")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PatientTransferDto> getTransfer(@PathVariable final UUID id) {
        return ResponseEntity.ok(patientTransferService.getTransferById(id));
    }

    /**
     * Retrieves all transfers for a given patient.
     *
     * @param patientId the patient UUID
     * @return list of transfer DTOs
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get all transfers for a patient")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PatientTransferDto>> getTransfersByPatient(
            @PathVariable final UUID patientId) {
        return ResponseEntity.ok(patientTransferService.getTransfersByPatientId(patientId));
    }
}
