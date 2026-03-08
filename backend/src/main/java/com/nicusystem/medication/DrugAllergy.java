package com.nicusystem.medication;

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
 * JPA entity representing a known drug allergy for a patient.
 */
@Entity
@Table(name = "drug_allergy")
@Getter
@Setter
@NoArgsConstructor
public class DrugAllergy extends BaseEntity {

    /** Patient identifier. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** Name of the allergen. */
    @Column(name = "allergen_name", nullable = false)
    private String allergenName;

    /** Type of allergic reaction. */
    @Column(name = "reaction_type", nullable = false)
    private String reactionType;

    /** Severity level of the allergy. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AllergySeverity severity;

    /** Additional notes about the allergy. */
    @Column(columnDefinition = "TEXT")
    private String notes;

    /** Whether this allergy record is active. */
    @Column(nullable = false)
    private boolean active = true;
}
