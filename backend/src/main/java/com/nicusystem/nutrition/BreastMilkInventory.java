package com.nicusystem.nutrition;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.nicusystem.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing a breast milk inventory entry for a neonatal patient.
 *
 * <p>Tracks individual containers of breast milk (mother's own or donor) assigned
 * to a specific patient, including volume, collection timestamp, expiry, and
 * whether the milk has been fortified.</p>
 */
@Entity
@Table(name = "breast_milk_inventory")
@Getter
@Setter
@NoArgsConstructor
public class BreastMilkInventory extends BaseEntity {

    /** Reference to the patient this milk is assigned to. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** Label or barcode identifier on the milk container. */
    @Column(name = "label", nullable = false)
    private String label;

    /** Volume of milk in millilitres. */
    @Column(name = "volume_ml", nullable = false)
    private Double volumeMl;

    /** Timestamp when the milk was collected. */
    @Column(name = "collected_at", nullable = false)
    private Instant collectedAt;

    /** Timestamp when the milk expires; null if not set. */
    @Column(name = "expires_at")
    private Instant expiresAt;

    /**
     * Whether this milk is from a donor rather than the patient's own mother.
     * Lombok generates {@code isDonorMilk()} getter and {@code setDonorMilk()} setter.
     */
    @Column(name = "is_donor_milk", nullable = false)
    private boolean donorMilk;

    /**
     * Whether this milk has been fortified with a human milk fortifier.
     * Lombok generates {@code isFortified()} getter and {@code setFortified()} setter.
     */
    @Column(name = "fortified", nullable = false)
    private boolean fortified;

    /** Additional clinical notes about this inventory entry. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
