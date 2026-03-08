package com.nicusystem.photo;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
 * REST controller for managing photo annotations.
 */
@RestController
@RequestMapping("/api/v1/photo-annotations")
@RequiredArgsConstructor
@Tag(name = "Photo Annotations", description = "Clinical photo annotation tools")
public class PhotoAnnotationController {

    private final PhotoAnnotationService photoAnnotationService;

    /**
     * Creates a new photo annotation.
     *
     * @param request the creation request
     * @return the created annotation with 201 status
     */
    @PostMapping
    @Operation(summary = "Create a new photo annotation")
    public ResponseEntity<PhotoAnnotationDto> create(
            @Valid @RequestBody final CreatePhotoAnnotationRequest request) {
        final PhotoAnnotationDto created = photoAnnotationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a photo annotation by its ID.
     *
     * @param id the annotation identifier
     * @return the annotation DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a photo annotation by ID")
    public ResponseEntity<PhotoAnnotationDto> getById(@PathVariable final UUID id) {
        return ResponseEntity.ok(photoAnnotationService.getById(id));
    }

    /**
     * Returns all annotations for a given photo.
     *
     * @param photoId the photo identifier
     * @return list of annotations
     */
    @GetMapping("/photo/{photoId}")
    @Operation(summary = "Get annotations for a photo")
    public ResponseEntity<List<PhotoAnnotationDto>> getByPhoto(
            @PathVariable final UUID photoId) {
        return ResponseEntity.ok(photoAnnotationService.getByPhoto(photoId));
    }

    /**
     * Returns annotations for a given photo filtered by type.
     *
     * @param photoId the photo identifier
     * @param type the annotation type
     * @return list of matching annotations
     */
    @GetMapping("/photo/{photoId}/type/{type}")
    @Operation(summary = "Get annotations for a photo by type")
    public ResponseEntity<List<PhotoAnnotationDto>> getByPhotoAndType(
            @PathVariable final UUID photoId,
            @PathVariable final AnnotationType type) {
        return ResponseEntity.ok(
                photoAnnotationService.getByPhotoAndType(photoId, type));
    }

    /**
     * Deletes a photo annotation by its ID.
     *
     * @param id the annotation identifier
     * @return 204 No Content
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a photo annotation")
    public ResponseEntity<Void> delete(@PathVariable final UUID id) {
        photoAnnotationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
