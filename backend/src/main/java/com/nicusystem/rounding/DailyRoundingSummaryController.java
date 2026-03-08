package com.nicusystem.rounding;

import java.time.LocalDate;
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
 * REST controller for daily rounding summary management.
 */
@RestController
@RequestMapping("/api/v1/rounding-summaries")
@RequiredArgsConstructor
@Tag(name = "Daily Rounding Summary",
        description = "Daily rounding summary templates and records")
public class DailyRoundingSummaryController {

    private final DailyRoundingSummaryService
            dailyRoundingSummaryService;

    /**
     * Creates a new daily rounding summary record.
     *
     * @param request the daily rounding summary data
     * @return the created daily rounding summary with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new daily rounding summary")
    public ResponseEntity<DailyRoundingSummaryDto> create(
            @Valid @RequestBody
            final CreateDailyRoundingSummaryRequest request) {
        final DailyRoundingSummaryDto created =
                dailyRoundingSummaryService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a daily rounding summary by ID.
     *
     * @param id the daily rounding summary UUID
     * @return the daily rounding summary DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get daily rounding summary by ID")
    public ResponseEntity<DailyRoundingSummaryDto> getById(
            @PathVariable final UUID id) {
        return ResponseEntity.ok(
                dailyRoundingSummaryService.getById(id));
    }

    /**
     * Gets daily rounding summaries for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of daily rounding summary records
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get daily rounding summaries for a patient")
    public ResponseEntity<Page<DailyRoundingSummaryDto>> getByPatient(
            @PathVariable final UUID patientId,
            final Pageable pageable) {
        return ResponseEntity.ok(
                dailyRoundingSummaryService.getByPatient(
                        patientId, pageable));
    }

    /**
     * Gets a daily rounding summary for a patient on a specific date.
     *
     * @param patientId the patient UUID
     * @param date      the rounding date
     * @return the daily rounding summary DTO
     */
    @GetMapping("/patient/{patientId}/date/{date}")
    @Operation(summary = "Get daily rounding summary by patient and date")
    public ResponseEntity<DailyRoundingSummaryDto> getByPatientAndDate(
            @PathVariable final UUID patientId,
            @PathVariable final LocalDate date) {
        return ResponseEntity.ok(
                dailyRoundingSummaryService.getByPatientAndDate(
                        patientId, date));
    }
}
