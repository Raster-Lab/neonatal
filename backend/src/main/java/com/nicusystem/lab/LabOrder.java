package com.nicusystem.lab;

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
 * JPA entity representing a laboratory order for a neonatal patient.
 *
 * <p>Tracks the lifecycle of a lab panel request from ordering through result reporting.</p>
 */
@Entity
@Table(name = "lab_order")
@Getter
@Setter
@NoArgsConstructor
public class LabOrder extends BaseEntity {

    /** Reference to the patient for whom the order is placed. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The type of laboratory panel ordered. */
    @Enumerated(EnumType.STRING)
    @Column(name = "panel_type", nullable = false)
    private LabPanelType panelType;

    /** Current status of the laboratory order. */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LabOrderStatus status;

    /** When the order was placed. */
    @Column(name = "ordered_at")
    private Instant orderedAt;

    /** Clinician who placed the order. */
    @Column(name = "ordered_by")
    private String orderedBy;

    /** When the specimen was collected. */
    @Column(name = "collected_at")
    private Instant collectedAt;

    /** Staff member who collected the specimen. */
    @Column(name = "collected_by")
    private String collectedBy;

    /** Volume of specimen collected in microliters. */
    @Column(name = "specimen_volume_ul")
    private Double specimenVolumeUl;

    /** Additional clinical notes about this order. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
