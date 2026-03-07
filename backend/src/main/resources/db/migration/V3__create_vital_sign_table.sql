-- Vital sign measurements table
CREATE TABLE vital_sign (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    vital_type VARCHAR(30) NOT NULL,
    "value" DOUBLE PRECISION NOT NULL,
    unit VARCHAR(20) NOT NULL,
    recorded_at TIMESTAMP NOT NULL,
    temperature_site VARCHAR(20),
    manual_entry BOOLEAN NOT NULL DEFAULT FALSE,
    notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT fk_vital_sign_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);

-- Indexes for performance
CREATE INDEX idx_vital_sign_patient_id ON vital_sign(patient_id);
CREATE INDEX idx_vital_sign_type ON vital_sign(vital_type);
CREATE INDEX idx_vital_sign_recorded_at ON vital_sign(recorded_at);
CREATE INDEX idx_vital_sign_patient_type ON vital_sign(patient_id, vital_type);
