CREATE TABLE aeeg_record (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    classification VARCHAR(50) NOT NULL,
    upper_margin_amplitude DOUBLE PRECISION NOT NULL,
    lower_margin_amplitude DOUBLE PRECISION NOT NULL,
    bandwidth DOUBLE PRECISION NOT NULL,
    seizure_detected BOOLEAN NOT NULL DEFAULT FALSE,
    seizure_duration_seconds INTEGER,
    recording_start_time TIMESTAMP WITH TIME ZONE NOT NULL,
    recording_end_time TIMESTAMP WITH TIME ZONE NOT NULL,
    device_identifier VARCHAR(100),
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);
