package com.nicusystem.insurance;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating or updating a patient insurance record.
 *
 * @param patientId             reference to the patient
 * @param insuranceType         type of insurance coverage
 * @param insurerName           name of the insurance company
 * @param policyNumber          insurance policy number
 * @param groupNumber           insurance group number
 * @param subscriberName        name of the subscriber
 * @param subscriberDob         date of birth of the subscriber
 * @param relationshipToPatient relationship of subscriber to patient
 * @param effectiveDate         coverage effective date
 * @param terminationDate       coverage termination date
 * @param notes                 optional notes
 */
public record CreatePatientInsuranceRequest(
        @NotNull UUID patientId,
        @NotNull InsuranceType insuranceType,
        @NotNull String insurerName,
        @NotNull String policyNumber,
        String groupNumber,
        String subscriberName,
        Instant subscriberDob,
        String relationshipToPatient,
        Instant effectiveDate,
        Instant terminationDate,
        String notes
) {
}
