-- This script inserts data into the destination_table from the source_table.
INSERT INTO myschema.destination_table (parent_id, document_no, equipment_id, party_code, current_status, refresh_time,
        receiver_country_code, receiver_country_name, receiver_region_code, receiver_city_code, receiver_city_name,
        sender_country_code, sender_country_name, sender_region_code, sender_city_code, sender_city_name,
        user_id, comments, total_amount, currency_code, category, total_amount_in_usd)
    SELECT parent_id, document_no, equipment_id, party_code, current_status, refresh_time, transportation->>'receiverCountryCode',
        transportation->>'receiverCountryName', transportation->>'receiverRegionCode', transportation->>'receiverCityCode',
        transportation->>'receiverCityName', transportation->>'senderCountryCode', transportation->>'senderCountryName',
        transportation->>'senderRegionCode', transportation->>'senderCityCode', transportation->>'senderCityName',
        user_id, comments, total_amount, currency_code, category, (((total_amount)::DECIMAL(10, 2) *
        (SELECT exchange_rate FROM myschema.currency WHERE currency_code = currency_code))::DECIMAL(10, 2))::text
    FROM myschema.source_table WHERE refresh_time IS NOT NULL AND current_status NOT IN ('COMPLETED', 'FAILED');

-- rollback DELETE FROM myschema.destination_table