package com.nicusystem.photo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link PhotoAnnotation} entities.
 */
public interface PhotoAnnotationRepository extends JpaRepository<PhotoAnnotation, UUID> {

    /**
     * Returns annotations for the given photo ordered by creation time ascending.
     *
     * @param photoId the photo identifier
     * @return list of annotations ordered by creation time
     */
    List<PhotoAnnotation> findByPhotoIdOrderByCreatedAtAsc(UUID photoId);

    /**
     * Returns annotations for the given photo filtered by annotation type.
     *
     * @param photoId the photo identifier
     * @param annotationType the annotation type filter
     * @return list of matching annotations
     */
    List<PhotoAnnotation> findByPhotoIdAndAnnotationType(UUID photoId, AnnotationType annotationType);
}
