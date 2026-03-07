package com.nicusystem.medication;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a new medication order.
 *
 * @param patientId            reference to the patient
 * @param name                 medication name
 * @param dosage               numeric dosage amount
 * @param dosageUnit           unit of the dosage
 * @param route                route of administration
 * @param frequency            frequency of administration
 * @param prescribedAt         when the medication was prescribed
 * @param prescribedBy         prescribing provider identifier
 * @param weightAtPrescription patient weight in grams at prescription
 * @param notes                optional clinical notes
 * @param highAlert            whether flagged as high-alert
 * @param maxDoseMgKgPerDay       maximum allowed dose in mg/kg/day
 * @param renalAdjustmentFactor   renal dose adjustment factor (0.0-1.0)
 * @param hepaticAdjustmentFactor hepatic dose adjustment factor (0.0-1.0)
 */
public record CreateMedicationRequest(
        @NotNull UUID patientId,
        @NotBlank String name,
        @NotNull Double dosage,
        @NotBlank String dosageUnit,
        @NotBlank String route,
        @NotBlank String frequency,
        Instant prescribedAt,
        String prescribedBy,
        Integer weightAtPrescription,
        String notes,
        boolean highAlert,
        Double maxDoseMgKgPerDay,
        Double renalAdjustmentFactor,
        Double hepaticAdjustmentFactor
) {
}
