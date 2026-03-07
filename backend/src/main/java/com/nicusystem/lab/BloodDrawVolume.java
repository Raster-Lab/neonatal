package com.nicusystem.lab;

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
 * JPA entity representing a blood draw volume record for a neonatal patient.
 *
 * <p>Tracking cumulative blood draw volumes is critical for premature infants to
 * prevent iatrogenic anaemia from phlebotomy losses.</p>
 */
@Entity
@Table(name = "blood_draw_volume")
@Getter
@Setter
@NoArgsConstructor
public class BloodDrawVolume extends BaseEntity {

    /** Reference to the patient from whom blood was drawn. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** Reference to the associated lab order, if any. */
    @Column(name = "lab_order_id")
    private UUID labOrderId;

    /** When the blood draw occurred. */
    @Column(name = "drawn_at", nullable = false)
    private Instant drawnAt;

    /** Volume drawn in microliters. */
    @Column(name = "volume_ul", nullable = false)
    private Double volumeUl;

    /** Staff member who performed the blood draw. */
    @Column(name = "drawn_by")
    private String drawnBy;

    /** Additional clinical notes about this blood draw. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
