package com.nicusystem.medication;

import lombok.Getter;

/**
 * Exception thrown when a drug allergy conflict is detected.
 */
@Getter
public class DrugAllergyException extends RuntimeException {

    private final String allergenName;
    private final String medicationName;

    /**
     * Constructs a new DrugAllergyException.
     *
     * @param message        the detail message
     * @param allergenName   the name of the allergen
     * @param medicationName the name of the medication
     */
    public DrugAllergyException(final String message,
                                final String allergenName,
                                final String medicationName) {
        super(message);
        this.allergenName = allergenName;
        this.medicationName = medicationName;
    }
}
