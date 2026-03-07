CREATE TABLE feeding_order (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    feeding_type VARCHAR(30) NOT NULL,
    feeding_route VARCHAR(30) NOT NULL,
    volume_ml DOUBLE PRECISION NOT NULL,
    frequency_hours INTEGER NOT NULL,
    started_at TIMESTAMP,
    discontinued_at TIMESTAMP,
    ordered_by VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);

CREATE INDEX idx_feeding_order_patient_id ON feeding_order(patient_id);
CREATE INDEX idx_feeding_order_feeding_type ON feeding_order(feeding_type);

CREATE TABLE breast_milk_inventory (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    label VARCHAR(255) NOT NULL,
    volume_ml DOUBLE PRECISION NOT NULL,
    collected_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP,
    is_donor_milk BOOLEAN NOT NULL DEFAULT FALSE,
    fortified BOOLEAN NOT NULL DEFAULT FALSE,
    notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);

CREATE INDEX idx_breast_milk_inventory_patient_id ON breast_milk_inventory(patient_id);
