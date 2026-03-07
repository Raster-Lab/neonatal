CREATE TABLE patient_consent (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    consent_type VARCHAR(30) NOT NULL,
    consent_status VARCHAR(20) NOT NULL,
    signed_by VARCHAR(255),
    relationship VARCHAR(255),
    signed_at TIMESTAMP,
    expires_at TIMESTAMP,
    notes TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT fk_patient_consent_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);

CREATE INDEX idx_patient_consent_patient_id ON patient_consent(patient_id);
CREATE INDEX idx_patient_consent_type ON patient_consent(consent_type);
CREATE INDEX idx_patient_consent_status ON patient_consent(consent_status);
CREATE INDEX idx_patient_consent_patient_type ON patient_consent(patient_id, consent_type);
