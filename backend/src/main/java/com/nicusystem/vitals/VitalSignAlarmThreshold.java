package com.nicusystem.vitals;

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
 * JPA entity representing alarm threshold configuration for a vital sign type in the NICU.
 */
@Entity
@Table(name = "vital_sign_alarm_threshold")
@Getter
@Setter
@NoArgsConstructor
public class VitalSignAlarmThreshold extends BaseEntity {

    /** The type of vital sign this threshold applies to. */
    @Enumerated(EnumType.STRING)
    @Column(name = "vital_type", nullable = false)
    private VitalSignType vitalType;

    /** Minimum gestational age (weeks) this threshold applies to. Null means all ages. */
    @Column(name = "minimum_gestational_age_weeks")
    private Integer minimumGestationalAgeWeeks;

    /** Maximum gestational age (weeks) this threshold applies to. Null means all ages. */
    @Column(name = "maximum_gestational_age_weeks")
    private Integer maximumGestationalAgeWeeks;

    /** Minimum patient weight (grams) this threshold applies to. Null means all weights. */
    @Column(name = "minimum_weight_grams")
    private Integer minimumWeightGrams;

    /** Maximum patient weight (grams) this threshold applies to. Null means all weights. */
    @Column(name = "maximum_weight_grams")
    private Integer maximumWeightGrams;

    /** Value below which a low alarm is triggered. */
    @Column(name = "low_alarm_value")
    private Double lowAlarmValue;

    /** Value above which a high alarm is triggered. */
    @Column(name = "high_alarm_value")
    private Double highAlarmValue;

    /** Value below which a critical low alarm is triggered. */
    @Column(name = "critical_low_value")
    private Double criticalLowValue;

    /** Value above which a critical high alarm is triggered. */
    @Column(name = "critical_high_value")
    private Double criticalHighValue;

    /** Unit of measurement for this threshold. */
    @Column(nullable = false)
    private String unit;

    /** Optional description of when this threshold applies. */
    @Column(name = "description")
    private String description;

    /** Whether this alarm threshold is active. */
    @Column(nullable = false)
    private boolean active = true;
}
