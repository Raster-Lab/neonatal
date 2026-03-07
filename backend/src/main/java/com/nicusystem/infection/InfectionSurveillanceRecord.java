package com.nicusystem.infection;

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
 * JPA entity representing an infection surveillance event for a neonatal patient.
 */
@Entity
@Table(name = "infection_surveillance")
@Getter
@Setter
@NoArgsConstructor
public class InfectionSurveillanceRecord extends BaseEntity {

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** Type of surveillance event. */
    @Enumerated(EnumType.STRING)
    @Column(name = "surveillance_type", nullable = false)
    private InfectionSurveillanceType surveillanceType;

    /** Date of the infection event. */
    @Column(name = "event_date", nullable = false)
    private Instant eventDate;

    /** Whether the infection was confirmed (vs. suspected). */
    @Column(nullable = false)
    private boolean confirmed;

    /** Pathogen identified (organism name). */
    @Column
    private String pathogen;

    /** Total antibiotic days associated with this event. */
    @Column(name = "antibiotic_days")
    private Integer antibioticDays;

    /** Central line days at time of event (for CLABSI). */
    @Column(name = "central_line_days")
    private Integer centralLineDays;

    /** Ventilator days at time of event (for VAE). */
    @Column(name = "ventilator_days")
    private Integer ventilatorDays;

    /** Optional clinical notes. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
