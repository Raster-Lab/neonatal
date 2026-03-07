CREATE TABLE patient_insurance (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    insurance_type VARCHAR(20) NOT NULL,
    insurer_name VARCHAR(255) NOT NULL,
    policy_number VARCHAR(100) NOT NULL,
    group_number VARCHAR(100),
    subscriber_name VARCHAR(255),
    subscriber_dob TIMESTAMP,
    relationship_to_patient VARCHAR(255),
    effective_date TIMESTAMP,
    termination_date TIMESTAMP,
    notes TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT fk_patient_insurance_patient FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE RESTRICT
);

CREATE INDEX idx_patient_insurance_patient_id ON patient_insurance(patient_id);
CREATE INDEX idx_patient_insurance_type ON patient_insurance(insurance_type);
CREATE INDEX idx_patient_insurance_patient_type ON patient_insurance(patient_id, insurance_type);
