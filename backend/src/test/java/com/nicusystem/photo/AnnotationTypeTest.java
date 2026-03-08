package com.nicusystem.photo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AnnotationType} enum.
 */
class AnnotationTypeTest {

    @Test
    void shouldContainAllExpectedValues() {
        // When
        final AnnotationType[] values = AnnotationType.values();

        // Then
        assertThat(values).containsExactlyInAnyOrder(
                AnnotationType.TEXT,
                AnnotationType.ARROW,
                AnnotationType.CIRCLE,
                AnnotationType.RECTANGLE,
                AnnotationType.FREEHAND
        );
    }

    @Test
    void shouldResolveFromName() {
        // When / Then
        assertThat(AnnotationType.valueOf("TEXT")).isEqualTo(AnnotationType.TEXT);
        assertThat(AnnotationType.valueOf("ARROW")).isEqualTo(AnnotationType.ARROW);
        assertThat(AnnotationType.valueOf("CIRCLE")).isEqualTo(AnnotationType.CIRCLE);
        assertThat(AnnotationType.valueOf("RECTANGLE")).isEqualTo(AnnotationType.RECTANGLE);
        assertThat(AnnotationType.valueOf("FREEHAND")).isEqualTo(AnnotationType.FREEHAND);
    }
}
