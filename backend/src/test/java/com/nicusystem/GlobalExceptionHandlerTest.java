package com.nicusystem;

import java.net.URI;

import com.nicusystem.common.ResourceNotFoundException;
import com.nicusystem.medication.DrugAllergyException;
import com.nicusystem.medication.DrugInteractionException;
import com.nicusystem.medication.MaxDoseExceededException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for GlobalExceptionHandler.
 */
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleResourceNotFound_shouldReturn404() {
        // Given
        final ResourceNotFoundException ex =
                new ResourceNotFoundException("Patient", "123");

        // When
        final ProblemDetail result = handler.handleResourceNotFound(ex);

        // Then
        assertThat(result.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result.getTitle()).isEqualTo("Resource Not Found");
        assertThat(result.getDetail()).isEqualTo("Patient not found with id: 123");
        assertThat(result.getType()).isEqualTo(URI.create("https://api.nicusystem.com/errors/not-found"));
        assertThat(result.getProperties()).containsEntry("resourceType", "Patient");
        assertThat(result.getProperties()).containsEntry("resourceId", "123");
    }

    @Test
    void handleValidationException_shouldReturn400() {
        // Given
        final MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);

        // When
        final ProblemDetail result = handler.handleValidationException(ex);

        // Then
        assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(result.getTitle()).isEqualTo("Validation Error");
        assertThat(result.getType()).isEqualTo(URI.create("https://api.nicusystem.com/errors/validation"));
    }

    @Test
    void handleAccessDenied_shouldReturn403() {
        // Given
        final AccessDeniedException ex = new AccessDeniedException("Forbidden");

        // When
        final ProblemDetail result = handler.handleAccessDenied(ex);

        // Then
        assertThat(result.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(result.getTitle()).isEqualTo("Forbidden");
        assertThat(result.getType()).isEqualTo(URI.create("https://api.nicusystem.com/errors/forbidden"));
    }

    @Test
    void handleGenericException_shouldReturn500() {
        // Given
        final Exception ex = new RuntimeException("Something went wrong");

        // When
        final ProblemDetail result = handler.handleGenericException(ex);

        // Then
        assertThat(result.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(result.getTitle()).isEqualTo("Internal Server Error");
        assertThat(result.getType()).isEqualTo(URI.create("https://api.nicusystem.com/errors/internal"));
    }

    @Test
    void handleMaxDoseExceeded_shouldReturn422() {
        // Given
        final MaxDoseExceededException ex = new MaxDoseExceededException(
                "Dose 50.00 mg/kg exceeds maximum allowed dose of 30.00 mg/kg for medication Gentamicin");

        // When
        final ProblemDetail result = handler.handleMaxDoseExceeded(ex);

        // Then
        assertThat(result.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
        assertThat(result.getTitle()).isEqualTo("Dose Limit Exceeded");
        assertThat(result.getType()).isEqualTo(URI.create("https://api.nicusystem.com/errors/dose-limit-exceeded"));
        assertThat(result.getDetail()).contains("Gentamicin");
    }

    @Test
    void handleDrugAllergy_shouldReturn409() {
        // Given
        final DrugAllergyException ex = new DrugAllergyException(
                "Patient has allergy to Penicillin which conflicts with medication Amoxicillin",
                "Penicillin", "Amoxicillin");

        // When
        final ProblemDetail result = handler.handleDrugAllergy(ex);

        // Then
        assertThat(result.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(result.getTitle()).isEqualTo("Drug Allergy Conflict");
        assertThat(result.getType()).isEqualTo(URI.create("https://api.nicusystem.com/errors/drug-allergy"));
        assertThat(result.getDetail()).contains("Penicillin");
        assertThat(result.getProperties()).containsEntry("allergenName", "Penicillin");
        assertThat(result.getProperties()).containsEntry("medicationName", "Amoxicillin");
    }

    @Test
    void handleDrugInteraction_shouldReturn409() {
        // Given
        final DrugInteractionException ex = new DrugInteractionException(
                "Contraindicated drug interaction between Warfarin and Aspirin: Bleeding risk");

        // When
        final ProblemDetail result = handler.handleDrugInteraction(ex);

        // Then
        assertThat(result.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(result.getTitle()).isEqualTo("Drug Interaction Detected");
        assertThat(result.getType()).isEqualTo(URI.create("https://api.nicusystem.com/errors/drug-interaction"));
        assertThat(result.getDetail()).contains("Warfarin");
    }
}
