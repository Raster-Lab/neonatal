package com.nicusystem.respiratory;

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
 * JPA entity representing a respiratory support record for a neonatal patient.
 *
 * <p>Captures ventilator and respiratory support settings at a point in time,
 * including mode, pressures, flow and FiO2 parameters.</p>
 */
@Entity
@Table(name = "respiratory_record")
@Getter
@Setter
@NoArgsConstructor
public class RespiratoryRecord extends BaseEntity {

    /** Reference to the patient receiving respiratory support. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The respiratory support mode in use at the time of recording. */
    @Enumerated(EnumType.STRING)
    @Column(name = "support_mode", nullable = false)
    private RespiratorySupport supportMode;

    /** Fraction of inspired oxygen as a percentage (21–100). */
    @Column(name = "fio2_percent")
    private Double fio2Percent;

    /** Positive end-expiratory pressure in cmH2O. */
    @Column(name = "peep")
    private Double peep;

    /** Peak inspiratory pressure in cmH2O. */
    @Column(name = "pip")
    private Double pip;

    /** Set ventilator rate in breaths per minute. */
    @Column(name = "rate_per_min")
    private Integer ratePerMin;

    /** Inspiratory time in seconds. */
    @Column(name = "ti_seconds")
    private Double tiSeconds;

    /** Mean airway pressure in cmH2O. */
    @Column(name = "map_cmh2o")
    private Double mapCmh2o;

    /** Gas flow in litres per minute. */
    @Column(name = "flow_lpm")
    private Double flowLpm;

    /** Timestamp when these respiratory parameters were recorded. */
    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    /** Name or identifier of the clinician who recorded the values. */
    @Column(name = "recorded_by")
    private String recordedBy;

    /** Additional clinical notes about the respiratory support. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
