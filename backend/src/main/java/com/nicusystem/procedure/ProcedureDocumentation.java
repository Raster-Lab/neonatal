package com.nicusystem.procedure;

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
 * JPA entity representing a procedure documentation record for a neonatal patient.
 */
@Entity
@Table(name = "procedure_documentation")
@Getter
@Setter
@NoArgsConstructor
public class ProcedureDocumentation extends BaseEntity {

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The type of procedure performed. */
    @Enumerated(EnumType.STRING)
    @Column(name = "procedure_type", nullable = false)
    private ProcedureType procedureType;

    /** Identifier of the clinician who performed the procedure. */
    @Column(name = "performed_by", nullable = false)
    private String performedBy;

    /** Identifier of the clinician who assisted. */
    @Column(name = "assisted_by")
    private String assistedBy;

    /** Clinical indication for the procedure. */
    @Column(name = "indication", columnDefinition = "TEXT")
    private String indication;

    /** Technique used during the procedure. */
    @Column(name = "technique", columnDefinition = "TEXT")
    private String technique;

    /** Findings observed during the procedure. */
    @Column(name = "findings", columnDefinition = "TEXT")
    private String findings;

    /** Complications encountered during the procedure. */
    @Column(name = "complications", columnDefinition = "TEXT")
    private String complications;

    /** Additional notes about the procedure. */
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    /** When the procedure was performed. */
    @Column(name = "performed_at", nullable = false)
    private Instant performedAt;
}
