package com.nicusystem.nirs;

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
 * JPA entity representing a near-infrared spectroscopy (NIRS) reading.
 *
 * <p>Captures regional oxygen saturation (rSO2) measurements from NIRS sensors
 * placed on various body sites of a neonatal patient.</p>
 */
@Entity
@Table(name = "nirs_reading")
@Getter
@Setter
@NoArgsConstructor
public class NirsReading extends BaseEntity {

    /** Reference to the patient being monitored. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** Body site where the NIRS sensor is placed. */
    @Enumerated(EnumType.STRING)
    @Column(name = "site", nullable = false)
    private NirsSite site;

    /** Regional oxygen saturation percentage (rSO2). */
    @Column(name = "rso2_value", nullable = false)
    private Double rso2Value;

    /** Baseline rSO2 value for comparison purposes. */
    @Column(name = "baseline")
    private Double baseline;

    /** Timestamp when the NIRS reading was recorded. */
    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    /** Identifier of the NIRS monitoring device. */
    @Column(name = "device_identifier", length = 100)
    private String deviceIdentifier;

    /** Additional clinical notes about the reading. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
