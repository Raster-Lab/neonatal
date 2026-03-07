package com.nicusystem.infection;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating an infection surveillance record.
 *
 * @param patientId        patient UUID
 * @param surveillanceType type of surveillance event
 * @param eventDate        date of the event
 * @param confirmed        whether infection was confirmed
 * @param pathogen         organism identified
 * @param antibioticDays   total antibiotic days
 * @param centralLineDays  central line days (for CLABSI)
 * @param ventilatorDays   ventilator days (for VAE)
 * @param notes            optional clinical notes
 */
public record CreateInfectionSurveillanceRecordRequest(
        @NotNull UUID patientId,
        @NotNull InfectionSurveillanceType surveillanceType,
        @NotNull Instant eventDate,
        boolean confirmed,
        String pathogen,
        Integer antibioticDays,
        Integer centralLineDays,
        Integer ventilatorDays,
        String notes
) {
}
