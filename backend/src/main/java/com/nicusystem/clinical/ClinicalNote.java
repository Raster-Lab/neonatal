package com.nicusystem.clinical;

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
 * JPA entity representing a clinical note (SOAP progress note) for a neonatal patient.
 */
@Entity
@Table(name = "clinical_note")
@Getter
@Setter
@NoArgsConstructor
public class ClinicalNote extends BaseEntity {

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The type of clinical note. */
    @Enumerated(EnumType.STRING)
    @Column(name = "note_type", nullable = false)
    private NoteType noteType;

    /** Subjective findings (S in SOAP). */
    @Column(name = "subjective_findings", columnDefinition = "TEXT")
    private String subjectiveFindings;

    /** Objective findings (O in SOAP). */
    @Column(name = "objective_findings", columnDefinition = "TEXT")
    private String objectiveFindings;

    /** Assessment (A in SOAP). */
    @Column(name = "assessment", columnDefinition = "TEXT")
    private String assessment;

    /** Plan (P in SOAP). */
    @Column(name = "plan", columnDefinition = "TEXT")
    private String plan;

    /** Free text for non-SOAP notes. */
    @Column(name = "free_text", columnDefinition = "TEXT")
    private String freeText;

    /** Author identifier. */
    @Column(name = "author_id", nullable = false)
    private String authorId;

    /** Author's role. */
    @Column(name = "author_role")
    private String authorRole;

    /** Co-signer identifier (for residents/students). */
    @Column(name = "co_signer_id")
    private String coSignerId;

    /** When the co-signature was provided. */
    @Column(name = "co_signed_at")
    private Instant coSignedAt;

    /** When the note was recorded. */
    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    /** Whether the note is active (soft delete flag). */
    @Column(name = "active", nullable = false)
    private boolean active = true;
}
