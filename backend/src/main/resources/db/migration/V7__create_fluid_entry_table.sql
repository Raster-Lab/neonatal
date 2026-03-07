CREATE TABLE fluid_entry (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    entry_type VARCHAR(10) NOT NULL,
    category VARCHAR(30) NOT NULL,
    volume_ml DOUBLE PRECISION NOT NULL,
    description VARCHAR(255),
    recorded_at TIMESTAMP NOT NULL,
    recorded_by VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);

CREATE INDEX idx_fluid_entry_patient_id ON fluid_entry(patient_id);
CREATE INDEX idx_fluid_entry_entry_type ON fluid_entry(entry_type);
CREATE INDEX idx_fluid_entry_recorded_at ON fluid_entry(recorded_at);
CREATE INDEX idx_fluid_entry_patient_recorded ON fluid_entry(patient_id, recorded_at);
