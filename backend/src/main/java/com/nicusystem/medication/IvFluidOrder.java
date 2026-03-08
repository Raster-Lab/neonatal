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
 * Entity representing an IV fluid order for a neonatal patient.
 */
@Entity
@Table(name = "iv_fluid_order")
@Getter
@Setter
@NoArgsConstructor
public class IvFluidOrder extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "fluid_type", nullable = false)
    private String fluidType;

    @Column(name = "base_solution")
    private String baseSolution;

    @Column(name = "concentration")
    private Double concentration;

    @Column(name = "concentration_unit")
    private String concentrationUnit;

    @Column(name = "rate", nullable = false)
    private Double rate;

    @Column(name = "rate_unit", nullable = false)
    private String rateUnit;

    @Column(name = "total_volume")
    private Double totalVolume;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private IvFluidStatus status;

    @Column(name = "ordered_by")
    private String orderedBy;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
