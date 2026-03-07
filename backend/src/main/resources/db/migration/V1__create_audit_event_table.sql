CREATE TABLE audit_event (
    id UUID PRIMARY KEY,
    action VARCHAR(255) NOT NULL,
    resource_type VARCHAR(255),
    resource_id VARCHAR(255),
    user_id VARCHAR(255),
    details TEXT,
    ip_address VARCHAR(45),
    timestamp TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);

CREATE INDEX idx_audit_event_user_id ON audit_event(user_id);
CREATE INDEX idx_audit_event_resource_type ON audit_event(resource_type);
CREATE INDEX idx_audit_event_timestamp ON audit_event(timestamp);
