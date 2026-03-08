CREATE TABLE procedure_documentation (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    procedure_type VARCHAR(50) NOT NULL,
    performed_by VARCHAR(255) NOT NULL,
    assisted_by VARCHAR(255),
    indication TEXT,
    technique TEXT,
    findings TEXT,
    complications TEXT,
    notes TEXT,
    performed_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);

CREATE INDEX idx_procedure_doc_patient_id ON procedure_documentation (patient_id);
CREATE INDEX idx_procedure_doc_type ON procedure_documentation (procedure_type);
CREATE INDEX idx_procedure_doc_performed_at ON procedure_documentation (performed_at);
