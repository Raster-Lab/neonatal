package com.nicusystem.patient;

import java.util.UUID;

/**
 * Data transfer object for Mother entity.
 *
 * @param id           unique identifier
 * @param firstName    first name
 * @param lastName     last name
 * @param bloodType    blood type
 * @param rhFactor     Rh factor
 * @param prenatalCare prenatal care summary
 * @param medications  medications during pregnancy
 * @param infections   infections during pregnancy
 * @param active       whether the record is active
 */
public record MotherDto(
        UUID id,
        String firstName,
        String lastName,
        String bloodType,
        String rhFactor,
        String prenatalCare,
        String medications,
        String infections,
        boolean active
) {
}
