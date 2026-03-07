package com.nicusystem.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ResourceNotFoundException.
 */
class ResourceNotFoundExceptionTest {

    @Test
    void constructor_shouldSetFieldsAndMessage() {
        // Given / When
        final ResourceNotFoundException exception =
                new ResourceNotFoundException("Patient", "123");

        // Then
        assertThat(exception.getResourceType()).isEqualTo("Patient");
        assertThat(exception.getResourceId()).isEqualTo("123");
        assertThat(exception.getMessage()).isEqualTo("Patient not found with id: 123");
    }

    @Test
    void shouldExtendRuntimeException() {
        // Given / When
        final ResourceNotFoundException exception =
                new ResourceNotFoundException("Encounter", "456");

        // Then
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }
}
