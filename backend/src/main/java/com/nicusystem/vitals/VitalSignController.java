package com.nicusystem.vitals;

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
 * REST controller for vital signs monitoring.
 */
@RestController
@RequestMapping("/api/v1/vitals")
@RequiredArgsConstructor
@Tag(name = "Vital Signs", description = "Real-time vital signs monitoring")
public class VitalSignController {

    private final VitalSignService vitalSignService;

    /**
     * Records a new vital sign measurement.
     *
     * @param request the vital sign data
     * @return the recorded vital sign with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Record a new vital sign measurement")
    public ResponseEntity<VitalSignDto> recordVitalSign(
            @Valid @RequestBody final CreateVitalSignRequest request) {
        final VitalSignDto created = vitalSignService.recordVitalSign(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a vital sign by ID.
     *
     * @param id the vital sign UUID
     * @return the vital sign DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get vital sign by ID")
    public ResponseEntity<VitalSignDto> getVitalSign(@PathVariable final UUID id) {
        return ResponseEntity.ok(vitalSignService.getVitalSignById(id));
    }

    /**
     * Gets vital signs for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of vital signs
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get vital signs for a patient")
    public ResponseEntity<Page<VitalSignDto>> getVitalSignsByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(
                vitalSignService.getVitalSignsByPatient(patientId, pageable));
    }

    /**
     * Gets vital signs for a patient filtered by type.
     *
     * @param patientId the patient UUID
     * @param type      the vital sign type
     * @param pageable  pagination information
     * @return page of vital signs
     */
    @GetMapping("/patient/{patientId}/type/{type}")
    @Operation(summary = "Get vital signs for a patient by type")
    public ResponseEntity<Page<VitalSignDto>> getVitalSignsByPatientAndType(
            @PathVariable final UUID patientId,
            @PathVariable final VitalSignType type,
            final Pageable pageable) {
        return ResponseEntity.ok(
                vitalSignService.getVitalSignsByPatientAndType(
                        patientId, type, pageable));
    }

    /**
     * Gets vital signs for a patient within a time range.
     *
     * @param patientId the patient UUID
     * @param start     the start time
     * @param end       the end time
     * @return list of vital signs
     */
    @GetMapping("/patient/{patientId}/range")
    @Operation(summary = "Get vital signs for a patient within time range")
    public ResponseEntity<List<VitalSignDto>> getVitalSignsByTimeRange(
            @PathVariable final UUID patientId,
            @RequestParam final Instant start,
            @RequestParam final Instant end) {
        return ResponseEntity.ok(
                vitalSignService.getVitalSignsByPatientAndTimeRange(
                        patientId, start, end));
    }
}
