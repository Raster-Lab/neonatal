package com.nicusystem.consent;

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
 * REST controller for patient consent management.
 */
@RestController
@RequestMapping("/api/v1/consents")
@RequiredArgsConstructor
@Tag(name = "Patient Consents", description = "Patient consent form management")
public class PatientConsentController {

    private final PatientConsentService patientConsentService;

    /**
     * Creates a new patient consent record.
     *
     * @param request the consent data
     * @return the created consent with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new patient consent record")
    public ResponseEntity<PatientConsentDto> createConsent(
            @Valid @RequestBody final CreatePatientConsentRequest request) {
        final PatientConsentDto created = patientConsentService.createConsent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a patient consent record by ID.
     *
     * @param id the consent UUID
     * @return the consent DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get patient consent by ID")
    public ResponseEntity<PatientConsentDto> getConsent(@PathVariable final UUID id) {
        return ResponseEntity.ok(patientConsentService.getConsentById(id));
    }

    /**
     * Gets consent records for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of consent records
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get consent records for a patient")
    public ResponseEntity<Page<PatientConsentDto>> getConsentsByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(
                patientConsentService.getConsentsByPatient(patientId, pageable));
    }

    /**
     * Gets consent records for a patient filtered by consent type.
     *
     * @param patientId the patient UUID
     * @param type      the consent type
     * @return list of consent records
     */
    @GetMapping("/patient/{patientId}/type/{type}")
    @Operation(summary = "Get consent records for a patient by type")
    public ResponseEntity<List<PatientConsentDto>> getConsentsByPatientAndType(
            @PathVariable final UUID patientId,
            @PathVariable final ConsentType type) {
        return ResponseEntity.ok(
                patientConsentService.getConsentsByPatientAndType(patientId, type));
    }

    /**
     * Updates the status of a patient consent record.
     *
     * @param id     the consent UUID
     * @param status the new consent status
     * @return the updated consent DTO
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update patient consent status")
    public ResponseEntity<PatientConsentDto> updateConsentStatus(
            @PathVariable final UUID id,
            @RequestParam final ConsentStatus status) {
        return ResponseEntity.ok(patientConsentService.updateConsentStatus(id, status));
    }
}
