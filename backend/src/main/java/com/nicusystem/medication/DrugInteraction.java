package com.nicusystem.medication;

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
 * JPA entity representing a known drug-drug interaction.
 */
@Entity
@Table(name = "drug_interaction")
@Getter
@Setter
@NoArgsConstructor
public class DrugInteraction extends BaseEntity {

    /** Name of the first drug. */
    @Column(name = "drug1_name", nullable = false)
    private String drug1Name;

    /** Name of the second drug. */
    @Column(name = "drug2_name", nullable = false)
    private String drug2Name;

    /** Severity level of the interaction. */
    @Enumerated(EnumType.STRING)
    @Column(name = "interaction_severity", nullable = false)
    private DrugInteractionSeverity interactionSeverity;

    /** Description of the interaction. */
    @Column(columnDefinition = "TEXT")
    private String description;

    /** Clinical effect of the interaction. */
    @Column(name = "clinical_effect", columnDefinition = "TEXT")
    private String clinicalEffect;

    /** Management recommendation. */
    @Column(columnDefinition = "TEXT")
    private String management;

    /** Whether this interaction record is active. */
    @Column(nullable = false)
    private boolean active = true;
}
