-- This script creates a function to update the source table
-- when a record is inserted to the source_upload table.
CREATE OR REPLACE FUNCTION myschema.update_source_table()
RETURNS TRIGGER AS $$
DECLARE
    var_current_status TEXT;
BEGIN
    -- Check if it is a price update
    IF (NEW.total_amount != NULL AND NEW.currency_code != NULL
            AND NEW.user_id != NULL AND NEW.approval_status != NULL AND NEW.comments != NULL) THEN
        -- If the approval_status is 'APPROVED', set current_status to 'IN_PROGRESS', otherwise set it to 'REJECTED'
        IF (NEW.approval_status = 'APPROVED') THEN
            var_current_status := 'IN_PROGRESS';
        ELSE var_current_status := 'REJECTED';
        END IF;
    -- Check if party or receiver transport details provided
    ELSE IF (NEW.party_code IS NOT NULL OR NEW.receiver_city_code IS NOT NULL OR NEW.receiver_city_name IS NOT NULL) THEN
        var_current_status := 'IN_PROGRESS';
    -- If no updates are made, set current_status to 'OPEN'
    ELSE var_current_status := 'OPEN';
    END IF;
    -- Update the source table with the new values
    UPDATE myschema.source_table
	    SET transportation = jsonb_set(jsonb_set(transportation, '{receiverCityCode}', NEW.receiver_city_code, true),
            '{receiverCityName}', NEW.receiver_city_name, true), total_amount=NEW.total_amount, currency_code=NEW.currency_code,
            approval_status=NEW.approval_status, user_id=NEW.user_id, comments=NEW.comments, current_status=var_current_status
            total_amount_in_usd=(((NEW.total_amount)::DECIMAL(10, 2) *
            (SELECT exchange_rate FROM myschema.currency WHERE currency_code = NEW.currency_code))::DECIMAL(10, 2))::text
	WHERE parent_id=NEW.parent_id AND document_no=NEW.document_no AND equipment_id=NEW.equipment_id;
    -- Delete the record from source_upload table
    DELETE FROM myschema.source_upload
	    WHERE parent_id=NEW.parent_id AND document_no=NEW.document_no AND equipment_id=NEW.equipment_id;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- This script creates a trigger to update the source_download table
-- when a record is updated in the source table, except when the current_status is not 'COMPLETED' or 'FAILED'.
CREATE OR REPLACE TRIGGER update_source_table
AFTER INSERT ON myschema.source_upload
FOR EACH ROW
EXECUTE FUNCTION myschema.update_source_table();