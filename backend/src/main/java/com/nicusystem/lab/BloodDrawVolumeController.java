package com.nicusystem.lab;

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
 * REST controller for blood draw volume tracking.
 */
@RestController
@RequestMapping("/api/v1/blood-draws")
@RequiredArgsConstructor
@Tag(name = "Blood Draw Volumes", description = "Blood draw volume tracking and cumulative monitoring")
public class BloodDrawVolumeController {

    private final BloodDrawVolumeService bloodDrawVolumeService;

    /**
     * Records a new blood draw event.
     *
     * @param request the blood draw creation data
     * @return the recorded blood draw with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Record a new blood draw")
    public ResponseEntity<BloodDrawVolumeDto> recordBloodDraw(
            @Valid @RequestBody final CreateBloodDrawVolumeRequest request) {
        final BloodDrawVolumeDto created = bloodDrawVolumeService.recordBloodDraw(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a blood draw record by ID.
     *
     * @param id the blood draw record UUID
     * @return the blood draw volume DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get blood draw record by ID")
    public ResponseEntity<BloodDrawVolumeDto> getBloodDrawById(@PathVariable final UUID id) {
        return ResponseEntity.ok(bloodDrawVolumeService.getBloodDrawById(id));
    }

    /**
     * Gets all blood draw records for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of blood draw records
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get blood draw records for a patient")
    public ResponseEntity<Page<BloodDrawVolumeDto>> getBloodDrawsByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(bloodDrawVolumeService.getBloodDrawsByPatient(patientId, pageable));
    }

    /**
     * Gets the cumulative blood draw volume for a patient.
     *
     * @param patientId the patient UUID
     * @return cumulative volume drawn in microliters
     */
    @GetMapping("/patient/{patientId}/cumulative")
    @Operation(summary = "Get cumulative blood draw volume for a patient")
    public ResponseEntity<Double> getCumulativeBloodDrawVolumeUl(
            @PathVariable final UUID patientId) {
        return ResponseEntity.ok(
                bloodDrawVolumeService.getCumulativeBloodDrawVolumeUl(patientId));
    }
}
