CREATE TABLE respiratory_record (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    support_mode VARCHAR(50) NOT NULL,
    fio2_percent DOUBLE PRECISION,
    peep DOUBLE PRECISION,
    pip DOUBLE PRECISION,
    rate_per_min INTEGER,
    ti_seconds DOUBLE PRECISION,
    map_cmh2o DOUBLE PRECISION,
    flow_lpm DOUBLE PRECISION,
    recorded_at TIMESTAMP NOT NULL,
    recorded_by VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT fk_respiratory_record_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);

CREATE INDEX idx_respiratory_record_patient_id ON respiratory_record(patient_id);
CREATE INDEX idx_respiratory_record_recorded_at ON respiratory_record(recorded_at);

CREATE TABLE apnea_event (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    occurred_at TIMESTAMP NOT NULL,
    duration_seconds INTEGER,
    associated_bradycardia BOOLEAN NOT NULL DEFAULT FALSE,
    lowest_heart_rate INTEGER,
    lowest_spo2 DOUBLE PRECISION,
    stimulation_required BOOLEAN NOT NULL DEFAULT FALSE,
    bagging_required BOOLEAN NOT NULL DEFAULT FALSE,
    caffeine_dose DOUBLE PRECISION,
    notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT fk_apnea_event_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);

CREATE INDEX idx_apnea_event_patient_id ON apnea_event(patient_id);
CREATE INDEX idx_apnea_event_occurred_at ON apnea_event(occurred_at);
