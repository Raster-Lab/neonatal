package com.nicusystem.vitals.autodoc;

import java.util.UUID;

import com.nicusystem.common.BaseEntity;
import com.nicusystem.vitals.VitalSignType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing an automated vital signs documentation configuration.
 */
@Entity
@Table(name = "vital_sign_auto_doc_config")
@Getter
@Setter
@NoArgsConstructor
public class AutoDocConfig extends BaseEntity {

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The type of vital sign to auto-document. */
    @Enumerated(EnumType.STRING)
    @Column(name = "vital_type", nullable = false)
    private VitalSignType vitalType;

    /** The documentation interval. */
    @Enumerated(EnumType.STRING)
    @Column(name = "\"interval\"", nullable = false)
    private AutoDocInterval interval;

    /** Whether this auto-doc configuration is enabled. */
    @Column(nullable = false)
    private boolean enabled = true;

    /** Optional notes about the configuration. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
