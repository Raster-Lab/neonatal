CREATE TABLE patient_photo (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(100) NOT NULL,
    file_size BIGINT NOT NULL,
    photo_data BYTEA,
    description VARCHAR(500),
    captured_at TIMESTAMP NOT NULL,
    captured_by VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT fk_patient_photo_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);

CREATE INDEX idx_patient_photo_patient_id ON patient_photo(patient_id);
