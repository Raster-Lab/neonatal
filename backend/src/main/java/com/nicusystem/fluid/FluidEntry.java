package com.nicusystem.fluid;

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
 * JPA entity representing a fluid intake or output entry for a neonatal patient.
 */
@Entity
@Table(name = "fluid_entry")
@Getter
@Setter
@NoArgsConstructor
public class FluidEntry extends BaseEntity {

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The type of fluid entry (intake or output). */
    @Enumerated(EnumType.STRING)
    @Column(name = "entry_type", nullable = false)
    private FluidEntryType entryType;

    /** The category of fluid. */
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private FluidCategory category;

    /** Volume in milliliters. */
    @Column(name = "volume_ml", nullable = false)
    private Double volumeMl;

    /** Optional description. */
    @Column(name = "description")
    private String description;

    /** When the fluid entry was recorded. */
    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    /** Who recorded the entry. */
    @Column(name = "recorded_by")
    private String recordedBy;
}
