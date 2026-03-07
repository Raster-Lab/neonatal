CREATE TABLE clinical_note (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    note_type VARCHAR(30) NOT NULL,
    subjective_findings TEXT,
    objective_findings TEXT,
    assessment TEXT,
    plan TEXT,
    free_text TEXT,
    author_id VARCHAR(255) NOT NULL,
    author_role VARCHAR(255),
    co_signer_id VARCHAR(255),
    co_signed_at TIMESTAMP,
    recorded_at TIMESTAMP NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);

CREATE INDEX idx_clinical_note_patient_id ON clinical_note(patient_id);
CREATE INDEX idx_clinical_note_note_type ON clinical_note(note_type);
CREATE INDEX idx_clinical_note_recorded_at ON clinical_note(recorded_at);
CREATE INDEX idx_clinical_note_patient_type ON clinical_note(patient_id, note_type);
