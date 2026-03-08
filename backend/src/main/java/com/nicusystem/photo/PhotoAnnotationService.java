package com.nicusystem.photo;

import java.util.List;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing photo annotations in the NICU clinical photography system.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PhotoAnnotationService {

    private final PhotoAnnotationRepository photoAnnotationRepository;
    private final PhotoAnnotationMapper photoAnnotationMapper;

    /**
     * Creates a new photo annotation.
     *
     * @param request the creation request
     * @return the created annotation DTO
     */
    @Transactional
    public PhotoAnnotationDto create(final CreatePhotoAnnotationRequest request) {
        final PhotoAnnotation entity = photoAnnotationMapper.toEntity(request);
        final PhotoAnnotation saved = photoAnnotationRepository.save(entity);
        log.info("Photo annotation created: type={}, photoId={}",
                request.annotationType(), request.photoId());
        return photoAnnotationMapper.toDto(saved);
    }

    /**
     * Retrieves a photo annotation by its unique identifier.
     *
     * @param id the annotation identifier
     * @return the annotation DTO
     */
    @Transactional(readOnly = true)
    public PhotoAnnotationDto getById(final UUID id) {
        return photoAnnotationRepository.findById(id)
                .map(photoAnnotationMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "PhotoAnnotation", id.toString()));
    }

    /**
     * Returns all annotations for a given photo.
     *
     * @param photoId the photo identifier
     * @return list of annotation DTOs ordered by creation time
     */
    @Transactional(readOnly = true)
    public List<PhotoAnnotationDto> getByPhoto(final UUID photoId) {
        return photoAnnotationRepository.findByPhotoIdOrderByCreatedAtAsc(photoId)
                .stream()
                .map(photoAnnotationMapper::toDto)
                .toList();
    }

    /**
     * Returns annotations for a given photo filtered by type.
     *
     * @param photoId the photo identifier
     * @param annotationType the annotation type filter
     * @return list of matching annotation DTOs
     */
    @Transactional(readOnly = true)
    public List<PhotoAnnotationDto> getByPhotoAndType(final UUID photoId,
                                                      final AnnotationType annotationType) {
        return photoAnnotationRepository
                .findByPhotoIdAndAnnotationType(photoId, annotationType)
                .stream()
                .map(photoAnnotationMapper::toDto)
                .toList();
    }

    /**
     * Deletes a photo annotation by its unique identifier.
     *
     * @param id the annotation identifier
     */
    @Transactional
    public void delete(final UUID id) {
        final PhotoAnnotation entity = photoAnnotationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "PhotoAnnotation", id.toString()));
        photoAnnotationRepository.delete(entity);
        log.info("Photo annotation deleted: id={}", id);
    }
}
