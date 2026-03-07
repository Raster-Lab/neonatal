package com.nicusystem.growth;

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
 * JPA entity representing a growth measurement for a neonatal patient.
 */
@Entity
@Table(name = "growth_measurement")
@Getter
@Setter
@NoArgsConstructor
public class GrowthMeasurement extends BaseEntity {

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The type of growth measurement. */
    @Enumerated(EnumType.STRING)
    @Column(name = "measurement_type", nullable = false)
    private MeasurementType measurementType;

    /** The numeric value (weight in grams, length/HC in cm). */
    @Column(name = "\"value\"", nullable = false)
    private Double value;

    /** Calculated percentile for the measurement. */
    @Column(name = "percentile")
    private Double percentile;

    /** Calculated z-score for the measurement. */
    @Column(name = "z_score")
    private Double zScore;

    /** Corrected gestational age in weeks at time of measurement. */
    @Column(name = "corrected_age_weeks")
    private Integer correctedAgeWeeks;

    /** When the measurement was taken. */
    @Column(name = "measured_at", nullable = false)
    private Instant measuredAt;

    /** Optional notes about the measurement. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
