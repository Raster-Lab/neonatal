CREATE TABLE flowsheet_entry (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    category VARCHAR(50) NOT NULL,
    entry_time TIMESTAMP WITH TIME ZONE NOT NULL,
    field_name VARCHAR(100) NOT NULL,
    field_value TEXT NOT NULL,
    documented_by VARCHAR(255) NOT NULL,
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);

CREATE INDEX idx_flowsheet_patient_id ON flowsheet_entry (patient_id);
CREATE INDEX idx_flowsheet_category ON flowsheet_entry (category);
CREATE INDEX idx_flowsheet_entry_time ON flowsheet_entry (entry_time);
CREATE INDEX idx_flowsheet_patient_time ON flowsheet_entry (patient_id, entry_time);
