CREATE TABLE waveform_data (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    waveform_type VARCHAR(30) NOT NULL,
    data_points TEXT NOT NULL,
    sampling_rate_hz DOUBLE PRECISION NOT NULL,
    start_time TIMESTAMP WITH TIME ZONE NOT NULL,
    end_time TIMESTAMP WITH TIME ZONE NOT NULL,
    device_identifier VARCHAR(100),
    unit VARCHAR(20) NOT NULL,
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT fk_waveform_data_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);
