-- Mother table
CREATE TABLE mother (
    id UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    blood_type VARCHAR(10),
    rh_factor VARCHAR(10),
    prenatal_care TEXT,
    medications TEXT,
    infections TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);

-- Patient table
CREATE TABLE patient (
    id UUID PRIMARY KEY,
    mrn VARCHAR(20) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    date_of_birth TIMESTAMP NOT NULL,
    birth_weight_grams INTEGER,
    birth_length_cm DOUBLE PRECISION,
    head_circumference_cm DOUBLE PRECISION,
    gestational_age_weeks INTEGER,
    gestational_age_days INTEGER,
    delivery_type VARCHAR(20),
    apgar_one_minute INTEGER,
    apgar_five_minute INTEGER,
    apgar_ten_minute INTEGER,
    mother_id UUID,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    admission_date TIMESTAMP,
    bed_number VARCHAR(20),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT fk_patient_mother FOREIGN KEY (mother_id) REFERENCES mother(id)
);

-- Indexes
CREATE INDEX idx_patient_mrn ON patient(mrn);
CREATE INDEX idx_patient_last_name ON patient(last_name);
CREATE INDEX idx_patient_mother_id ON patient(mother_id);
CREATE INDEX idx_patient_active ON patient(active);
CREATE INDEX idx_patient_bed_number ON patient(bed_number);
CREATE INDEX idx_mother_last_name ON mother(last_name);
