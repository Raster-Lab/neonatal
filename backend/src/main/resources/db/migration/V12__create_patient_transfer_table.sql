CREATE TABLE patient_transfer (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    from_unit VARCHAR(100) NOT NULL,
    to_unit VARCHAR(100) NOT NULL,
    from_facility VARCHAR(255),
    to_facility VARCHAR(255),
    transfer_type VARCHAR(20),
    transfer_reason TEXT,
    transferred_at TIMESTAMP NOT NULL,
    transferred_by VARCHAR(255),
    transport_mode VARCHAR(50),
    notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);

CREATE INDEX idx_patient_transfer_patient_id ON patient_transfer(patient_id);
CREATE INDEX idx_patient_transfer_transferred_at ON patient_transfer(transferred_at);
