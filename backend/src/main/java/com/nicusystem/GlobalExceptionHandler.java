package com.nicusystem;

import java.net.URI;

import com.nicusystem.common.ResourceNotFoundException;
import com.nicusystem.medication.DrugInteractionException;
import com.nicusystem.medication.MaxDoseExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler providing RFC 7807 Problem Detail responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResourceNotFoundException.
     *
     * @param ex the exception
     * @return a 404 ProblemDetail
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(final ResourceNotFoundException ex) {
        final ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setType(URI.create("https://api.nicusystem.com/errors/not-found"));
        problemDetail.setProperty("resourceType", ex.getResourceType());
        problemDetail.setProperty("resourceId", ex.getResourceId());
        return problemDetail;
    }

    /**
     * Handles validation errors.
     *
     * @param ex the exception
     * @return a 400 ProblemDetail
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(final MethodArgumentNotValidException ex) {
        final ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
        problemDetail.setTitle("Validation Error");
        problemDetail.setType(URI.create("https://api.nicusystem.com/errors/validation"));
        return problemDetail;
    }

    /**
     * Handles access denied errors.
     *
     * @param ex the exception
     * @return a 403 ProblemDetail
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDenied(final AccessDeniedException ex) {
        final ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Access denied");
        problemDetail.setTitle("Forbidden");
        problemDetail.setType(URI.create("https://api.nicusystem.com/errors/forbidden"));
        return problemDetail;
    }

    /**
     * Handles MaxDoseExceededException.
     *
     * @param ex the exception
     * @return a 422 ProblemDetail
     */
    @ExceptionHandler(MaxDoseExceededException.class)
    public ProblemDetail handleMaxDoseExceeded(final MaxDoseExceededException ex) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        problemDetail.setTitle("Dose Limit Exceeded");
        problemDetail.setType(URI.create("https://api.nicusystem.com/errors/dose-limit-exceeded"));
        return problemDetail;
    }

    /**
     * Handles DrugInteractionException.
     *
     * @param ex the exception
     * @return a 409 ProblemDetail
     */
    @ExceptionHandler(DrugInteractionException.class)
    public ProblemDetail handleDrugInteraction(final DrugInteractionException ex) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Drug Interaction Detected");
        problemDetail.setType(URI.create("https://api.nicusystem.com/errors/drug-interaction"));
        return problemDetail;
    }

    /**
     * Handles all uncaught exceptions.
     *
     * @param ex the exception
     * @return a 500 ProblemDetail
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(final Exception ex) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("https://api.nicusystem.com/errors/internal"));
        return problemDetail;
    }
}
