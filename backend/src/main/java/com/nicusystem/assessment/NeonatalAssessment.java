package com.nicusystem.assessment;

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
 * JPA entity representing a neonatal admission or shift assessment covering
 * all body-system assessments specific to the NICU environment.
 */
@Entity
@Table(name = "neonatal_assessment")
@Getter
@Setter
@NoArgsConstructor
public class NeonatalAssessment extends BaseEntity {

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The type of assessment (admission, shift, daily round, discharge). */
    @Enumerated(EnumType.STRING)
    @Column(name = "assessment_type", nullable = false)
    private AssessmentType assessmentType;

    /** Timestamp when the assessment was performed. */
    @Column(name = "assessed_at", nullable = false)
    private Instant assessedAt;

    /** Identifier of the clinician who performed the assessment. */
    @Column(name = "assessed_by", nullable = false)
    private String assessedBy;

    // ── Neurological ──────────────────────────────────────────────────────────

    /** Muscle tone description. */
    @Column(name = "neuro_tone")
    private String neuroTone;

    /** Reflex assessment description. */
    @Column(name = "neuro_reflexes")
    private String neuroReflexes;

    /** Whether seizure activity is present. */
    @Column(name = "neuro_seizure_activity")
    private Boolean neuroSeizureActivity;

    /** Fontanelle assessment description. */
    @Column(name = "neuro_fontanelle_status")
    private String neuroFontanelleStatus;

    // ── Cardiovascular ────────────────────────────────────────────────────────

    /** Perfusion assessment description. */
    @Column(name = "cardiac_perfusion")
    private String cardiacPerfusion;

    /** Capillary refill time in seconds. */
    @Column(name = "cardiac_capillary_refill_seconds")
    private Integer cardiacCapillaryRefillSeconds;

    /** Heart sounds description. */
    @Column(name = "cardiac_heart_sounds")
    private String cardiacHeartSounds;

    /** Pulse quality description. */
    @Column(name = "cardiac_pulses")
    private String cardiacPulses;

    // ── Respiratory ───────────────────────────────────────────────────────────

    /** Breath sounds description. */
    @Column(name = "resp_breath_sounds")
    private String respBreathSounds;

    /** Work of breathing description. */
    @Column(name = "resp_work_of_breathing")
    private String respWorkOfBreathing;

    /** Chest movement description. */
    @Column(name = "resp_chest_movement")
    private String respChestMovement;

    // ── Gastrointestinal ──────────────────────────────────────────────────────

    /** Abdomen assessment description. */
    @Column(name = "gi_abdomen")
    private String giAbdomen;

    /** Whether bowel sounds are present. */
    @Column(name = "gi_bowel_sounds")
    private Boolean giBowelSounds;

    /** Stool pattern description. */
    @Column(name = "gi_stool_pattern")
    private String giStoolPattern;

    /** Feeding tolerance description. */
    @Column(name = "gi_feeding_tolerance")
    private String giFeedingTolerance;

    // ── Genitourinary ─────────────────────────────────────────────────────────

    /** Urine output in mL/kg/hr. */
    @Column(name = "gu_urine_output_ml_per_kg_hr")
    private Double guUrineOutputMlPerKgHr;

    /** Genitalia assessment description. */
    @Column(name = "gu_genitalia_assessment")
    private String guGenitaliaAssessment;

    // ── Musculoskeletal ───────────────────────────────────────────────────────

    /** Extremities assessment description. */
    @Column(name = "mskel_extremities")
    private String mskelExtremities;

    /** Hip assessment description. */
    @Column(name = "mskel_hips")
    private String mskelHips;

    /** Spine assessment description. */
    @Column(name = "mskel_spine")
    private String mskelSpine;

    // ── Integumentary ─────────────────────────────────────────────────────────

    /** Skin integrity description. */
    @Column(name = "integ_skin_integrity")
    private String integSkinIntegrity;

    /** Skin color description. */
    @Column(name = "integ_skin_color")
    private String integSkinColor;

    /** Rashes and lesions description. */
    @Column(name = "integ_rashes")
    private String integRashes;

    /** Whether jaundice is present. */
    @Column(name = "integ_jaundice")
    private Boolean integJaundice;

    /** Braden Q pressure ulcer risk score (0–23). */
    @Column(name = "integ_braden_q_score")
    private Integer integBradenQScore;

    // ── General ───────────────────────────────────────────────────────────────

    /** Free-text clinical notes. */
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
