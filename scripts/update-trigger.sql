-- This script creates a trigger to update the destination_table
CREATE OR REPLACE FUNCTION myschema.update_destination_table()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE myschema.destination_table
	    SET current_status=NEW.current_status, invoice_payment_term=NEW.invoice_payment_term, cbu_name=NEW.cbu_name, cbu_id=NEW.cbu_id,
            receiver_city_code=NEW.transportation->>'receiverCityCode', receiver_city_name=NEW.transportation->>'receiverCityName',
            sender_city_code=NEW.transportation->>'senderCityCode', sender_city_name=NEW.transportation->>'senderCityName'
            user_id=NEW.user_id, comments=NEW.comments, total_amount=NEW.total_amount, currency_code=NEW.currency_code,
            category=NEW.category, refresh_time=NEW.refresh_time, total_amount_in_usd=(((NEW.total_amount)::DECIMAL(10, 2) *
            (SELECT exchange_rate FROM myschema.currency WHERE currency_code = NEW.currency_code))::DECIMAL(10, 2))::text
	WHERE parent_id=NEW.parent_id AND document_no=NEW.document_no AND equipment_id=NEW.equipment_id;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- This script creates a trigger to update the destination_table
-- when a record is updated in the source_table, except when the current_status is not 'COMPLETED' or 'FAILED'.
CREATE OR REPLACE TRIGGER update_destination_table
AFTER UPDATE ON myschema.source_table
FOR EACH ROW
WHEN (NEW.current_status NOT IN ('COMPLETED', 'FAILED'))
EXECUTE FUNCTION myschema.update_destination_table();

--rollback DROP TRIGGER update_destination_table ON myschema.source_table;
--rollback DROP FUNCTION myschema.update_destination_table();