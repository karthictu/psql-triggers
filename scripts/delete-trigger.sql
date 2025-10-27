-- This script creates a trigger that deletes records from the destination_table
CREATE OR REPLACE FUNCTION myschema.delete_from_destination_table()
RETURNS TRIGGER AS $$
BEGIN
    DELETE FROM myschema.destination_table
	WHERE parent_id=NEW.parent_id AND document_no=NEW.document_no AND equipment_id=NEW.equipment_id;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- This script creates a trigger to delete records from the destination_table
-- when the current_status is updated to 'COMPLETED' or 'FAILED' in the source_table.
CREATE OR REPLACE TRIGGER delete_from_destination_table
AFTER UPDATE OF current_status ON myschema.source_table
FOR EACH ROW
WHEN (NEW.current_status IN ('COMPLETED', 'FAILED'))
EXECUTE FUNCTION myschema.delete_from_destination_table();

--rollback DROP TRIGGER delete_from_destination_table ON myschema.source_table;
--rollback DROP FUNCTION myschema.delete_from_destination_table();