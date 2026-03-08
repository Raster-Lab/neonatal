CREATE TABLE infection_surveillance (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    surveillance_type VARCHAR(50) NOT NULL,
    event_date TIMESTAMP NOT NULL,
    confirmed BOOLEAN NOT NULL,
    pathogen VARCHAR(255),
    antibiotic_days INTEGER,
    central_line_days INTEGER,
    ventilator_days INTEGER,
    notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT fk_infection_surveillance_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);
CREATE INDEX idx_infection_surveillance_patient_id ON infection_surveillance(patient_id);

CREATE TABLE isolation_precaution (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    precaution_type VARCHAR(50) NOT NULL,
    initiated_at TIMESTAMP NOT NULL,
    discontinued_at TIMESTAMP,
    initiated_by VARCHAR(255),
    indication VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT fk_isolation_precaution_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);
CREATE INDEX idx_isolation_precaution_patient_id ON isolation_precaution(patient_id);
