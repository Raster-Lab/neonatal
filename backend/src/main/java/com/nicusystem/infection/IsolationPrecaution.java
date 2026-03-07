package com.nicusystem.infection;

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
 * JPA entity representing an isolation precaution applied to a neonatal patient.
 */
@Entity
@Table(name = "isolation_precaution")
@Getter
@Setter
@NoArgsConstructor
public class IsolationPrecaution extends BaseEntity {

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** Type of isolation precaution. */
    @Enumerated(EnumType.STRING)
    @Column(name = "precaution_type", nullable = false)
    private IsolationPrecautionType precautionType;

    /** When the precaution was initiated. */
    @Column(name = "initiated_at", nullable = false)
    private Instant initiatedAt;

    /** When the precaution was discontinued (null if still active). */
    @Column(name = "discontinued_at")
    private Instant discontinuedAt;

    /** Provider who initiated the precaution. */
    @Column(name = "initiated_by")
    private String initiatedBy;

    /** Clinical indication/reason for the precaution. */
    @Column
    private String indication;

    /** Optional clinical notes. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
