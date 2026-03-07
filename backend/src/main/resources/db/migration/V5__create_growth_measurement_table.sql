CREATE TABLE growth_measurement (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    measurement_type VARCHAR(30) NOT NULL,
    "value" DOUBLE PRECISION NOT NULL,
    percentile DOUBLE PRECISION,
    z_score DOUBLE PRECISION,
    corrected_age_weeks INTEGER,
    measured_at TIMESTAMP NOT NULL,
    notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT fk_growth_measurement_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);

CREATE INDEX idx_growth_measurement_patient_id ON growth_measurement(patient_id);
CREATE INDEX idx_growth_measurement_type ON growth_measurement(measurement_type);
CREATE INDEX idx_growth_measurement_measured_at ON growth_measurement(measured_at);
CREATE INDEX idx_growth_measurement_patient_type ON growth_measurement(patient_id, measurement_type);
