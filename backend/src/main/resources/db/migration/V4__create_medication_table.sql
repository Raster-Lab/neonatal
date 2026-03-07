-- Medication orders table
CREATE TABLE medication (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    dosage DOUBLE PRECISION NOT NULL,
    dosage_unit VARCHAR(50) NOT NULL,
    route VARCHAR(50) NOT NULL,
    frequency VARCHAR(50) NOT NULL,
    status VARCHAR(30) NOT NULL,
    prescribed_at TIMESTAMP,
    prescribed_by VARCHAR(255),
    weight_at_prescription INTEGER,
    notes TEXT,
    high_alert BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT fk_medication_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);

-- Indexes for performance
CREATE INDEX idx_medication_patient_id ON medication(patient_id);
CREATE INDEX idx_medication_status ON medication(status);
CREATE INDEX idx_medication_prescribed_at ON medication(prescribed_at);
