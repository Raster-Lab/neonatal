CREATE TABLE vital_sign_alarm_threshold (
    id UUID PRIMARY KEY,
    vital_type VARCHAR(30) NOT NULL,
    minimum_gestational_age_weeks INTEGER,
    maximum_gestational_age_weeks INTEGER,
    minimum_weight_grams INTEGER,
    maximum_weight_grams INTEGER,
    low_alarm_value DOUBLE PRECISION,
    high_alarm_value DOUBLE PRECISION,
    critical_low_value DOUBLE PRECISION,
    critical_high_value DOUBLE PRECISION,
    unit VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);

CREATE INDEX idx_vital_alarm_threshold_vital_type ON vital_sign_alarm_threshold(vital_type);
CREATE INDEX idx_vital_alarm_threshold_active ON vital_sign_alarm_threshold(active);
CREATE INDEX idx_vital_alarm_threshold_vital_type_active ON vital_sign_alarm_threshold(vital_type, active);
