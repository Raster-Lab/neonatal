package com.nicusystem.insurance;

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
 * JPA entity representing patient insurance information in the NICU.
 */
@Entity
@Table(name = "patient_insurance")
@Getter
@Setter
@NoArgsConstructor
public class PatientInsurance extends BaseEntity {

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The type of insurance coverage. */
    @Enumerated(EnumType.STRING)
    @Column(name = "insurance_type", nullable = false)
    private InsuranceType insuranceType;

    /** Name of the insurance company. */
    @Column(name = "insurer_name", nullable = false)
    private String insurerName;

    /** Insurance policy number. */
    @Column(name = "policy_number", nullable = false)
    private String policyNumber;

    /** Insurance group number. */
    @Column(name = "group_number")
    private String groupNumber;

    /** Name of the insurance subscriber. */
    @Column(name = "subscriber_name")
    private String subscriberName;

    /** Date of birth of the insurance subscriber. */
    @Column(name = "subscriber_dob")
    private Instant subscriberDob;

    /** Relationship of the subscriber to the patient. */
    @Column(name = "relationship_to_patient")
    private String relationshipToPatient;

    /** Date when coverage becomes effective. */
    @Column(name = "effective_date")
    private Instant effectiveDate;

    /** Date when coverage terminates. */
    @Column(name = "termination_date")
    private Instant terminationDate;

    /** Optional notes about the insurance. */
    @Column(columnDefinition = "TEXT")
    private String notes;

    /** Whether this insurance record is active (soft delete). */
    @Column(nullable = false)
    private boolean active = true;
}
