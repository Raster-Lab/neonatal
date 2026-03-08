package com.nicusystem.flowsheet;

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
 * JPA entity representing a flowsheet entry for hourly documentation.
 */
@Entity
@Table(name = "flowsheet_entry")
@Getter
@Setter
@NoArgsConstructor
public class FlowsheetEntry extends BaseEntity {

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** The category of the flowsheet entry. */
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private FlowsheetCategory category;

    /** When the observation or action occurred. */
    @Column(name = "entry_time", nullable = false)
    private Instant entryTime;

    /** Name of the documented field. */
    @Column(name = "field_name", nullable = false)
    private String fieldName;

    /** Value of the documented field. */
    @Column(name = "field_value", nullable = false, columnDefinition = "TEXT")
    private String fieldValue;

    /** Identifier of the clinician who documented the entry. */
    @Column(name = "documented_by", nullable = false)
    private String documentedBy;

    /** Additional notes about the entry. */
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
