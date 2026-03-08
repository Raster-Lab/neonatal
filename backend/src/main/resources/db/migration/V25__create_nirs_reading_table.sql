CREATE TABLE nirs_reading (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    site VARCHAR(30) NOT NULL,
    rso2_value DOUBLE PRECISION NOT NULL,
    baseline DOUBLE PRECISION,
    recorded_at TIMESTAMP WITH TIME ZONE NOT NULL,
    device_identifier VARCHAR(100),
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);
