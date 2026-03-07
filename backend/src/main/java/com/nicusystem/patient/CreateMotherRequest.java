package com.nicusystem.patient;

import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for creating a new mother record.
 *
 * @param firstName    first name
 * @param lastName     last name
 * @param bloodType    blood type
 * @param rhFactor     Rh factor
 * @param prenatalCare prenatal care summary
 * @param medications  medications during pregnancy
 * @param infections   infections during pregnancy
 */
public record CreateMotherRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        String bloodType,
        String rhFactor,
        String prenatalCare,
        String medications,
        String infections
) {
}
