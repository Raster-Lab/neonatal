package com.nicusystem.handoff;

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
 * REST controller for shift handoff report management.
 */
@RestController
@RequestMapping("/api/v1/handoffs")
@RequiredArgsConstructor
@Tag(name = "Handoffs", description = "I-PASS and SBAR shift handoff reports")
public class ShiftHandoffController {

    private final ShiftHandoffService handoffService;

    /**
     * Creates a new shift handoff report.
     *
     * @param request the handoff data
     * @return the created handoff with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new shift handoff report")
    public ResponseEntity<ShiftHandoffDto> createHandoff(
            @Valid @RequestBody final CreateShiftHandoffRequest request) {
        final ShiftHandoffDto created = handoffService.createHandoff(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a shift handoff report by ID.
     *
     * @param id the handoff UUID
     * @return the handoff DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get shift handoff by ID")
    public ResponseEntity<ShiftHandoffDto> getById(@PathVariable final UUID id) {
        return ResponseEntity.ok(handoffService.getById(id));
    }

    /**
     * Gets handoff reports for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of handoff DTOs
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get handoff reports for a patient")
    public ResponseEntity<Page<ShiftHandoffDto>> getByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(handoffService.getByPatient(patientId, pageable));
    }
}
