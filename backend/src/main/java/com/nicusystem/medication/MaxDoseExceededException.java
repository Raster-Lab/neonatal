package com.nicusystem.medication;

/**
 * Exception thrown when a prescribed dose exceeds the maximum allowed dose.
 */
public class MaxDoseExceededException extends RuntimeException {

    /**
     * Constructs a new MaxDoseExceededException with the specified message.
     *
     * @param message the detail message
     */
    public MaxDoseExceededException(final String message) {
        super(message);
    }
}
