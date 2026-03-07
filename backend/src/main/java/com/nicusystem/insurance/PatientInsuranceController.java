package com.nicusystem.insurance;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for patient insurance management.
 */
@RestController
@RequestMapping("/api/v1/insurance")
@RequiredArgsConstructor
@Tag(name = "Patient Insurance", description = "Patient insurance and billing management")
public class PatientInsuranceController {

    private final PatientInsuranceService patientInsuranceService;

    /**
     * Creates a new patient insurance record.
     *
     * @param request the insurance data
     * @return the created insurance with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new patient insurance record")
    public ResponseEntity<PatientInsuranceDto> createInsurance(
            @Valid @RequestBody final CreatePatientInsuranceRequest request) {
        final PatientInsuranceDto created = patientInsuranceService.createInsurance(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a patient insurance record by ID.
     *
     * @param id the insurance UUID
     * @return the insurance DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get patient insurance by ID")
    public ResponseEntity<PatientInsuranceDto> getInsurance(@PathVariable final UUID id) {
        return ResponseEntity.ok(patientInsuranceService.getInsuranceById(id));
    }

    /**
     * Gets insurance records for a patient.
     *
     * @param patientId the patient UUID
     * @return list of insurance records
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get insurance records for a patient")
    public ResponseEntity<List<PatientInsuranceDto>> getInsurancesByPatient(
            @PathVariable final UUID patientId) {
        return ResponseEntity.ok(patientInsuranceService.getInsurancesByPatient(patientId));
    }

    /**
     * Gets insurance records for a patient filtered by insurance type.
     *
     * @param patientId the patient UUID
     * @param type      the insurance type
     * @return list of insurance records
     */
    @GetMapping("/patient/{patientId}/type/{type}")
    @Operation(summary = "Get insurance records for a patient by type")
    public ResponseEntity<List<PatientInsuranceDto>> getInsurancesByPatientAndType(
            @PathVariable final UUID patientId,
            @PathVariable final InsuranceType type) {
        return ResponseEntity.ok(
                patientInsuranceService.getInsurancesByPatientAndType(patientId, type));
    }

    /**
     * Updates a patient insurance record.
     *
     * @param id      the insurance UUID
     * @param request the updated insurance data
     * @return the updated insurance DTO
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a patient insurance record")
    public ResponseEntity<PatientInsuranceDto> updateInsurance(
            @PathVariable final UUID id,
            @Valid @RequestBody final CreatePatientInsuranceRequest request) {
        return ResponseEntity.ok(patientInsuranceService.updateInsurance(id, request));
    }
}
