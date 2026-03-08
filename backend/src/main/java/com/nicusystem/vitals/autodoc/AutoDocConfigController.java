package com.nicusystem.vitals.autodoc;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for automated vital signs documentation configuration.
 */
@RestController
@RequestMapping("/api/v1/vitals/auto-doc")
@RequiredArgsConstructor
@Tag(name = "Vital Signs Auto-Doc", description = "Automated vital signs documentation configuration")
public class AutoDocConfigController {

    private final AutoDocConfigService autoDocConfigService;

    /**
     * Creates a new auto-doc configuration.
     *
     * @param request the configuration data
     * @return the created configuration with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new auto-doc configuration")
    public ResponseEntity<AutoDocConfigDto> create(
            @Valid @RequestBody final CreateAutoDocConfigRequest request) {
        final AutoDocConfigDto created = autoDocConfigService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves an auto-doc configuration by ID.
     *
     * @param id the configuration UUID
     * @return the configuration DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get auto-doc configuration by ID")
    public ResponseEntity<AutoDocConfigDto> getById(@PathVariable final UUID id) {
        return ResponseEntity.ok(autoDocConfigService.getById(id));
    }

    /**
     * Gets auto-doc configurations for a patient with pagination.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of configurations
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get auto-doc configurations for a patient")
    public ResponseEntity<Page<AutoDocConfigDto>> getByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(autoDocConfigService.getByPatient(patientId, pageable));
    }

    /**
     * Gets enabled auto-doc configurations for a patient.
     *
     * @param patientId the patient UUID
     * @return list of enabled configurations
     */
    @GetMapping("/patient/{patientId}/enabled")
    @Operation(summary = "Get enabled auto-doc configurations for a patient")
    public ResponseEntity<List<AutoDocConfigDto>> getEnabledByPatient(
            @PathVariable final UUID patientId) {
        return ResponseEntity.ok(autoDocConfigService.getEnabledByPatient(patientId));
    }

    /**
     * Toggles the enabled state of an auto-doc configuration.
     *
     * @param id      the configuration UUID
     * @param enabled the new enabled state
     * @return the updated configuration
     */
    @PatchMapping("/{id}/toggle")
    @Operation(summary = "Toggle enabled state of auto-doc configuration")
    public ResponseEntity<AutoDocConfigDto> toggleEnabled(
            @PathVariable final UUID id,
            @RequestParam final boolean enabled) {
        return ResponseEntity.ok(autoDocConfigService.toggleEnabled(id, enabled));
    }
}
