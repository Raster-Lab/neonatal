CREATE TABLE lab_order (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    panel_type VARCHAR(50) NOT NULL,
    status VARCHAR(30) NOT NULL,
    ordered_at TIMESTAMP,
    ordered_by VARCHAR(255),
    collected_at TIMESTAMP,
    collected_by VARCHAR(255),
    specimen_volume_ul DOUBLE PRECISION,
    notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT fk_lab_order_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);
CREATE INDEX idx_lab_order_patient_id ON lab_order(patient_id);
CREATE INDEX idx_lab_order_status ON lab_order(status);

CREATE TABLE lab_result (
    id UUID PRIMARY KEY,
    lab_order_id UUID NOT NULL,
    patient_id UUID NOT NULL,
    test_name VARCHAR(255) NOT NULL,
    result_value VARCHAR(255) NOT NULL,
    unit VARCHAR(50),
    reference_range_low VARCHAR(50),
    reference_range_high VARCHAR(50),
    is_critical BOOLEAN NOT NULL DEFAULT FALSE,
    is_abnormal BOOLEAN NOT NULL DEFAULT FALSE,
    resulted_at TIMESTAMP,
    resulted_by VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT fk_lab_result_lab_order FOREIGN KEY (lab_order_id) REFERENCES lab_order(id)
);
CREATE INDEX idx_lab_result_patient_id ON lab_result(patient_id);
CREATE INDEX idx_lab_result_lab_order_id ON lab_result(lab_order_id);
CREATE INDEX idx_lab_result_is_critical ON lab_result(is_critical);

CREATE TABLE blood_draw_volume (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    lab_order_id UUID,
    drawn_at TIMESTAMP NOT NULL,
    volume_ul DOUBLE PRECISION NOT NULL,
    drawn_by VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT fk_blood_draw_volume_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);
CREATE INDEX idx_blood_draw_volume_patient_id ON blood_draw_volume(patient_id);
