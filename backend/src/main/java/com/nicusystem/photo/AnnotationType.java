package com.nicusystem.photo;

/**
 * Enumeration of annotation types for clinical photography.
 *
 * <p>Each value identifies a type of graphical annotation that can be placed
 * on a patient photo to highlight clinical observations.</p>
 */
public enum AnnotationType {

    /** Free-form text label annotation. */
    TEXT,

    /** Arrow pointing to a region of interest. */
    ARROW,

    /** Circle highlighting a region of interest. */
    CIRCLE,

    /** Rectangle highlighting a region of interest. */
    RECTANGLE,

    /** Freehand drawing annotation. */
    FREEHAND
}
