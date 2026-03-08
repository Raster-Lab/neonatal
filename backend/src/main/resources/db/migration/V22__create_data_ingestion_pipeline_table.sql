CREATE TABLE data_ingestion_pipeline (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    data_source VARCHAR(50) NOT NULL,
    device_identifier VARCHAR(100) NOT NULL,
    status VARCHAR(30) NOT NULL,
    connection_endpoint VARCHAR(255) NOT NULL,
    polling_interval_seconds INTEGER NOT NULL,
    last_data_received_at TIMESTAMP WITH TIME ZONE,
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT fk_data_ingestion_pipeline_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);
