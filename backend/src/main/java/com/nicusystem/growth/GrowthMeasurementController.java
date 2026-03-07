package com.nicusystem.growth;

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
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for growth measurement tracking.
 */
@RestController
@RequestMapping("/api/v1/growth")
@RequiredArgsConstructor
@Tag(name = "Growth Measurements", description = "Growth and developmental tracking")
public class GrowthMeasurementController {

    private final GrowthMeasurementService growthMeasurementService;

    /**
     * Records a new growth measurement.
     *
     * @param request the growth measurement data
     * @return the recorded growth measurement with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Record a new growth measurement")
    public ResponseEntity<GrowthMeasurementDto> recordMeasurement(
            @Valid @RequestBody final CreateGrowthMeasurementRequest request) {
        final GrowthMeasurementDto created =
                growthMeasurementService.recordMeasurement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a growth measurement by ID.
     *
     * @param id the growth measurement UUID
     * @return the growth measurement DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get growth measurement by ID")
    public ResponseEntity<GrowthMeasurementDto> getMeasurement(
            @PathVariable final UUID id) {
        return ResponseEntity.ok(growthMeasurementService.getMeasurementById(id));
    }

    /**
     * Gets growth measurements for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of growth measurements
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get growth measurements for a patient")
    public ResponseEntity<Page<GrowthMeasurementDto>> getMeasurementsByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(
                growthMeasurementService.getMeasurementsByPatient(
                        patientId, pageable));
    }

    /**
     * Gets growth measurements for a patient filtered by type.
     *
     * @param patientId the patient UUID
     * @param type      the measurement type
     * @param pageable  pagination information
     * @return page of growth measurements
     */
    @GetMapping("/patient/{patientId}/type/{type}")
    @Operation(summary = "Get growth measurements for a patient by type")
    public ResponseEntity<Page<GrowthMeasurementDto>> getMeasurementsByPatientAndType(
            @PathVariable final UUID patientId,
            @PathVariable final MeasurementType type,
            final Pageable pageable) {
        return ResponseEntity.ok(
                growthMeasurementService.getMeasurementsByPatientAndType(
                        patientId, type, pageable));
    }

    /**
     * Gets growth trend data for charting (ascending order).
     *
     * @param patientId the patient UUID
     * @param type      the measurement type
     * @return list of growth measurements ordered ascending
     */
    @GetMapping("/patient/{patientId}/type/{type}/trend")
    @Operation(summary = "Get growth trend for a patient by type")
    public ResponseEntity<List<GrowthMeasurementDto>> getGrowthTrend(
            @PathVariable final UUID patientId,
            @PathVariable final MeasurementType type) {
        return ResponseEntity.ok(
                growthMeasurementService.getGrowthTrend(patientId, type));
    }
}
