package com.nicusystem.patient;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for Patient entity.
 *
 * @param id                    unique identifier
 * @param mrn                   medical record number
 * @param firstName             first name
 * @param lastName              last name
 * @param gender                biological sex
 * @param dateOfBirth           date and time of birth
 * @param birthWeightGrams      birth weight in grams
 * @param birthLengthCm         birth length in centimeters
 * @param headCircumferenceCm   head circumference in centimeters
 * @param gestationalAgeWeeks   gestational age in completed weeks
 * @param gestationalAgeDays    gestational age remaining days
 * @param deliveryType          type of delivery
 * @param apgarOneMinute        APGAR score at 1 minute
 * @param apgarFiveMinute       APGAR score at 5 minutes
 * @param apgarTenMinute        APGAR score at 10 minutes
 * @param motherId              reference to the mother
 * @param active                whether the record is active
 * @param admissionDate         admission date and time
 * @param bedNumber             bed number or location
 * @param birthFacility         birth facility name
 * @param referringFacility     referring facility name
 * @param transportDetails      free-text transport notes
 */
public record PatientDto(
        UUID id,
        String mrn,
        String firstName,
        String lastName,
        Gender gender,
        Instant dateOfBirth,
        Integer birthWeightGrams,
        Double birthLengthCm,
        Double headCircumferenceCm,
        Integer gestationalAgeWeeks,
        Integer gestationalAgeDays,
        DeliveryType deliveryType,
        Integer apgarOneMinute,
        Integer apgarFiveMinute,
        Integer apgarTenMinute,
        UUID motherId,
        boolean active,
        Instant admissionDate,
        String bedNumber,
        String birthFacility,
        String referringFacility,
        String transportDetails
) {
}
