package com.nicusystem.insurance;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for PatientInsurance entity.
 *
 * @param id                    unique identifier
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
 * @param active                whether this insurance record is active
 */
public record PatientInsuranceDto(
        UUID id,
        UUID patientId,
        InsuranceType insuranceType,
        String insurerName,
        String policyNumber,
        String groupNumber,
        String subscriberName,
        Instant subscriberDob,
        String relationshipToPatient,
        Instant effectiveDate,
        Instant terminationDate,
        String notes,
        boolean active
) {
}
