package com.nicusystem.patient;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request payload for creating a new patient.
 *
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
 * @param apgarOneMinute        APGAR score at 1 minute (0-10)
 * @param apgarFiveMinute       APGAR score at 5 minutes (0-10)
 * @param apgarTenMinute        APGAR score at 10 minutes (0-10, optional)
 * @param motherId              reference to the mother
 * @param admissionDate         admission date and time
 * @param bedNumber             bed number or location
 */
public record CreatePatientRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull Gender gender,
        @NotNull Instant dateOfBirth,
        Integer birthWeightGrams,
        Double birthLengthCm,
        Double headCircumferenceCm,
        @Min(22) @Max(44) Integer gestationalAgeWeeks,
        @Min(0) @Max(6) Integer gestationalAgeDays,
        DeliveryType deliveryType,
        @Min(0) @Max(10) Integer apgarOneMinute,
        @Min(0) @Max(10) Integer apgarFiveMinute,
        @Min(0) @Max(10) Integer apgarTenMinute,
        UUID motherId,
        Instant admissionDate,
        String bedNumber
) {
}
