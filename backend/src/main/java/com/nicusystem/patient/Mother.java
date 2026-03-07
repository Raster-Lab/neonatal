package com.nicusystem.patient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.nicusystem.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing the mother linked to a neonatal patient.
 */
@Entity
@Table(name = "mother")
@Getter
@Setter
@NoArgsConstructor
public class Mother extends BaseEntity {

    /** First name of the mother. */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /** Last name of the mother. */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /** Blood type. */
    @Column(name = "blood_type")
    private String bloodType;

    /** Rh factor (positive or negative). */
    @Column(name = "rh_factor")
    private String rhFactor;

    /** Prenatal care summary. */
    @Column(name = "prenatal_care", columnDefinition = "TEXT")
    private String prenatalCare;

    /** Medications taken during pregnancy. */
    @Column(columnDefinition = "TEXT")
    private String medications;

    /** Known infections during pregnancy. */
    @Column(columnDefinition = "TEXT")
    private String infections;

    /** Whether the record is active (soft delete). */
    @Column(nullable = false)
    private boolean active = true;
}
