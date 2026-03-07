package com.nicusystem.medication;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for Medication entity.
 *
 * @param id                   unique identifier
 * @param patientId            reference to the patient
 * @param name                 medication name
 * @param dosage               numeric dosage amount
 * @param dosageUnit           unit of the dosage
 * @param route                route of administration
 * @param frequency            frequency of administration
 * @param status               current medication status
 * @param prescribedAt         when the medication was prescribed
 * @param prescribedBy         prescribing provider identifier
 * @param weightAtPrescription patient weight in grams at prescription
 * @param notes                optional clinical notes
 * @param highAlert            whether flagged as high-alert
 * @param maxDoseMgKgPerDay       maximum allowed dose in mg/kg/day
 * @param renalAdjustmentFactor   renal dose adjustment factor (0.0-1.0)
 * @param hepaticAdjustmentFactor hepatic dose adjustment factor (0.0-1.0)
 */
public record MedicationDto(
        UUID id,
        UUID patientId,
        String name,
        Double dosage,
        String dosageUnit,
        String route,
        String frequency,
        MedicationStatus status,
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
