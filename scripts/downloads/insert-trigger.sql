-- This script creates a trigger to insert data into the destination_table
CREATE OR REPLACE FUNCTION myschema.insert_to_destination_table()
RETURNS TRIGGER AS $$
DECLARE
    exchange_rate NUMERIC;
BEGIN
    -- Fetch the exchange rate for the charge currency code
    SELECT exchange_rate INTO exchange_rate FROM myschema.currency WHERE currency_code = NEW.charge_currency_cd;
    -- If the currency code is not found, raise an exception
    IF exchange_rate IS NULL THEN
        RAISE EXCEPTION 'Currency code % not found in currency table', NEW.charge_currency_cd;
    END IF;
    -- Insert the new record into the destination_table table with the exchange rate applied to the total_amount
    INSERT INTO myschema.destination_table (parent_id, document_no, equipment_id, party_code, current_status, refresh_time,
        receiver_country_code, receiver_country_name, receiver_region_code, receiver_city_code, receiver_city_name,
        sender_country_code, sender_country_name, sender_region_code, sender_city_code, sender_city_name,
        user_id, comments, total_amount, currency_code, category, total_amount_in_usd)
    VALUES
        (NEW.parent_id, NEW.document_no, NEW.equipment_id, NEW.party_code, NEW.current_status, NEW.refresh_time,
        NEW.transportation->>'receiverCountryCode', NEW.transportation->>'receiverCountryName', NEW.transportation->>'receiverRegionCode',
        NEW.transportation->>'receiverCityCode', NEW.transportation->>'receiverCityName', NEW.transportation->>'senderCountryCode',
        NEW.transportation->>'senderCountryName', NEW.transportation->>'senderRegionCode', NEW.transportation->>'senderCityCode',
        NEW.transportation->>'senderCityName', NEW.user_id, NEW.comments, NEW.total_amount, NEW.currency_code, NEW.category,
        (((NEW.total_amount)::DECIMAL(10, 2) * exchange_rate)::DECIMAL(10, 2))::text);
    RETURN NULL;
END
$$ LANGUAGE plpgsql;

-- This script creates a trigger to insert data into the destination_table
-- when a new record is inserted into the destination_table.
-- The trigger will only execute if the refresh_time is not null and the current_status is not 'COMPLETED' or 'FAILED'.
CREATE OR REPLACE TRIGGER insert_to_destination_table
AFTER INSERT ON myschema.source_table
FOR EACH ROW
WHEN (NEW.refresh_time IS NOT NULL AND NEW.current_status NOT IN ('COMPLETED', 'FAILED'))
EXECUTE FUNCTION myschema.insert_to_destination_table();

--rollback DROP TRIGGER insert_to_destination_table ON myschema.source_table;
--rollback DROP FUNCTION myschema.insert_to_destination_table();