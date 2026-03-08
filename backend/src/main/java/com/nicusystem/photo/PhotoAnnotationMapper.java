package com.nicusystem.photo;

import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between {@link PhotoAnnotation} entities and DTOs.
 */
@Component
public class PhotoAnnotationMapper {

    /**
     * Converts a {@link PhotoAnnotation} entity to a {@link PhotoAnnotationDto}.
     *
     * @param entity the photo annotation entity
     * @return the corresponding DTO
     */
    public PhotoAnnotationDto toDto(final PhotoAnnotation entity) {
        return new PhotoAnnotationDto(
                entity.getId(),
                entity.getPhotoId(),
                entity.getAnnotationType(),
                entity.getContent(),
                entity.getXCoordinate(),
                entity.getYCoordinate(),
                entity.getWidth(),
                entity.getHeight(),
                entity.getColor(),
                entity.getAnnotatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a {@link CreatePhotoAnnotationRequest} to a new {@link PhotoAnnotation} entity.
     *
     * @param request the creation request
     * @return a new photo annotation entity
     */
    public PhotoAnnotation toEntity(final CreatePhotoAnnotationRequest request) {
        final PhotoAnnotation entity = new PhotoAnnotation();
        entity.setPhotoId(request.photoId());
        entity.setAnnotationType(request.annotationType());
        entity.setContent(request.content());
        entity.setXCoordinate(request.xCoordinate());
        entity.setYCoordinate(request.yCoordinate());
        entity.setWidth(request.width());
        entity.setHeight(request.height());
        entity.setColor(request.color());
        entity.setAnnotatedBy(request.annotatedBy());
        return entity;
    }
}
