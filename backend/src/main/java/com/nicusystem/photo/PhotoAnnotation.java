package com.nicusystem.photo;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import com.nicusystem.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing an annotation on a patient photo.
 *
 * <p>Stores graphical annotations such as text labels, arrows, circles,
 * rectangles, and freehand drawings that clinicians place on patient photos
 * to highlight clinical observations.</p>
 */
@Entity
@Table(name = "photo_annotation")
@Getter
@Setter
@NoArgsConstructor
public class PhotoAnnotation extends BaseEntity {

    /** Reference to the patient photo this annotation belongs to. */
    @Column(name = "photo_id", nullable = false)
    private UUID photoId;

    /** Type of the annotation. */
    @Enumerated(EnumType.STRING)
    @Column(name = "annotation_type", nullable = false)
    private AnnotationType annotationType;

    /** Text content of the annotation (e.g. label text). */
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    /** X-axis coordinate of the annotation position. */
    @Column(name = "x_coordinate", nullable = false)
    private Double xCoordinate;

    /** Y-axis coordinate of the annotation position. */
    @Column(name = "y_coordinate", nullable = false)
    private Double yCoordinate;

    /** Width of the annotation in pixels. */
    @Column(name = "width")
    private Double width;

    /** Height of the annotation in pixels. */
    @Column(name = "height")
    private Double height;

    /** Hex color code for the annotation (e.g. #FF0000). */
    @Column(name = "color", length = 20)
    private String color;

    /** Identifier of the person who created the annotation. */
    @Column(name = "annotated_by", nullable = false)
    private String annotatedBy;
}
