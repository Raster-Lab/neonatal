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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for infection surveillance management.
 */
@RestController
@RequestMapping("/api/v1/infection-surveillance")
@RequiredArgsConstructor
@Tag(name = "Infection Surveillance", description = "Infection control and sepsis surveillance")
public class InfectionSurveillanceRecordController {

    private final InfectionSurveillanceRecordService service;

    /**
     * Creates a new infection surveillance record.
     *
     * @param request the creation request
     * @return the created record with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new infection surveillance record")
    public ResponseEntity<InfectionSurveillanceRecordDto> createRecord(
            @Valid @RequestBody final CreateInfectionSurveillanceRecordRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createRecord(request));
    }

    /**
     * Retrieves an infection surveillance record by ID.
     *
     * @param id the record UUID
     * @return the record DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get infection surveillance record by ID")
    public ResponseEntity<InfectionSurveillanceRecordDto> getRecord(
            @PathVariable final UUID id) {
        return ResponseEntity.ok(service.getRecordById(id));
    }

    /**
     * Gets all infection surveillance records for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination info
     * @return page of records
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get infection surveillance records for a patient")
    public ResponseEntity<Page<InfectionSurveillanceRecordDto>> getRecordsByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(service.getRecordsByPatient(patientId, pageable));
    }

    /**
     * Gets infection surveillance records for a patient filtered by type.
     *
     * @param patientId        the patient UUID
     * @param surveillanceType the surveillance type
     * @param pageable         pagination info
     * @return page of records
     */
    @GetMapping("/patient/{patientId}/type/{surveillanceType}")
    @Operation(summary = "Get infection surveillance records by patient and type")
    public ResponseEntity<Page<InfectionSurveillanceRecordDto>> getRecordsByPatientAndType(
            @PathVariable final UUID patientId,
            @PathVariable final InfectionSurveillanceType surveillanceType,
            final Pageable pageable) {
        return ResponseEntity.ok(
                service.getRecordsByPatientAndType(patientId, surveillanceType, pageable));
    }
}
