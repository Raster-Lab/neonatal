package com.nicusystem.rounding;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.nicusystem.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing a daily rounding summary for a neonatal patient.
 */
@Entity
@Table(name = "daily_rounding_summary")
@Getter
@Setter
@NoArgsConstructor
public class DailyRoundingSummary extends BaseEntity {

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The date of the rounding. */
    @Column(name = "rounding_date", nullable = false)
    private LocalDate roundingDate;

    /** Presenting problems discussed during the rounding. */
    @Column(name = "presenting_problems", columnDefinition = "TEXT")
    private String presentingProblems;

    /** Significant events that occurred overnight. */
    @Column(name = "overnight_events", columnDefinition = "TEXT")
    private String overnightEvents;

    /** Summary of the patient's current vital signs. */
    @Column(name = "current_vitals_summary", columnDefinition = "TEXT")
    private String currentVitalsSummary;

    /** Summary of the patient's current medications. */
    @Column(name = "current_medications_summary", columnDefinition = "TEXT")
    private String currentMedicationsSummary;

    /** Assessment and plan documented during the rounding. */
    @Column(name = "assessment_plan", columnDefinition = "TEXT")
    private String assessmentPlan;

    /** Attending physician leading the rounding. */
    @Column(name = "attending_physician", nullable = false)
    private String attendingPhysician;

    /** Participants present during the rounding. */
    @Column(name = "participants", columnDefinition = "TEXT")
    private String participants;

    /** Action items identified during the rounding. */
    @Column(name = "action_items", columnDefinition = "TEXT")
    private String actionItems;

    /** Additional notes about the rounding. */
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
