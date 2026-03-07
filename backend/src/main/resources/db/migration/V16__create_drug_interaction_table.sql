-- Drug interaction table for tracking known drug-drug interactions
CREATE TABLE drug_interaction (
    id UUID PRIMARY KEY,
    drug1_name VARCHAR(255) NOT NULL,
    drug2_name VARCHAR(255) NOT NULL,
    interaction_severity VARCHAR(30) NOT NULL,
    description TEXT,
    clinical_effect TEXT,
    management TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);

-- Indexes for performance
CREATE INDEX idx_drug_interaction_drug1 ON drug_interaction(drug1_name);
CREATE INDEX idx_drug_interaction_drug2 ON drug_interaction(drug2_name);
CREATE INDEX idx_drug_interaction_severity ON drug_interaction(interaction_severity);
CREATE INDEX idx_drug_interaction_active ON drug_interaction(active);
