package com.nicusystem.medication;

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
 * JPA entity representing a medication order for a neonatal patient.
 */
@Entity
@Table(name = "medication")
@Getter
@Setter
@NoArgsConstructor
public class Medication extends BaseEntity {

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** Name of the medication. */
    @Column(nullable = false)
    private String name;

    /** Numeric dosage amount. */
    @Column(nullable = false)
    private Double dosage;

    /** Unit of the dosage (e.g. mg, mL). */
    @Column(name = "dosage_unit", nullable = false)
    private String dosageUnit;

    /** Route of administration (e.g. IV, oral). */
    @Column(nullable = false)
    private String route;

    /** Frequency of administration (e.g. q6h, BID). */
    @Column(nullable = false)
    private String frequency;

    /** Current status of the medication order. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MedicationStatus status;

    /** When the medication was prescribed. */
    @Column(name = "prescribed_at")
    private Instant prescribedAt;

    /** Identifier of the prescribing provider. */
    @Column(name = "prescribed_by")
    private String prescribedBy;

    /** Patient weight in grams at time of prescription. */
    @Column(name = "weight_at_prescription")
    private Integer weightAtPrescription;

    /** Optional clinical notes about the medication. */
    @Column(columnDefinition = "TEXT")
    private String notes;

    /** Whether this medication is flagged as high-alert. */
    @Column(name = "high_alert", nullable = false)
    private boolean highAlert;

    /** Maximum allowed dose in mg/kg/day. */
    @Column(name = "max_dose_mg_kg_per_day")
    private Double maxDoseMgKgPerDay;

    /** Renal dose adjustment factor (0.0-1.0). */
    @Column(name = "renal_adjustment_factor")
    private Double renalAdjustmentFactor;

    /** Hepatic dose adjustment factor (0.0-1.0). */
    @Column(name = "hepatic_adjustment_factor")
    private Double hepaticAdjustmentFactor;
}
