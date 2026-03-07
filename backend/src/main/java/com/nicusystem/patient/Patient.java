package com.nicusystem.patient;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import com.nicusystem.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing a neonatal patient in the NICU system.
 */
@Entity
@Table(name = "patient")
@Getter
@Setter
@NoArgsConstructor
public class Patient extends BaseEntity {

    /** Medical Record Number (MRN). */
    @Column(name = "mrn", nullable = false, unique = true)
    private String mrn;

    /** First name of the patient. */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /** Last name of the patient. */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /** Biological sex. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    /** Date and time of birth. */
    @Column(name = "date_of_birth", nullable = false)
    private Instant dateOfBirth;

    /** Birth weight in grams. */
    @Column(name = "birth_weight_grams")
    private Integer birthWeightGrams;

    /** Birth length in centimeters. */
    @Column(name = "birth_length_cm")
    private Double birthLengthCm;

    /** Head circumference in centimeters at birth. */
    @Column(name = "head_circumference_cm")
    private Double headCircumferenceCm;

    /** Gestational age in completed weeks. */
    @Column(name = "gestational_age_weeks")
    private Integer gestationalAgeWeeks;

    /** Gestational age remaining days beyond completed weeks. */
    @Column(name = "gestational_age_days")
    private Integer gestationalAgeDays;

    /** Type of delivery. */
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type")
    private DeliveryType deliveryType;

    /** APGAR score at 1 minute. */
    @Column(name = "apgar_one_minute")
    private Integer apgarOneMinute;

    /** APGAR score at 5 minutes. */
    @Column(name = "apgar_five_minute")
    private Integer apgarFiveMinute;

    /** APGAR score at 10 minutes (optional). */
    @Column(name = "apgar_ten_minute")
    private Integer apgarTenMinute;

    /** Reference to the mother's UUID. */
    @Column(name = "mother_id")
    private UUID motherId;

    /** Whether the patient record is active (soft delete support). */
    @Column(nullable = false)
    private boolean active = true;

    /** Admission date and time. */
    @Column(name = "admission_date")
    private Instant admissionDate;

    /** Bed number or location within the NICU. */
    @Column(name = "bed_number")
    private String bedNumber;

    /** Name of the birth facility. */
    @Column(name = "birth_facility")
    private String birthFacility;

    /** Name of the referring facility. */
    @Column(name = "referring_facility")
    private String referringFacility;

    /** Free-text transport notes. */
    @Column(name = "transport_details", columnDefinition = "TEXT")
    private String transportDetails;
}
