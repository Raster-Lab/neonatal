CREATE TABLE daily_rounding_summary (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    rounding_date DATE NOT NULL,
    presenting_problems TEXT,
    overnight_events TEXT,
    current_vitals_summary TEXT,
    current_medications_summary TEXT,
    assessment_plan TEXT,
    attending_physician VARCHAR(255) NOT NULL,
    participants TEXT,
    action_items TEXT,
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);

CREATE INDEX idx_rounding_patient_id ON daily_rounding_summary (patient_id);
CREATE INDEX idx_rounding_date ON daily_rounding_summary (rounding_date);
CREATE INDEX idx_rounding_patient_date ON daily_rounding_summary (patient_id, rounding_date);
