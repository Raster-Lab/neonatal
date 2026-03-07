package com.nicusystem.respiratory;

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
 * REST controller for managing apnea events.
 *
 * <p>Provides endpoints to record and retrieve apnea episodes for neonatal patients.</p>
 */
@RestController
@RequestMapping("/api/v1/apnea-events")
@RequiredArgsConstructor
@Tag(name = "Apnea Events", description = "Apnea event recording and retrieval")
public class ApneaEventController {

    private final ApneaEventService apneaEventService;

    /**
     * Records a new apnea event.
     *
     * @param request the apnea event data
     * @return the created apnea event with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Record a new apnea event")
    public ResponseEntity<ApneaEventDto> recordApneaEvent(
            @Valid @RequestBody final CreateApneaEventRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apneaEventService.recordApneaEvent(request));
    }

    /**
     * Retrieves an apnea event by its ID.
     *
     * @param id the apnea event UUID
     * @return the apnea event DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get an apnea event by ID")
    public ResponseEntity<ApneaEventDto> getApneaEventById(@PathVariable final UUID id) {
        return ResponseEntity.ok(apneaEventService.getApneaEventById(id));
    }

    /**
     * Returns paginated apnea events for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of apnea event DTOs
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get apnea events for a patient")
    public ResponseEntity<Page<ApneaEventDto>> getApneaEventsByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(
                apneaEventService.getApneaEventsByPatient(patientId, pageable));
    }

    /**
     * Returns the total count of apnea events for a patient.
     *
     * @param patientId the patient UUID
     * @return count of apnea events
     */
    @GetMapping("/patient/{patientId}/count")
    @Operation(summary = "Count apnea events for a patient")
    public ResponseEntity<Long> countApneaEventsForPatient(@PathVariable final UUID patientId) {
        return ResponseEntity.ok(apneaEventService.countApneaEventsForPatient(patientId));
    }
}
