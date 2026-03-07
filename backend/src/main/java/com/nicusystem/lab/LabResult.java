package com.nicusystem.lab;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.nicusystem.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing a single laboratory result within a panel.
 *
 * <p>Each result corresponds to one specific test (e.g., "Hemoglobin") within a
 * laboratory order. Critical and abnormal flags facilitate clinical alerting.</p>
 */
@Entity
@Table(name = "lab_result")
@Getter
@Setter
@NoArgsConstructor
public class LabResult extends BaseEntity {

    /** Reference to the originating lab order. */
    @Column(name = "lab_order_id", nullable = false)
    private UUID labOrderId;

    /** Reference to the patient. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** Specific test name within the panel (e.g., "Hemoglobin"). */
    @Column(name = "test_name", nullable = false)
    private String testName;

    /** The result value as a string (numeric or qualitative). */
    @Column(name = "result_value", nullable = false)
    private String resultValue;

    /** Unit of measurement (e.g., "g/dL"). */
    @Column(name = "unit")
    private String unit;

    /** Normal range lower bound. */
    @Column(name = "reference_range_low")
    private String referenceRangeLow;

    /** Normal range upper bound. */
    @Column(name = "reference_range_high")
    private String referenceRangeHigh;

    /** Whether this result constitutes a critical value requiring immediate action. */
    @Column(name = "is_critical", nullable = false)
    private boolean isCritical;

    /** Whether this result is outside the normal reference range. */
    @Column(name = "is_abnormal", nullable = false)
    private boolean isAbnormal;

    /** When the result was reported. */
    @Column(name = "resulted_at")
    private Instant resultedAt;

    /** Staff member who reported the result. */
    @Column(name = "resulted_by")
    private String resultedBy;

    /** Additional clinical notes. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
