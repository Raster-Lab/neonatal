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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing respiratory support records.
 *
 * <p>Provides endpoints to create and retrieve respiratory support records and
 * to calculate the oxygenation index from ventilator and blood-gas parameters.</p>
 */
@RestController
@RequestMapping("/api/v1/respiratory-records")
@RequiredArgsConstructor
@Tag(name = "Respiratory Records", description = "Respiratory support and ventilator management")
public class RespiratoryRecordController {

    private final RespiratoryRecordService respiratoryRecordService;

    /**
     * Creates a new respiratory support record.
     *
     * @param request the respiratory record data
     * @return the created record with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new respiratory support record")
    public ResponseEntity<RespiratoryRecordDto> createRecord(
            @Valid @RequestBody final CreateRespiratoryRecordRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(respiratoryRecordService.createRecord(request));
    }

    /**
     * Retrieves a respiratory record by its ID.
     *
     * @param id the respiratory record UUID
     * @return the respiratory record DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a respiratory record by ID")
    public ResponseEntity<RespiratoryRecordDto> getRecordById(@PathVariable final UUID id) {
        return ResponseEntity.ok(respiratoryRecordService.getRecordById(id));
    }

    /**
     * Returns paginated respiratory records for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of respiratory record DTOs
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get respiratory records for a patient")
    public ResponseEntity<Page<RespiratoryRecordDto>> getRecordsByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(
                respiratoryRecordService.getRecordsByPatient(patientId, pageable));
    }

    /**
     * Returns paginated respiratory records for a patient ordered most recent first.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of respiratory record DTOs ordered by recorded time descending
     */
    @GetMapping("/patient/{patientId}/latest")
    @Operation(summary = "Get latest respiratory records for a patient")
    public ResponseEntity<Page<RespiratoryRecordDto>> getLatestRecordsByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(
                respiratoryRecordService.getLatestRecordsByPatient(patientId, pageable));
    }

    /**
     * Calculates the oxygenation index from provided ventilator and blood-gas parameters.
     *
     * @param fio2 fraction of inspired oxygen percentage (21–100)
     * @param map  mean airway pressure in cmH2O
     * @param pao2 arterial partial pressure of oxygen in mmHg
     * @return oxygenation metrics DTO containing the calculated OI value
     */
    @GetMapping("/oi")
    @Operation(summary = "Calculate the oxygenation index (OI)")
    public ResponseEntity<OxygenationMetricsDto> calculateOxygenationIndex(
            @RequestParam final Double fio2,
            @RequestParam final Double map,
            @RequestParam final Double pao2) {
        final Double oiValue = respiratoryRecordService.calculateOxygenationIndex(fio2, map, pao2);
        return ResponseEntity.ok(new OxygenationMetricsDto(oiValue, null, null));
    }
}
