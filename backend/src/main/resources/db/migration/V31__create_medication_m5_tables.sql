-- M5 Medication Management Tables
-- Drug allergies, IV fluids, TPN, continuous infusions, medication administration,
-- smart pump integration, controlled substances, reconciliation, surfactant,
-- caffeine therapy, phototherapy, antibiotic stewardship, pharmacokinetic monitoring,
-- and medication usage reporting.

-- 1. Drug Allergy
CREATE TABLE drug_allergy (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    allergen_name VARCHAR(255) NOT NULL,
    reaction_type VARCHAR(255) NOT NULL,
    severity VARCHAR(30) NOT NULL,
    notes TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_drug_allergy_patient_id ON drug_allergy (patient_id);
CREATE INDEX idx_drug_allergy_allergen_name ON drug_allergy (allergen_name);

-- 2. IV Fluid Order
CREATE TABLE iv_fluid_order (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    fluid_type VARCHAR(255) NOT NULL,
    base_solution VARCHAR(255) NOT NULL,
    concentration DOUBLE PRECISION NOT NULL,
    concentration_unit VARCHAR(50) NOT NULL,
    rate DOUBLE PRECISION NOT NULL,
    rate_unit VARCHAR(50) NOT NULL,
    total_volume DOUBLE PRECISION,
    start_time TIMESTAMP WITH TIME ZONE,
    end_time TIMESTAMP WITH TIME ZONE,
    status VARCHAR(30) NOT NULL,
    ordered_by VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_iv_fluid_order_patient_id ON iv_fluid_order (patient_id);
CREATE INDEX idx_iv_fluid_order_status ON iv_fluid_order (status);

-- 3. TPN Order
CREATE TABLE tpn_order (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    amino_acids_percent DOUBLE PRECISION NOT NULL,
    dextrose_percent DOUBLE PRECISION NOT NULL,
    lipids_percent DOUBLE PRECISION,
    sodium_meq_per_l DOUBLE PRECISION,
    potassium_meq_per_l DOUBLE PRECISION,
    calcium_mg_per_dl DOUBLE PRECISION,
    magnesium_meq_per_l DOUBLE PRECISION,
    phosphorus_mmol_per_l DOUBLE PRECISION,
    trace_elements_included BOOLEAN NOT NULL DEFAULT FALSE,
    multivitamins_included BOOLEAN NOT NULL DEFAULT FALSE,
    gir DOUBLE PRECISION,
    total_volume_ml DOUBLE PRECISION NOT NULL,
    infusion_rate_ml_per_hr DOUBLE PRECISION NOT NULL,
    cycle_hours INTEGER NOT NULL DEFAULT 24,
    day_number INTEGER NOT NULL DEFAULT 1,
    status VARCHAR(30) NOT NULL,
    ordered_by VARCHAR(255),
    notes TEXT,
    weight_grams INTEGER,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_tpn_order_patient_id ON tpn_order (patient_id);
CREATE INDEX idx_tpn_order_status ON tpn_order (status);

-- 4. Continuous Infusion
CREATE TABLE continuous_infusion (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    drug_name VARCHAR(255) NOT NULL,
    concentration DOUBLE PRECISION NOT NULL,
    concentration_unit VARCHAR(50) NOT NULL,
    rate DOUBLE PRECISION NOT NULL,
    rate_unit VARCHAR(50) NOT NULL,
    dose_per_kg_per_min DOUBLE PRECISION,
    weight_grams INTEGER,
    start_time TIMESTAMP WITH TIME ZONE NOT NULL,
    end_time TIMESTAMP WITH TIME ZONE,
    status VARCHAR(30) NOT NULL,
    ordered_by VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_continuous_infusion_patient_id ON continuous_infusion (patient_id);
CREATE INDEX idx_continuous_infusion_status ON continuous_infusion (status);
CREATE INDEX idx_continuous_infusion_drug_name ON continuous_infusion (drug_name);

-- 5. Medication Administration
CREATE TABLE medication_administration (
    id UUID PRIMARY KEY,
    medication_id UUID NOT NULL,
    patient_id UUID NOT NULL,
    scheduled_time TIMESTAMP WITH TIME ZONE NOT NULL,
    administered_at TIMESTAMP WITH TIME ZONE,
    administered_by VARCHAR(255),
    barcode_verified BOOLEAN NOT NULL DEFAULT FALSE,
    barcode_patient VARCHAR(255),
    barcode_medication VARCHAR(255),
    dose_given DOUBLE PRECISION,
    dose_unit VARCHAR(50),
    route VARCHAR(50),
    site VARCHAR(100),
    status VARCHAR(30) NOT NULL,
    variance_minutes INTEGER,
    notes TEXT,
    is_prn BOOLEAN NOT NULL DEFAULT FALSE,
    prn_reason TEXT,
    prn_effectiveness VARCHAR(255),
    prn_effectiveness_time TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_medication_administration_medication_id ON medication_administration (medication_id);
CREATE INDEX idx_medication_administration_patient_id ON medication_administration (patient_id);
CREATE INDEX idx_medication_administration_status ON medication_administration (status);
CREATE INDEX idx_medication_administration_scheduled_time ON medication_administration (scheduled_time);

-- 6. Smart Pump Integration
CREATE TABLE smart_pump_integration (
    id UUID PRIMARY KEY,
    administration_id UUID NOT NULL,
    pump_device_id VARCHAR(255) NOT NULL,
    pump_channel VARCHAR(50),
    program_name VARCHAR(255),
    programmed_rate DOUBLE PRECISION,
    programmed_dose DOUBLE PRECISION,
    programmed_volume DOUBLE PRECISION,
    actual_rate DOUBLE PRECISION,
    actual_volume_infused DOUBLE PRECISION,
    alert_type VARCHAR(100),
    alert_message TEXT,
    status VARCHAR(30) NOT NULL,
    communication_time TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_smart_pump_integration_administration_id ON smart_pump_integration (administration_id);
CREATE INDEX idx_smart_pump_integration_pump_device_id ON smart_pump_integration (pump_device_id);
CREATE INDEX idx_smart_pump_integration_status ON smart_pump_integration (status);

-- 7. Controlled Substance Log
CREATE TABLE controlled_substance_log (
    id UUID PRIMARY KEY,
    medication_id UUID NOT NULL,
    patient_id UUID NOT NULL,
    drug_name VARCHAR(255) NOT NULL,
    quantity_dispensed DOUBLE PRECISION NOT NULL,
    quantity_administered DOUBLE PRECISION NOT NULL,
    quantity_wasted DOUBLE PRECISION NOT NULL DEFAULT 0,
    waste_witness VARCHAR(255),
    waste_reason TEXT,
    lot_number VARCHAR(255),
    dispensed_at TIMESTAMP WITH TIME ZONE NOT NULL,
    administered_at TIMESTAMP WITH TIME ZONE,
    witnessed_by VARCHAR(255),
    discrepancy_noted BOOLEAN NOT NULL DEFAULT FALSE,
    discrepancy_details TEXT,
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_controlled_substance_log_medication_id ON controlled_substance_log (medication_id);
CREATE INDEX idx_controlled_substance_log_patient_id ON controlled_substance_log (patient_id);
CREATE INDEX idx_controlled_substance_log_drug_name ON controlled_substance_log (drug_name);

-- 8. Medication Reconciliation
CREATE TABLE medication_reconciliation (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    reconciliation_type VARCHAR(50) NOT NULL,
    reconciliation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    performed_by VARCHAR(255) NOT NULL,
    verified_by VARCHAR(255),
    status VARCHAR(30) NOT NULL,
    medications_reviewed INTEGER NOT NULL DEFAULT 0,
    discrepancies_found INTEGER NOT NULL DEFAULT 0,
    discrepancy_details TEXT,
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_medication_reconciliation_patient_id ON medication_reconciliation (patient_id);
CREATE INDEX idx_medication_reconciliation_reconciliation_type ON medication_reconciliation (reconciliation_type);
CREATE INDEX idx_medication_reconciliation_status ON medication_reconciliation (status);

-- 9. Surfactant Administration
CREATE TABLE surfactant_administration (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    surfactant_type VARCHAR(255) NOT NULL,
    dose_mg_per_kg DOUBLE PRECISION NOT NULL,
    total_dose_mg DOUBLE PRECISION NOT NULL,
    administration_method VARCHAR(100) NOT NULL,
    administered_at TIMESTAMP WITH TIME ZONE NOT NULL,
    administered_by VARCHAR(255) NOT NULL,
    dose_number INTEGER NOT NULL DEFAULT 1,
    fio2_before DOUBLE PRECISION,
    fio2_after DOUBLE PRECISION,
    map_before DOUBLE PRECISION,
    map_after DOUBLE PRECISION,
    complications TEXT,
    response VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_surfactant_administration_patient_id ON surfactant_administration (patient_id);

-- 10. Caffeine Therapy
CREATE TABLE caffeine_therapy (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    loading_dose_mg_per_kg DOUBLE PRECISION NOT NULL,
    maintenance_dose_mg_per_kg DOUBLE PRECISION NOT NULL,
    frequency VARCHAR(50) NOT NULL,
    start_date TIMESTAMP WITH TIME ZONE NOT NULL,
    end_date TIMESTAMP WITH TIME ZONE,
    status VARCHAR(30) NOT NULL,
    apnea_count_baseline INTEGER,
    apnea_count_current INTEGER,
    serum_level DOUBLE PRECISION,
    serum_level_date TIMESTAMP WITH TIME ZONE,
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_caffeine_therapy_patient_id ON caffeine_therapy (patient_id);
CREATE INDEX idx_caffeine_therapy_status ON caffeine_therapy (status);

-- 11. Phototherapy Protocol
CREATE TABLE phototherapy_protocol (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    protocol_type VARCHAR(100) NOT NULL,
    start_time TIMESTAMP WITH TIME ZONE NOT NULL,
    end_time TIMESTAMP WITH TIME ZONE,
    device_type VARCHAR(255),
    irradiance_level DOUBLE PRECISION,
    bilirubin_before DOUBLE PRECISION,
    bilirubin_after DOUBLE PRECISION,
    exchange_transfusion BOOLEAN NOT NULL DEFAULT FALSE,
    blood_volume_exchanged_ml DOUBLE PRECISION,
    complications TEXT,
    status VARCHAR(30) NOT NULL,
    ordered_by VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_phototherapy_protocol_patient_id ON phototherapy_protocol (patient_id);
CREATE INDEX idx_phototherapy_protocol_status ON phototherapy_protocol (status);
CREATE INDEX idx_phototherapy_protocol_protocol_type ON phototherapy_protocol (protocol_type);

-- 12. Antibiotic Stewardship
CREATE TABLE antibiotic_stewardship (
    id UUID PRIMARY KEY,
    medication_id UUID NOT NULL,
    patient_id UUID NOT NULL,
    antibiotic_name VARCHAR(255) NOT NULL,
    indication VARCHAR(255) NOT NULL,
    start_date TIMESTAMP WITH TIME ZONE NOT NULL,
    planned_duration_days INTEGER NOT NULL,
    actual_end_date TIMESTAMP WITH TIME ZONE,
    de_escalation_date TIMESTAMP WITH TIME ZONE,
    de_escalated_to VARCHAR(255),
    culture_result VARCHAR(255),
    culture_date TIMESTAMP WITH TIME ZONE,
    reviewed_by VARCHAR(255),
    review_date TIMESTAMP WITH TIME ZONE,
    status VARCHAR(30) NOT NULL,
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_antibiotic_stewardship_patient_id ON antibiotic_stewardship (patient_id);
CREATE INDEX idx_antibiotic_stewardship_medication_id ON antibiotic_stewardship (medication_id);
CREATE INDEX idx_antibiotic_stewardship_status ON antibiotic_stewardship (status);

-- 13. Pharmacokinetic Monitoring
CREATE TABLE pharmacokinetic_monitoring (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    drug_name VARCHAR(255) NOT NULL,
    trough_level DOUBLE PRECISION,
    trough_time TIMESTAMP WITH TIME ZONE,
    peak_level DOUBLE PRECISION,
    peak_time TIMESTAMP WITH TIME ZONE,
    target_trough_min DOUBLE PRECISION,
    target_trough_max DOUBLE PRECISION,
    target_peak_min DOUBLE PRECISION,
    target_peak_max DOUBLE PRECISION,
    dose_adjusted BOOLEAN NOT NULL DEFAULT FALSE,
    new_dose VARCHAR(255),
    interpretation TEXT,
    collected_at TIMESTAMP WITH TIME ZONE NOT NULL,
    reviewed_by VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_pharmacokinetic_monitoring_patient_id ON pharmacokinetic_monitoring (patient_id);
CREATE INDEX idx_pharmacokinetic_monitoring_drug_name ON pharmacokinetic_monitoring (drug_name);

-- 14. Medication Usage Report
CREATE TABLE medication_usage_report (
    id UUID PRIMARY KEY,
    report_date TIMESTAMP WITH TIME ZONE NOT NULL,
    report_type VARCHAR(100) NOT NULL,
    department VARCHAR(255),
    total_orders INTEGER NOT NULL DEFAULT 0,
    total_administrations INTEGER NOT NULL DEFAULT 0,
    high_alert_count INTEGER NOT NULL DEFAULT 0,
    controlled_substance_count INTEGER NOT NULL DEFAULT 0,
    total_cost DOUBLE PRECISION,
    average_turnaround_minutes DOUBLE PRECISION,
    adverse_events INTEGER NOT NULL DEFAULT 0,
    generated_by VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_medication_usage_report_report_date ON medication_usage_report (report_date);
CREATE INDEX idx_medication_usage_report_report_type ON medication_usage_report (report_type);
