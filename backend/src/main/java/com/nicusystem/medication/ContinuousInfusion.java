package com.nicusystem.medication;

import com.nicusystem.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * Entity representing a continuous infusion for a neonatal patient.
 */
@Entity
@Table(name = "continuous_infusion")
@Getter
@Setter
@NoArgsConstructor
public class ContinuousInfusion extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "drug_name", nullable = false)
    private String drugName;

    @Column(name = "concentration")
    private Double concentration;

    @Column(name = "concentration_unit")
    private String concentrationUnit;

    @Column(name = "rate", nullable = false)
    private Double rate;

    @Column(name = "rate_unit", nullable = false)
    private String rateUnit;

    @Column(name = "dose_per_kg_per_min")
    private Double dosePerKgPerMin;

    @Column(name = "weight_grams")
    private Integer weightGrams;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InfusionStatus status;

    @Column(name = "ordered_by")
    private String orderedBy;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
