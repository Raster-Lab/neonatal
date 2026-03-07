package com.nicusystem.clinical;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for clinical note management.
 */
@RestController
@RequestMapping("/api/v1/clinical-notes")
@RequiredArgsConstructor
@Tag(name = "Clinical Notes", description = "SOAP progress notes and clinical documentation")
public class ClinicalNoteController {

    private final ClinicalNoteService clinicalNoteService;

    /**
     * Creates a new clinical note.
     *
     * @param request the clinical note data
     * @return the created clinical note with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new clinical note")
    public ResponseEntity<ClinicalNoteDto> createNote(
            @Valid @RequestBody final CreateClinicalNoteRequest request) {
        final ClinicalNoteDto created = clinicalNoteService.createNote(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a clinical note by ID.
     *
     * @param id the clinical note UUID
     * @return the clinical note DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get clinical note by ID")
    public ResponseEntity<ClinicalNoteDto> getNoteById(@PathVariable final UUID id) {
        return ResponseEntity.ok(clinicalNoteService.getNoteById(id));
    }

    /**
     * Gets clinical notes for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of clinical notes
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get clinical notes for a patient")
    public ResponseEntity<Page<ClinicalNoteDto>> getNotesByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(
                clinicalNoteService.getNotesByPatient(patientId, pageable));
    }

    /**
     * Gets clinical notes for a patient filtered by note type.
     *
     * @param patientId the patient UUID
     * @param type      the note type
     * @param pageable  pagination information
     * @return page of clinical notes
     */
    @GetMapping("/patient/{patientId}/type/{type}")
    @Operation(summary = "Get clinical notes for a patient by type")
    public ResponseEntity<Page<ClinicalNoteDto>> getNotesByPatientAndType(
            @PathVariable final UUID patientId,
            @PathVariable final NoteType type,
            final Pageable pageable) {
        return ResponseEntity.ok(
                clinicalNoteService.getNotesByPatientAndType(patientId, type, pageable));
    }

    /**
     * Adds a co-signature to a clinical note.
     *
     * @param id         the clinical note UUID
     * @param coSignerId the co-signer's identifier
     * @return the updated clinical note DTO
     */
    @PutMapping("/{id}/co-sign")
    @Operation(summary = "Add co-signature to a clinical note")
    public ResponseEntity<ClinicalNoteDto> addCoSignature(
            @PathVariable final UUID id,
            @RequestParam final String coSignerId) {
        return ResponseEntity.ok(clinicalNoteService.addCoSignature(id, coSignerId));
    }
}
