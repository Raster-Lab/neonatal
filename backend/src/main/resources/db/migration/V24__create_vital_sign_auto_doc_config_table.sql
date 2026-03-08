CREATE TABLE vital_sign_auto_doc_config (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    vital_type VARCHAR(50) NOT NULL,
    "interval" VARCHAR(30) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);
