package com.nicusystem.assessment;

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
 * REST controller for neonatal assessment management.
 */
@RestController
@RequestMapping("/api/v1/assessments")
@RequiredArgsConstructor
@Tag(name = "Assessments", description = "Neonatal body-system assessments")
public class NeonatalAssessmentController {

    private final NeonatalAssessmentService assessmentService;

    /**
     * Creates a new neonatal assessment.
     *
     * @param request the assessment data
     * @return the created assessment with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new neonatal assessment")
    public ResponseEntity<NeonatalAssessmentDto> createAssessment(
            @Valid @RequestBody final CreateNeonatalAssessmentRequest request) {
        final NeonatalAssessmentDto created = assessmentService.createAssessment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a neonatal assessment by ID.
     *
     * @param id the assessment UUID
     * @return the assessment DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get neonatal assessment by ID")
    public ResponseEntity<NeonatalAssessmentDto> getById(@PathVariable final UUID id) {
        return ResponseEntity.ok(assessmentService.getById(id));
    }

    /**
     * Gets assessments for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of assessment DTOs
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get assessments for a patient")
    public ResponseEntity<Page<NeonatalAssessmentDto>> getByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(assessmentService.getByPatient(patientId, pageable));
    }

    /**
     * Gets assessments for a patient filtered by assessment type.
     *
     * @param patientId      the patient UUID
     * @param assessmentType the assessment type
     * @param pageable       pagination information
     * @return page of assessment DTOs
     */
    @GetMapping("/patient/{patientId}/type/{assessmentType}")
    @Operation(summary = "Get assessments for a patient by type")
    public ResponseEntity<Page<NeonatalAssessmentDto>> getByPatientAndType(
            @PathVariable final UUID patientId,
            @PathVariable final AssessmentType assessmentType,
            final Pageable pageable) {
        return ResponseEntity.ok(
                assessmentService.getByPatientAndType(patientId, assessmentType, pageable));
    }
}
