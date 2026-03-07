package com.nicusystem.infection;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO representing an infection surveillance record.
 *
 * @param id               record UUID
 * @param patientId        patient UUID
 * @param surveillanceType type of infection event
 * @param eventDate        date of the event
 * @param confirmed        whether infection was confirmed
 * @param pathogen         organism identified
 * @param antibioticDays   total antibiotic days
 * @param centralLineDays  central line days (for CLABSI)
 * @param ventilatorDays   ventilator days (for VAE)
 * @param notes            clinical notes
 * @param createdAt        creation timestamp
 * @param updatedAt        last update timestamp
 */
public record InfectionSurveillanceRecordDto(
        UUID id,
        UUID patientId,
        InfectionSurveillanceType surveillanceType,
        Instant eventDate,
        boolean confirmed,
        String pathogen,
        Integer antibioticDays,
        Integer centralLineDays,
        Integer ventilatorDays,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
}
