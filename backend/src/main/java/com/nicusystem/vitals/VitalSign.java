package com.nicusystem.vitals;

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
 * JPA entity representing a vital sign measurement for a neonatal patient.
 */
@Entity
@Table(name = "vital_sign")
@Getter
@Setter
@NoArgsConstructor
public class VitalSign extends BaseEntity {

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The type of vital sign measured. */
    @Enumerated(EnumType.STRING)
    @Column(name = "vital_type", nullable = false)
    private VitalSignType vitalType;

    /** The numeric value of the measurement. */
    @Column(name = "\"value\"", nullable = false)
    private Double value;

    /** The unit of measurement. */
    @Column(nullable = false)
    private String unit;

    /** When the measurement was recorded. */
    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    /** Temperature measurement site (only for temperature type). */
    @Enumerated(EnumType.STRING)
    @Column(name = "temperature_site")
    private TemperatureSite temperatureSite;

    /** Whether this was a manual entry vs. device-generated. */
    @Column(name = "manual_entry", nullable = false)
    private boolean manualEntry;

    /** Optional notes about the measurement. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
