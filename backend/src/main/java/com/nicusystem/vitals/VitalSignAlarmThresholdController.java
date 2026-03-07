package com.nicusystem.vitals;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for vital sign alarm threshold management.
 */
@RestController
@RequestMapping("/api/v1/vitals/alarm-thresholds")
@RequiredArgsConstructor
@Tag(name = "Vital Sign Alarm Thresholds", description = "Vital sign alarm threshold configuration")
public class VitalSignAlarmThresholdController {

    private final VitalSignAlarmThresholdService vitalSignAlarmThresholdService;

    /**
     * Creates a new vital sign alarm threshold.
     *
     * @param request the threshold data
     * @return the created threshold with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new vital sign alarm threshold")
    public ResponseEntity<VitalSignAlarmThresholdDto> createThreshold(
            @Valid @RequestBody final CreateVitalSignAlarmThresholdRequest request) {
        final VitalSignAlarmThresholdDto created =
                vitalSignAlarmThresholdService.createThreshold(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a vital sign alarm threshold by ID.
     *
     * @param id the threshold UUID
     * @return the threshold DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get vital sign alarm threshold by ID")
    public ResponseEntity<VitalSignAlarmThresholdDto> getThreshold(
            @PathVariable final UUID id) {
        return ResponseEntity.ok(vitalSignAlarmThresholdService.getThresholdById(id));
    }

    /**
     * Gets all active vital sign alarm thresholds with pagination.
     *
     * @param pageable pagination information
     * @return page of active thresholds
     */
    @GetMapping
    @Operation(summary = "Get all active vital sign alarm thresholds")
    public ResponseEntity<Page<VitalSignAlarmThresholdDto>> getAllActiveThresholds(
            final Pageable pageable) {
        return ResponseEntity.ok(
                vitalSignAlarmThresholdService.getAllActiveThresholds(pageable));
    }

    /**
     * Gets active alarm thresholds filtered by vital sign type.
     *
     * @param vitalType the vital sign type
     * @return list of matching thresholds
     */
    @GetMapping("/vital-type/{vitalType}")
    @Operation(summary = "Get alarm thresholds by vital sign type")
    public ResponseEntity<List<VitalSignAlarmThresholdDto>> getThresholdsByVitalType(
            @PathVariable final VitalSignType vitalType) {
        return ResponseEntity.ok(
                vitalSignAlarmThresholdService.getThresholdsByVitalType(vitalType));
    }

    /**
     * Updates a vital sign alarm threshold.
     *
     * @param id      the threshold UUID
     * @param request the updated threshold data
     * @return the updated threshold DTO
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a vital sign alarm threshold")
    public ResponseEntity<VitalSignAlarmThresholdDto> updateThreshold(
            @PathVariable final UUID id,
            @Valid @RequestBody final CreateVitalSignAlarmThresholdRequest request) {
        return ResponseEntity.ok(
                vitalSignAlarmThresholdService.updateThreshold(id, request));
    }

    /**
     * Deactivates a vital sign alarm threshold.
     *
     * @param id the threshold UUID
     * @return the deactivated threshold DTO
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate a vital sign alarm threshold")
    public ResponseEntity<VitalSignAlarmThresholdDto> deactivateThreshold(
            @PathVariable final UUID id) {
        return ResponseEntity.ok(
                vitalSignAlarmThresholdService.deactivateThreshold(id));
    }
}
