package com.nicusystem.medication;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for drug allergy management.
 */
@RestController
@RequestMapping("/api/v1/drug-allergies")
@RequiredArgsConstructor
@Tag(name = "Drug Allergies",
        description = "Drug allergy management")
public class DrugAllergyController {

    private final DrugAllergyService drugAllergyService;

    /**
     * Creates a new drug allergy record.
     *
     * @param request the creation request
     * @return the created allergy with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new drug allergy record")
    public ResponseEntity<DrugAllergyDto> createAllergy(
            @Valid @RequestBody
            final CreateDrugAllergyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(drugAllergyService.createAllergy(request));
    }

    /**
     * Retrieves a drug allergy by ID.
     *
     * @param id the allergy UUID
     * @return the allergy DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get drug allergy by ID")
    public ResponseEntity<DrugAllergyDto> getAllergy(
            @PathVariable final UUID id) {
        return ResponseEntity.ok(
                drugAllergyService.getAllergyById(id));
    }

    /**
     * Returns all allergies for a patient with pagination.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of drug allergies
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get drug allergies for a patient")
    public ResponseEntity<Page<DrugAllergyDto>> getAllergiesByPatient(
            @PathVariable final UUID patientId,
            final Pageable pageable) {
        return ResponseEntity.ok(
                drugAllergyService.getAllergiesByPatient(
                        patientId, pageable));
    }

    /**
     * Checks whether a patient has an allergy conflicting with a
     * medication. Returns 200 OK if no conflict, or throws
     * DrugAllergyException (409 CONFLICT) if a match is found.
     *
     * @param patientId      the patient UUID
     * @param medicationName the medication name to check
     * @return empty 200 OK response if no allergy conflict
     */
    @PostMapping("/check")
    @Operation(summary = "Check for drug allergy conflicts")
    public ResponseEntity<Void> checkAllergy(
            @RequestParam final UUID patientId,
            @RequestParam final String medicationName) {
        drugAllergyService.checkAllergyForMedication(
                patientId, medicationName);
        return ResponseEntity.ok().build();
    }

    /**
     * Soft-deletes a drug allergy.
     *
     * @param id the allergy UUID
     * @return HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Soft-delete a drug allergy")
    public ResponseEntity<Void> deleteAllergy(
            @PathVariable final UUID id) {
        drugAllergyService.deleteAllergy(id);
        return ResponseEntity.noContent().build();
    }
}
