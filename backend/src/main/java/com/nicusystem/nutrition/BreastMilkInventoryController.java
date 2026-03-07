package com.nicusystem.nutrition;

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
 * REST controller for managing breast milk inventory.
 *
 * <p>Provides endpoints to record, retrieve, and filter breast milk inventory entries
 * for neonatal patients.</p>
 */
@RestController
@RequestMapping("/api/v1/breast-milk")
@RequiredArgsConstructor
@Tag(name = "Breast Milk Inventory", description = "Breast milk inventory management")
public class BreastMilkInventoryController {

    private final BreastMilkInventoryService breastMilkInventoryService;

    /**
     * Creates a new breast milk inventory entry.
     *
     * @param request the inventory entry data
     * @return the created entry with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a breast milk inventory entry")
    public ResponseEntity<BreastMilkInventoryDto> createEntry(
            @Valid @RequestBody final CreateBreastMilkInventoryRequest request) {
        final BreastMilkInventoryDto created = breastMilkInventoryService.createEntry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a breast milk inventory entry by ID.
     *
     * @param id the inventory entry UUID
     * @return the inventory entry DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a breast milk inventory entry by ID")
    public ResponseEntity<BreastMilkInventoryDto> getById(@PathVariable final UUID id) {
        return ResponseEntity.ok(breastMilkInventoryService.getById(id));
    }

    /**
     * Returns paginated breast milk inventory entries for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of inventory entry DTOs
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get breast milk inventory for a patient")
    public ResponseEntity<Page<BreastMilkInventoryDto>> getByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(
                breastMilkInventoryService.getByPatient(patientId, pageable));
    }

    /**
     * Returns paginated breast milk inventory entries for a patient filtered by donor milk status.
     *
     * @param patientId the patient UUID
     * @param donorMilk {@code true} to return donor milk entries; {@code false} for own milk
     * @param pageable  pagination information
     * @return page of inventory entry DTOs
     */
    @GetMapping("/patient/{patientId}/donor/{donorMilk}")
    @Operation(summary = "Get breast milk inventory for a patient filtered by donor status")
    public ResponseEntity<Page<BreastMilkInventoryDto>> getByPatientAndDonorMilk(
            @PathVariable final UUID patientId,
            @PathVariable final boolean donorMilk,
            final Pageable pageable) {
        return ResponseEntity.ok(
                breastMilkInventoryService.getByPatientAndDonorMilk(
                        patientId, donorMilk, pageable));
    }
}
