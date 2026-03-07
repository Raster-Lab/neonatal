CREATE TABLE shift_handoff (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    handoff_format VARCHAR(10) NOT NULL,
    handoff_at TIMESTAMP NOT NULL,
    handing_off_provider VARCHAR(255) NOT NULL,
    receiving_provider VARCHAR(255) NOT NULL,

    -- I-PASS fields
    ipass_illness_severity VARCHAR(255),
    ipass_patient_summary TEXT,
    ipass_action_list TEXT,
    ipass_situation_awareness TEXT,
    ipass_synthesis_by_receiver TEXT,

    -- SBAR fields
    sbar_situation TEXT,
    sbar_background TEXT,
    sbar_assessment TEXT,
    sbar_recommendation TEXT,

    -- General
    notes TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,

    -- Audit fields
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);

CREATE INDEX idx_shift_handoff_patient_id ON shift_handoff(patient_id);
CREATE INDEX idx_shift_handoff_format ON shift_handoff(handoff_format);
CREATE INDEX idx_shift_handoff_at ON shift_handoff(handoff_at);
