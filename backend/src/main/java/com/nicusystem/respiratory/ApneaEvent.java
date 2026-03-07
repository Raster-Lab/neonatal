package com.nicusystem.respiratory;

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
 * JPA entity representing an apnea event for a neonatal patient.
 *
 * <p>Records the occurrence, duration, and clinical context of an apnea episode,
 * including associated bradycardia, oxygen desaturation, and interventions required.</p>
 */
@Entity
@Table(name = "apnea_event")
@Getter
@Setter
@NoArgsConstructor
public class ApneaEvent extends BaseEntity {

    /** Reference to the patient who experienced the apnea event. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** Timestamp when the apnea event occurred. */
    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    /** Duration of the apnea episode in seconds. */
    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    /** Whether a bradycardia episode was associated with this apnea event. */
    @Column(name = "associated_bradycardia", nullable = false)
    private boolean associatedBradycardia;

    /** Lowest heart rate recorded during the event in beats per minute. */
    @Column(name = "lowest_heart_rate")
    private Integer lowestHeartRate;

    /** Lowest oxygen saturation (SpO2) recorded during the event as a percentage. */
    @Column(name = "lowest_spo2")
    private Double lowestSpo2;

    /** Whether tactile or other stimulation was required to resolve the event. */
    @Column(name = "stimulation_required", nullable = false)
    private boolean stimulationRequired;

    /** Whether bag-mask ventilation was required to resolve the event. */
    @Column(name = "bagging_required", nullable = false)
    private boolean baggingRequired;

    /** Caffeine dose administered in mg/kg, if applicable. */
    @Column(name = "caffeine_dose")
    private Double caffeineDose;

    /** Additional clinical notes about the apnea event. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
