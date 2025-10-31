-- This script creates a trigger to log changes in the source_table
-- into the audit_history table, capturing the changes made to the records.

-- Create a type for the audit data object
CREATE TYPE audit_data_object AS (
    newValue TEXT,
    timestamp TEXT,
    previousValue TEXT,
    changedProperty TEXT,
    user_id TEXT
)

-- Create a type for the collection of field names and column names
CREATE TYPE col_collection AS (
    field_name TEXT,
    column_name TEXT
)

-- Create a function to extract editable data from transportation JSON
CREATE OR REPLACE FUNCTION myschema.extract_editable_data_from_transportation(field_name TEXT, transportation JSONB)
RETURNS TEXT AS $$
BEGIN
    -- Check the field_name and extract the corresponding value
    IF field_name = 'receiverCityCode' THEN
        RETURN transportation->>'receiverCityCode'::TEXT;
    ELSE IF field_name = 'receiverCityName' THEN
        RETURN transportation->>'receiverCityName'::TEXT;
    END IF;
END;
$$ LANGUAGE plpgsql;

-- Create a function to insert or update the audit history
-- This function will be triggered after an update on the source_table
CREATE OR REPLACE FUNCTION myschema.insert_or_update_audit_history()
RETURNS TRIGGER AS $$
DECLARE
    audit_record RECORD;
    changed_data audit_data_object[];
    old_value TEXT;
    new_value TEXT;
    -- Define a collection of field names and corresponding column names
    column_collection col_collection[] = ARRAY[
        ROW('receiverCityCode', 'transportation'),
        ROW('receiverCityName', 'transportation'),
        ROW('partyCode', 'party_code'),
        ROW('totalAmount', 'total_amount'),
        ROW('currencyCode', 'currency_code'),
        ROW('comments', 'comments')
    ];
BEGIN
    -- Check if the record already exists in the audit_history table
    IF EXISTS (SELECT * INTO audit_record FROM audit_history WHERE parent_id=NEW.parent_id AND document_no=NEW.document_no
            AND equipment_id=NEW.equipment_id) THEN
        -- Record exists, update it
        FOR columns IN column_collection LOOP
            -- Initialize old_value and new_value after checking the column name
            IF column.column_name = 'transportation' THEN
                old_value := myschema.extract_editable_data_from_transportation(column.field_name, OLD.transportation);
                new_value := myschema.extract_editable_data_from_transportation(column.field_name, NEW.transportation);
            ELSE
                EXECUTE format('SELECT ($1).%I::TEXT, ($2).%I::TEXT', columns.column_name, columns.column_name)
                    INTO old_value, new_value USING OLD, NEW;
            END IF;
            -- Check if the old_value is distinct from the new_value and append to changed_data if they are different
            IF old_value IS DISTINCT FROM new_value THEN
                changed_data := array_append(changed_data, ROW(new_value, (CURRENT_TIMESTAMP AT TIME ZONE 'UTC')::TEXT || 'Z',
                    old_value, columns.field_name, current_user)::audit_data_object);
            END IF;
        END LOOP;
        -- Update the existing record with the new changed_data
        UPDATE audit_history SET audit_data = changed_data::jsonb
            WHERE parent_id=NEW.parent_id AND document_no=NEW.document_no AND equipment_id=NEW.equipment_id;
    ELSE
        -- Record does not exist, insert a new one
        FOR column IN column_collection LOOP
            old_value := NULL;
            -- Initialize new_value based on the column name
            IF column.column_name = 'transportation' THEN
                new_value := myschema.extract_editable_data_from_transportation(column.field_name, NEW.transportation);
            ELSE
                new_value := NEW.(column.column_name)::TEXT;
            END IF;
            changed_data := array_append(changed_data, ROW(new_value, (CURRENT_TIMESTAMP AT TIME ZONE 'UTC')::TEXT || 'Z',
                old_value, column.field_name, current_user)::audit_data_object);
        END LOOP;        
        -- Insert the new record into the audit_history table
        INSERT INTO audit_history (parent_id, document_no, equipment_id, audit_data)
            VALUES (NEW.parent_id, NEW.document_no, equipment_id, changed_data::jsonb);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create a trigger that calls the insert_or_update_audit_history function after an update on the source_table
CREATE TRIGGER audit_history_trigger
AFTER UPDATE ON myschema.source_table
FOR EACH ROW
EXECUTE FUNCTION myschema.insert_or_update_audit_history();