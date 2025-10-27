-- Table DDL for model_table
CREATE TABLE IF NOT EXISTS myschema.source_table
(
	parent_id character varying(255) NOT NULL,
    document_no character varying(255) NOT NULL,
    equipment_id character varying(255) NOT NULL,
    party_code character varying(255) NOT NULL,
    refresh_time character varying(255),
    current_status character varying(255) NOT NULL,

    transportation JSONB NOT NULL,

    user_id character varying(255) NOT NULL,
    comments character varying(255) NOT NULL,
    total_amount character varying(255) NOT NULL,
    currency_code character varying(3) NOT NULL,
    category character varying(255) NOT NULL,
    total_amount_in_usd character varying(255) NOT NULL,
    CONSTRAINT source_table_pkey PRIMARY KEY (parent_id, document_no, equipment_id)
);

--rollback DROP TABLE myschema.source_table