CREATE TABLE neonatal_assessment (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    assessment_type VARCHAR(30) NOT NULL,
    assessed_at TIMESTAMP NOT NULL,
    assessed_by VARCHAR(255) NOT NULL,

    -- Neurological
    neuro_tone VARCHAR(255),
    neuro_reflexes VARCHAR(255),
    neuro_seizure_activity BOOLEAN,
    neuro_fontanelle_status VARCHAR(255),

    -- Cardiovascular
    cardiac_perfusion VARCHAR(255),
    cardiac_capillary_refill_seconds INTEGER,
    cardiac_heart_sounds VARCHAR(255),
    cardiac_pulses VARCHAR(255),

    -- Respiratory
    resp_breath_sounds VARCHAR(255),
    resp_work_of_breathing VARCHAR(255),
    resp_chest_movement VARCHAR(255),

    -- Gastrointestinal
    gi_abdomen VARCHAR(255),
    gi_bowel_sounds BOOLEAN,
    gi_stool_pattern VARCHAR(255),
    gi_feeding_tolerance VARCHAR(255),

    -- Genitourinary
    gu_urine_output_ml_per_kg_hr DOUBLE PRECISION,
    gu_genitalia_assessment VARCHAR(255),

    -- Musculoskeletal
    mskel_extremities VARCHAR(255),
    mskel_hips VARCHAR(255),
    mskel_spine VARCHAR(255),

    -- Integumentary
    integ_skin_integrity VARCHAR(255),
    integ_skin_color VARCHAR(255),
    integ_rashes VARCHAR(255),
    integ_jaundice BOOLEAN,
    integ_braden_q_score INTEGER,

    -- General
    notes TEXT,

    -- Audit fields
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);

CREATE INDEX idx_neonatal_assessment_patient_id ON neonatal_assessment(patient_id);
CREATE INDEX idx_neonatal_assessment_type ON neonatal_assessment(assessment_type);
CREATE INDEX idx_neonatal_assessment_assessed_at ON neonatal_assessment(assessed_at);
CREATE INDEX idx_neonatal_assessment_patient_type ON neonatal_assessment(patient_id, assessment_type);
