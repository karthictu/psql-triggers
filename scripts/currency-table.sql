-- This script creates a currency table in the myschema schema
-- and inserts initial data with exchange rates.
CREATE TABLE IF NOT EXISTS myschema.currency (
    currency_code VARCHAR(3) PRIMARY KEY,
    exchange_rate NUMERIC NOT NULL,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO myschema.currency (currency_code, exchange_rate) VALUES
('USD', 1.0000), ('GBP', 1.2409), ('EUR', 1.0327), ('PLN', 0.25), ('BRL', 0.1710),
('INR', 0.012), ('KRW', 0.00069), ('ZAR', 0.054), ('AED', 0.2723), ('CNY', 0.14),
('SAR', 0.27), ('VND', 0.000039), ('DKK', 0.1384), ('SGD', 0.7373), ('XOF', 0.0015),
('NGN', 0.0022), ('MYR', 0.2237), ('JPY', 0.0066), ('IDR', 0.000066), ('THB', 0.029),
('MAD', 0.100), ('QAR', 0.2747), ('XAF', 0.0016), ('EGP', 0.020), ('SEK', 0.091), ('DZD', 0.0074),
('DJF', 0.0056), ('BGN', 0.53), ('TWD', 0.030), ('NOK', 0.089), ('CHF', 1.10), ('OMR', 2.60),
('KWD', 3.2400), ('HKD', 0.13), ('GNF', 0.00012), ('MMK', 0.0005), ('CVE', 0.0093), ('TND', 0.31),
('MGA', 0.00021), ('GHS', 0.065), ('SDG', 0.0017), ('MRU', 0.0250), ('RON', 0.2100), ('AUD', 0.63)

-- rollback DROP TABLE myschema.currency;
-- rollback DELETE FROM myschema.currency;