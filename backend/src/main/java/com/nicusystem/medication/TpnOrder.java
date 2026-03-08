package com.nicusystem.medication;

import com.nicusystem.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * Entity representing a TPN (Total Parenteral Nutrition) order for a neonatal patient.
 */
@Entity
@Table(name = "tpn_order")
@Getter
@Setter
@NoArgsConstructor
public class TpnOrder extends BaseEntity {

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "amino_acids_percent")
    private Double aminoAcidsPercent;

    @Column(name = "dextrose_percent")
    private Double dextrosePercent;

    @Column(name = "lipids_percent")
    private Double lipidsPercent;

    @Column(name = "sodium_meq_per_l")
    private Double sodiumMeqPerL;

    @Column(name = "potassium_meq_per_l")
    private Double potassiumMeqPerL;

    @Column(name = "calcium_mg_per_dl")
    private Double calciumMgPerDl;

    @Column(name = "magnesium_meq_per_l")
    private Double magnesiumMeqPerL;

    @Column(name = "phosphorus_mmol_per_l")
    private Double phosphorusMmolPerL;

    @Column(name = "trace_elements_included")
    private boolean traceElementsIncluded;

    @Column(name = "multivitamins_included")
    private boolean multivitaminsIncluded;

    @Column(name = "gir")
    private Double gir;

    @Column(name = "total_volume_ml")
    private Double totalVolumeMl;

    @Column(name = "infusion_rate_ml_per_hr")
    private Double infusionRateMlPerHr;

    @Column(name = "cycle_hours")
    private int cycleHours = 24;

    @Column(name = "day_number")
    private int dayNumber = 1;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TpnStatus status;

    @Column(name = "ordered_by")
    private String orderedBy;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "weight_grams")
    private Integer weightGrams;
}
