package com.nicusystem.fluid;

import java.time.Instant;
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
 * REST controller for fluid balance and intake/output tracking.
 */
@RestController
@RequestMapping("/api/v1/fluid")
@RequiredArgsConstructor
@Tag(name = "Fluid Balance", description = "Fluid intake and output tracking")
public class FluidBalanceController {

    private final FluidBalanceService fluidBalanceService;

    /**
     * Records a new fluid entry.
     *
     * @param request the fluid entry data
     * @return the recorded fluid entry with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Record a new fluid entry")
    public ResponseEntity<FluidEntryDto> recordFluidEntry(
            @Valid @RequestBody final CreateFluidEntryRequest request) {
        final FluidEntryDto created = fluidBalanceService.recordFluidEntry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a fluid entry by ID.
     *
     * @param id the fluid entry UUID
     * @return the fluid entry DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get fluid entry by ID")
    public ResponseEntity<FluidEntryDto> getFluidEntryById(@PathVariable final UUID id) {
        return ResponseEntity.ok(fluidBalanceService.getFluidEntryById(id));
    }

    /**
     * Gets fluid entries for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of fluid entries
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get fluid entries for a patient")
    public ResponseEntity<Page<FluidEntryDto>> getFluidEntriesByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(
                fluidBalanceService.getFluidEntriesByPatient(patientId, pageable));
    }

    /**
     * Gets fluid balance summary for a patient over a time period.
     *
     * @param patientId    the patient UUID
     * @param start        start of the period (ISO-8601)
     * @param end          end of the period (ISO-8601)
     * @param weightGrams  patient weight in grams (optional)
     * @return fluid balance summary
     */
    @GetMapping("/patient/{patientId}/summary")
    @Operation(summary = "Get fluid balance summary for a patient")
    public ResponseEntity<FluidBalanceSummaryDto> getFluidBalanceSummary(
            @PathVariable final UUID patientId,
            @RequestParam final Instant start,
            @RequestParam final Instant end,
            @RequestParam(required = false) final Integer weightGrams) {
        return ResponseEntity.ok(
                fluidBalanceService.getFluidBalanceSummary(
                        patientId, start, end, weightGrams));
    }
}
