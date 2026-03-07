package com.nicusystem.patient;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for patient registration and demographics.
 */
@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Tag(name = "Patient", description = "Patient registration and demographics")
public class PatientController {

    private final PatientService patientService;

    /**
     * Creates a new patient.
     *
     * @param request the patient creation request
     * @return the created patient with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Register a new neonatal patient")
    public ResponseEntity<PatientDto> createPatient(
            @Valid @RequestBody final CreatePatientRequest request) {
        final PatientDto created = patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a patient by ID.
     *
     * @param id the patient UUID
     * @return the patient DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get patient by ID")
    public ResponseEntity<PatientDto> getPatient(@PathVariable final UUID id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    /**
     * Retrieves a patient by MRN.
     *
     * @param mrn the medical record number
     * @return the patient DTO
     */
    @GetMapping("/mrn/{mrn}")
    @Operation(summary = "Get patient by MRN")
    public ResponseEntity<PatientDto> getPatientByMrn(@PathVariable final String mrn) {
        return ResponseEntity.ok(patientService.getPatientByMrn(mrn));
    }

    /**
     * Returns a page of all active patients.
     *
     * @param pageable pagination information
     * @return page of active patients
     */
    @GetMapping
    @Operation(summary = "List all active patients")
    public ResponseEntity<Page<PatientDto>> getActivePatients(final Pageable pageable) {
        return ResponseEntity.ok(patientService.getActivePatients(pageable));
    }

    /**
     * Searches patients by name.
     *
     * @param name     name fragment to search
     * @param pageable pagination information
     * @return page of matching patients
     */
    @GetMapping("/search")
    @Operation(summary = "Search patients by name")
    public ResponseEntity<Page<PatientDto>> searchPatients(
            @RequestParam final String name, final Pageable pageable) {
        return ResponseEntity.ok(patientService.searchPatients(name, pageable));
    }

    /**
     * Returns all patients (siblings) linked to a mother.
     *
     * @param motherId the mother UUID
     * @return list of patient DTOs
     */
    @GetMapping("/mother/{motherId}")
    @Operation(summary = "Get patients by mother ID (sibling linkage)")
    public ResponseEntity<List<PatientDto>> getPatientsByMother(
            @PathVariable final UUID motherId) {
        return ResponseEntity.ok(patientService.getPatientsByMotherId(motherId));
    }

    /**
     * Updates an existing patient.
     *
     * @param id      the patient UUID
     * @param request the update request
     * @return the updated patient DTO
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update patient demographics")
    public ResponseEntity<PatientDto> updatePatient(
            @PathVariable final UUID id,
            @Valid @RequestBody final CreatePatientRequest request) {
        return ResponseEntity.ok(patientService.updatePatient(id, request));
    }

    /**
     * Soft deletes a patient.
     *
     * @param id the patient UUID
     * @return HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate (soft delete) a patient")
    public ResponseEntity<Void> deletePatient(@PathVariable final UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Returns the demographic summary for a patient.
     *
     * @param id the patient UUID
     * @return the demographic summary DTO
     */
    @GetMapping("/{id}/summary")
    @Operation(summary = "Get patient demographic summary")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PatientDemographicSummaryDto> getDemographicSummary(
            @PathVariable final UUID id) {
        return ResponseEntity.ok(patientService.getDemographicSummary(id));
    }

    /**
     * Creates a new mother record.
     *
     * @param request the mother creation request
     * @return the created mother with HTTP 201
     */
    @PostMapping("/mothers")
    @Operation(summary = "Register a mother record")
    public ResponseEntity<MotherDto> createMother(
            @Valid @RequestBody final CreateMotherRequest request) {
        final MotherDto created = patientService.createMother(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a mother by ID.
     *
     * @param id the mother UUID
     * @return the mother DTO
     */
    @GetMapping("/mothers/{id}")
    @Operation(summary = "Get mother by ID")
    public ResponseEntity<MotherDto> getMother(@PathVariable final UUID id) {
        return ResponseEntity.ok(patientService.getMotherById(id));
    }

    /**
     * Returns a page of all active mothers.
     *
     * @param pageable pagination information
     * @return page of mother DTOs
     */
    @GetMapping("/mothers")
    @Operation(summary = "List all active mother records")
    public ResponseEntity<Page<MotherDto>> getActiveMothers(final Pageable pageable) {
        return ResponseEntity.ok(patientService.getActiveMothers(pageable));
    }
}
