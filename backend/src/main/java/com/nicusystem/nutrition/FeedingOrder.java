package com.nicusystem.nutrition;

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
 * JPA entity representing a feeding order for a neonatal patient.
 *
 * <p>A feeding order prescribes the type, route, volume, and frequency of enteral
 * nutrition for an infant in the NICU. Orders remain active until {@code discontinuedAt}
 * is set.</p>
 */
@Entity
@Table(name = "feeding_order")
@Getter
@Setter
@NoArgsConstructor
public class FeedingOrder extends BaseEntity {

    /** Reference to the patient receiving this feeding order. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The type of feeding (breast milk, formula, donor milk, etc.). */
    @Enumerated(EnumType.STRING)
    @Column(name = "feeding_type", nullable = false)
    private FeedingType feedingType;

    /** The route by which the feeding is delivered. */
    @Enumerated(EnumType.STRING)
    @Column(name = "feeding_route", nullable = false)
    private FeedingRoute feedingRoute;

    /** Volume of feed per administration in millilitres. */
    @Column(name = "volume_ml", nullable = false)
    private Double volumeMl;

    /** How often feeds are given, expressed as hours between feeds. */
    @Column(name = "frequency_hours", nullable = false)
    private Integer frequencyHours;

    /** When this feeding order was started. */
    @Column(name = "started_at")
    private Instant startedAt;

    /** When this feeding order was discontinued; null if still active. */
    @Column(name = "discontinued_at")
    private Instant discontinuedAt;

    /** Name or identifier of the clinician who placed the order. */
    @Column(name = "ordered_by")
    private String orderedBy;

    /** Additional clinical notes about this feeding order. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
