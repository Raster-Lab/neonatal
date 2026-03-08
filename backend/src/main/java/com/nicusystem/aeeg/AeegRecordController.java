package com.nicusystem.aeeg;

import java.time.Instant;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing amplitude-integrated EEG (aEEG) records.
 *
 * <p>Provides endpoints to create and retrieve aEEG monitoring records
 * for neonatal patients, including seizure detection queries.</p>
 */
@RestController
@RequestMapping("/api/v1/aeeg")
@RequiredArgsConstructor
@Tag(name = "aEEG Records", description = "Amplitude-integrated EEG data capture and display")
public class AeegRecordController {

    private final AeegRecordService aeegRecordService;

    /**
     * Creates a new aEEG record.
     *
     * @param request the aEEG record data
     * @return the created record with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new aEEG record")
    public ResponseEntity<AeegRecordDto> create(
            @Valid @RequestBody final CreateAeegRecordRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(aeegRecordService.create(request));
    }

    /**
     * Retrieves an aEEG record by its ID.
     *
     * @param id the aEEG record UUID
     * @return the aEEG record DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get an aEEG record by ID")
    public ResponseEntity<AeegRecordDto> getById(@PathVariable final UUID id) {
        return ResponseEntity.ok(aeegRecordService.getById(id));
    }

    /**
     * Returns paginated aEEG records for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of aEEG record DTOs
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get aEEG records for a patient")
    public ResponseEntity<Page<AeegRecordDto>> getByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(aeegRecordService.getByPatient(patientId, pageable));
    }

    /**
     * Returns aEEG records with seizure detected for a patient.
     *
     * @param patientId the patient UUID
     * @return list of aEEG record DTOs with seizure detected
     */
    @GetMapping("/patient/{patientId}/seizures")
    @Operation(summary = "Get aEEG records with seizure detected for a patient")
    public ResponseEntity<List<AeegRecordDto>> getSeizuresByPatient(
            @PathVariable final UUID patientId) {
        return ResponseEntity.ok(aeegRecordService.getSeizuresByPatient(patientId));
    }

    /**
     * Returns aEEG records for a patient within a time range.
     *
     * @param patientId the patient UUID
     * @param start     the start of the time range
     * @param end       the end of the time range
     * @return list of aEEG record DTOs within the time range
     */
    @GetMapping("/patient/{patientId}/range")
    @Operation(summary = "Get aEEG records for a patient within a time range")
    public ResponseEntity<List<AeegRecordDto>> getByPatientAndTimeRange(
            @PathVariable final UUID patientId,
            @RequestParam final Instant start,
            @RequestParam final Instant end) {
        return ResponseEntity.ok(
                aeegRecordService.getByPatientAndTimeRange(patientId, start, end));
    }
}
