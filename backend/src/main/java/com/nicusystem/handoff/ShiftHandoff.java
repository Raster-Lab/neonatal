package com.nicusystem.handoff;

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
 * JPA entity representing a structured shift handoff report in I-PASS or SBAR format.
 */
@Entity
@Table(name = "shift_handoff")
@Getter
@Setter
@NoArgsConstructor
public class ShiftHandoff extends BaseEntity {

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The structured communication format used for this handoff. */
    @Enumerated(EnumType.STRING)
    @Column(name = "handoff_format", nullable = false)
    private HandoffFormat handoffFormat;

    /** Timestamp when the handoff occurred. */
    @Column(name = "handoff_at", nullable = false)
    private Instant handoffAt;

    /** Identifier of the provider handing off the patient. */
    @Column(name = "handing_off_provider", nullable = false)
    private String handingOffProvider;

    /** Identifier of the provider receiving the patient. */
    @Column(name = "receiving_provider", nullable = false)
    private String receivingProvider;

    // ── I-PASS fields ─────────────────────────────────────────────────────────

    /** I: Illness severity — stable, watcher, or unstable. */
    @Column(name = "ipass_illness_severity")
    private String ipassIllnessSeverity;

    /** P: Brief patient summary. */
    @Column(name = "ipass_patient_summary", columnDefinition = "TEXT")
    private String ipassPatientSummary;

    /** A: To-do list and action items. */
    @Column(name = "ipass_action_list", columnDefinition = "TEXT")
    private String ipassActionList;

    /** S: Situational awareness and contingency plans. */
    @Column(name = "ipass_situation_awareness", columnDefinition = "TEXT")
    private String ipassSituationAwareness;

    /** S: Read-back and synthesis by the receiving provider. */
    @Column(name = "ipass_synthesis_by_receiver", columnDefinition = "TEXT")
    private String ipassSynthesisByReceiver;

    // ── SBAR fields ───────────────────────────────────────────────────────────

    /** S: Current situation. */
    @Column(name = "sbar_situation", columnDefinition = "TEXT")
    private String sbarSituation;

    /** B: Relevant background. */
    @Column(name = "sbar_background", columnDefinition = "TEXT")
    private String sbarBackground;

    /** A: Clinical assessment. */
    @Column(name = "sbar_assessment", columnDefinition = "TEXT")
    private String sbarAssessment;

    /** R: Recommendation or request. */
    @Column(name = "sbar_recommendation", columnDefinition = "TEXT")
    private String sbarRecommendation;

    // ── General ───────────────────────────────────────────────────────────────

    /** Free-text clinical notes. */
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    /** Whether this handoff record is active (soft delete flag). */
    @Column(name = "active", nullable = false)
    private boolean active = true;
}
