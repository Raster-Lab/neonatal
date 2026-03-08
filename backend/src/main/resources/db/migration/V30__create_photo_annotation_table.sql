CREATE TABLE photo_annotation (
    id UUID PRIMARY KEY,
    photo_id UUID NOT NULL,
    annotation_type VARCHAR(30) NOT NULL,
    content TEXT,
    x_coordinate DOUBLE PRECISION NOT NULL,
    y_coordinate DOUBLE PRECISION NOT NULL,
    width DOUBLE PRECISION,
    height DOUBLE PRECISION,
    color VARCHAR(20),
    annotated_by VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT
);

CREATE INDEX idx_photo_annotation_photo_id ON photo_annotation (photo_id);
