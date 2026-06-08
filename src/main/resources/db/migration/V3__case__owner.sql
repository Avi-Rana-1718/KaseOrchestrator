DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'cases' AND column_name = 'owner_type'
    ) THEN
        ALTER TABLE cases ADD COLUMN owner_type VARCHAR(50);
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'cases' AND column_name = 'owner_value'
    ) THEN
        ALTER TABLE cases ADD COLUMN owner_value VARCHAR(50);
    END IF;
END $$;