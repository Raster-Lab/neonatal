package com.nicusystem.flowsheet;

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
 * REST controller for flowsheet entry management.
 */
@RestController
@RequestMapping("/api/v1/flowsheet-entries")
@RequiredArgsConstructor
@Tag(name = "Flowsheet Entries",
        description = "Hourly documentation for vitals, I/O, "
                + "assessments, and interventions")
public class FlowsheetEntryController {

    private final FlowsheetEntryService flowsheetEntryService;

    /**
     * Creates a new flowsheet entry.
     *
     * @param request the flowsheet entry data
     * @return the created flowsheet entry with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new flowsheet entry")
    public ResponseEntity<FlowsheetEntryDto> create(
            @Valid @RequestBody
            final CreateFlowsheetEntryRequest request) {
        final FlowsheetEntryDto created =
                flowsheetEntryService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a flowsheet entry by ID.
     *
     * @param id the flowsheet entry UUID
     * @return the flowsheet entry DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get flowsheet entry by ID")
    public ResponseEntity<FlowsheetEntryDto> getById(
            @PathVariable final UUID id) {
        return ResponseEntity.ok(flowsheetEntryService.getById(id));
    }

    /**
     * Gets flowsheet entries for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of flowsheet entry records
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get flowsheet entries for a patient")
    public ResponseEntity<Page<FlowsheetEntryDto>> getByPatient(
            @PathVariable final UUID patientId,
            final Pageable pageable) {
        return ResponseEntity.ok(
                flowsheetEntryService.getByPatient(
                        patientId, pageable));
    }

    /**
     * Gets flowsheet entries for a patient filtered by category.
     *
     * @param patientId the patient UUID
     * @param category  the flowsheet category
     * @param pageable  pagination information
     * @return page of flowsheet entry records
     */
    @GetMapping("/patient/{patientId}/category/{category}")
    @Operation(summary = "Get flowsheet entries by patient and category")
    public ResponseEntity<Page<FlowsheetEntryDto>>
            getByPatientAndCategory(
                    @PathVariable final UUID patientId,
                    @PathVariable final FlowsheetCategory category,
                    final Pageable pageable) {
        return ResponseEntity.ok(
                flowsheetEntryService.getByPatientAndCategory(
                        patientId, category, pageable));
    }

    /**
     * Gets flowsheet entries for a patient within a time range.
     *
     * @param patientId the patient UUID
     * @param start     start of the time range
     * @param end       end of the time range
     * @return list of flowsheet entry records
     */
    @GetMapping("/patient/{patientId}/range")
    @Operation(summary = "Get flowsheet entries by patient and time range")
    public ResponseEntity<List<FlowsheetEntryDto>>
            getByPatientAndTimeRange(
                    @PathVariable final UUID patientId,
                    @RequestParam final Instant start,
                    @RequestParam final Instant end) {
        return ResponseEntity.ok(
                flowsheetEntryService.getByPatientAndTimeRange(
                        patientId, start, end));
    }
}
