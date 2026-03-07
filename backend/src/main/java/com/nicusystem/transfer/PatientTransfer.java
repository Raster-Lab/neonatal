package com.nicusystem.transfer;

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
 * JPA entity representing a patient transfer event.
 */
@Entity
@Table(name = "patient_transfer")
@Getter
@Setter
@NoArgsConstructor
public class PatientTransfer extends BaseEntity {

    /** The patient being transferred. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The unit the patient is transferred from. */
    @Column(name = "from_unit", length = 100, nullable = false)
    private String fromUnit;

    /** The unit the patient is transferred to. */
    @Column(name = "to_unit", length = 100, nullable = false)
    private String toUnit;

    /** The facility the patient is transferred from (nullable for internal transfers). */
    @Column(name = "from_facility")
    private String fromFacility;

    /** The facility the patient is transferred to (nullable for internal transfers). */
    @Column(name = "to_facility")
    private String toFacility;

    /** Whether this is an internal or external transfer. */
    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_type")
    private PatientTransferType transferType;

    /** Clinical reason for the transfer. */
    @Column(name = "transfer_reason", columnDefinition = "TEXT")
    private String transferReason;

    /** Date and time when the transfer occurred. */
    @Column(name = "transferred_at", nullable = false)
    private Instant transferredAt;

    /** Provider who arranged the transfer. */
    @Column(name = "transferred_by")
    private String transferredBy;

    /** Mode of transport (e.g., GROUND_AMBULANCE, HELICOPTER). */
    @Column(name = "transport_mode", length = 50)
    private String transportMode;

    /** Additional notes about the transfer. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
