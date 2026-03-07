package com.nicusystem.photo;

import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing patient photos.
 *
 * <p>Provides endpoints to create, retrieve, download, and delete photos
 * captured for neonatal patients in the NICU.</p>
 */
@RestController
@RequestMapping("/api/v1/patient-photos")
@RequiredArgsConstructor
@Tag(name = "Patient Photos", description = "Patient photo capture and storage")
public class PatientPhotoController {

    private final PatientPhotoService patientPhotoService;

    /**
     * Creates a new patient photo.
     *
     * @param request the photo data
     * @return the created photo metadata with HTTP 201
     */
    @PostMapping
    @Operation(summary = "Upload a new patient photo")
    public ResponseEntity<PatientPhotoDto> createPhoto(
            @Valid @RequestBody final CreatePatientPhotoRequest request) {
        final PatientPhotoDto created = patientPhotoService.createPhoto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves photo metadata by its ID.
     *
     * @param id the photo UUID
     * @return the photo metadata DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get photo metadata by ID")
    public ResponseEntity<PatientPhotoDto> getPhoto(@PathVariable final UUID id) {
        return ResponseEntity.ok(patientPhotoService.getPhotoById(id));
    }

    /**
     * Downloads the raw image binary data for a photo.
     *
     * @param id the photo UUID
     * @return the image bytes with appropriate content-type header
     */
    @GetMapping("/{id}/data")
    @Operation(summary = "Download photo binary data")
    public ResponseEntity<byte[]> getPhotoData(@PathVariable final UUID id) {
        final PatientPhotoDto photo = patientPhotoService.getPhotoById(id);
        final byte[] data = patientPhotoService.getPhotoData(id);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(photo.contentType()));
        headers.setContentDisposition(
                ContentDisposition.attachment().filename(photo.fileName()).build());

        return ResponseEntity.ok().headers(headers).body(data);
    }

    /**
     * Returns paginated photos for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of photo metadata DTOs
     */
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get photos for a patient")
    public ResponseEntity<Page<PatientPhotoDto>> getPhotosByPatient(
            @PathVariable final UUID patientId, final Pageable pageable) {
        return ResponseEntity.ok(
                patientPhotoService.getPhotosByPatient(patientId, pageable));
    }

    /**
     * Deletes a patient photo by its ID.
     *
     * @param id the photo UUID
     * @return HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient photo")
    public ResponseEntity<Void> deletePhoto(@PathVariable final UUID id) {
        patientPhotoService.deletePhoto(id);
        return ResponseEntity.noContent().build();
    }
}
