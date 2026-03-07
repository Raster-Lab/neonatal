package com.nicusystem.medication;

/**
 * Exception thrown when a contraindicated drug interaction is detected.
 */
public class DrugInteractionException extends RuntimeException {

    /**
     * Constructs a new DrugInteractionException with the specified message.
     *
     * @param message the detail message
     */
    public DrugInteractionException(final String message) {
        super(message);
    }
}
