package com.nicusystem.nirs;

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
 * REST controller for managing NIRS cerebral oxygenation readings.
 *
 * <p>Provides endpoints to create and retrieve near-infrared spectroscopy readings
 * for neonatal patients, including filtering by site and time range.</p>
 */
@RestController
@RequestMapping("/api/v1/nirs")
@RequiredArgsConstructor
@Tag(name = "NIRS Readings", description = "Near-infrared spectroscopy cerebral oxygenation tracking")
public class NirsReadingController {

    private final NirsReadingService nirsReadingService;

    /**
     * Creates a new NIRS reading.
     *
     * @param request the NIRS reading data
     * @return the created reading with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new NIRS reading")
    public ResponseEntity<NirsReadingDto> create(
            @Valid @RequestBody final CreateNirsReadingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(nirsReadingService.create(request));
    }

    /**
     * Retrieves a NIRS reading by its ID.
     *
     * @param id the NIRS reading UUID
     * @return the NIRS reading DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a NIRS reading by ID")
    public ResponseEntity<NirsReadingDto> getById(@PathVariable final UUID id) {
        return ResponseEntity.ok(nirsReadingService.getById(id));
    }

    /**
     * Returns paginated NIRS readings for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of NIRS reading DTOs
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get NIRS readings for a patient")
    public ResponseEntity<Page<NirsReadingDto>> getByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(nirsReadingService.getByPatient(patientId, pageable));
    }

    /**
     * Returns paginated NIRS readings for a patient filtered by sensor site.
     *
     * @param patientId the patient UUID
     * @param site      the NIRS sensor site
     * @param pageable  pagination information
     * @return page of NIRS reading DTOs filtered by site
     */
    @GetMapping("/patient/{patientId}/site/{site}")
    @Operation(summary = "Get NIRS readings for a patient by site")
    public ResponseEntity<Page<NirsReadingDto>> getByPatientAndSite(
            @PathVariable final UUID patientId,
            @PathVariable final NirsSite site,
            final Pageable pageable) {
        return ResponseEntity.ok(
                nirsReadingService.getByPatientAndSite(patientId, site, pageable));
    }

    /**
     * Returns NIRS readings for a patient within a time range.
     *
     * @param patientId the patient UUID
     * @param start     the start of the time range
     * @param end       the end of the time range
     * @return list of NIRS reading DTOs within the time range
     */
    @GetMapping("/patient/{patientId}/range")
    @Operation(summary = "Get NIRS readings for a patient within a time range")
    public ResponseEntity<List<NirsReadingDto>> getByPatientAndTimeRange(
            @PathVariable final UUID patientId,
            @RequestParam final Instant start,
            @RequestParam final Instant end) {
        return ResponseEntity.ok(
                nirsReadingService.getByPatientAndTimeRange(patientId, start, end));
    }
}
