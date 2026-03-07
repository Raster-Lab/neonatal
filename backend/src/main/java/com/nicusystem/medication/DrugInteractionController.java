package com.nicusystem.medication;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for drug-drug interaction management.
 */
@RestController
@RequestMapping("/api/v1/drug-interactions")
@RequiredArgsConstructor
@Tag(name = "Drug Interactions", description = "Drug-drug interaction management")
public class DrugInteractionController {

    private final DrugInteractionService drugInteractionService;

    /**
     * Creates a new drug interaction record.
     *
     * @param request the creation request
     * @return the created interaction with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new drug interaction record")
    public ResponseEntity<DrugInteractionDto> createInteraction(
            @Valid @RequestBody final CreateDrugInteractionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(drugInteractionService.createInteraction(request));
    }

    /**
     * Retrieves a drug interaction by ID.
     *
     * @param id the interaction UUID
     * @return the interaction DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get drug interaction by ID")
    public ResponseEntity<DrugInteractionDto> getInteraction(
            @PathVariable final UUID id) {
        return ResponseEntity.ok(drugInteractionService.getInteractionById(id));
    }

    /**
     * Returns all active drug interactions with pagination.
     *
     * @param pageable pagination information
     * @return page of drug interactions
     */
    @GetMapping
    @Operation(summary = "List all active drug interactions")
    public ResponseEntity<Page<DrugInteractionDto>> getInteractions(
            final Pageable pageable) {
        return ResponseEntity.ok(drugInteractionService.getInteractions(pageable));
    }

    /**
     * Soft-deletes a drug interaction.
     *
     * @param id the interaction UUID
     * @return HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Soft-delete a drug interaction")
    public ResponseEntity<Void> deleteInteraction(@PathVariable final UUID id) {
        drugInteractionService.deleteInteraction(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Checks for known interactions among the given list of medication names.
     *
     * @param medicationNames list of medication names
     * @return list of detected interactions
     */
    @PostMapping("/check")
    @Operation(summary = "Check for drug interactions among a list of medications")
    public ResponseEntity<List<DrugInteractionDto>> checkInteractions(
            @RequestBody final List<String> medicationNames) {
        return ResponseEntity.ok(
                drugInteractionService.checkInteractions(medicationNames));
    }
}
