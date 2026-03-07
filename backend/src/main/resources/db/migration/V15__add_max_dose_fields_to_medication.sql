-- Add maximum dose and adjustment factor columns to medication table
ALTER TABLE medication ADD COLUMN max_dose_mg_kg_per_day DOUBLE PRECISION;
ALTER TABLE medication ADD COLUMN renal_adjustment_factor DOUBLE PRECISION;
ALTER TABLE medication ADD COLUMN hepatic_adjustment_factor DOUBLE PRECISION;
