package com.nicusystem.waveform;

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
 * REST controller for real-time waveform data management.
 */
@RestController
@RequestMapping("/api/v1/waveforms")
@RequiredArgsConstructor
@Tag(name = "Waveform Data", description = "Real-time waveform display (ECG, pulse oximetry, respiratory)")
public class WaveformDataController {

    private final WaveformDataService waveformDataService;

    /**
     * Creates a new waveform data record.
     *
     * @param request the waveform data creation request
     * @return the created waveform data with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create new waveform data")
    public ResponseEntity<WaveformDataDto> create(
            @Valid @RequestBody final CreateWaveformDataRequest request) {
        final WaveformDataDto created = waveformDataService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves waveform data by ID.
     *
     * @param id the waveform data UUID
     * @return the waveform data DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get waveform data by ID")
    public ResponseEntity<WaveformDataDto> getById(@PathVariable final UUID id) {
        return ResponseEntity.ok(waveformDataService.getById(id));
    }

    /**
     * Gets waveform data for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of waveform data
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get waveform data for a patient")
    public ResponseEntity<Page<WaveformDataDto>> getByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(
                waveformDataService.getByPatient(patientId, pageable));
    }

    /**
     * Gets waveform data for a patient filtered by type.
     *
     * @param patientId the patient UUID
     * @param type      the waveform type
     * @param pageable  pagination information
     * @return page of waveform data
     */
    @GetMapping("/patient/{patientId}/type/{type}")
    @Operation(summary = "Get waveform data for a patient by type")
    public ResponseEntity<Page<WaveformDataDto>> getByPatientAndType(
            @PathVariable final UUID patientId,
            @PathVariable final WaveformType type,
            final Pageable pageable) {
        return ResponseEntity.ok(
                waveformDataService.getByPatientAndType(
                        patientId, type, pageable));
    }

    /**
     * Gets waveform data for a patient within a time range.
     *
     * @param patientId the patient UUID
     * @param start     the start of the time range
     * @param end       the end of the time range
     * @return list of waveform data ordered by start time ascending
     */
    @GetMapping("/patient/{patientId}/range")
    @Operation(summary = "Get waveform data for a patient within a time range")
    public ResponseEntity<List<WaveformDataDto>> getByPatientAndTimeRange(
            @PathVariable final UUID patientId,
            @RequestParam final Instant start,
            @RequestParam final Instant end) {
        return ResponseEntity.ok(
                waveformDataService.getByPatientAndTimeRange(
                        patientId, start, end));
    }
}
