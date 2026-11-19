CREATE TABLE company (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    legal_name VARCHAR(255) NOT NULL,
    tax_id VARCHAR(50) NOT NULL,
    country_code VARCHAR(2) NOT NULL DEFAULT 'ME',

    address_line VARCHAR(255),
    city VARCHAR(100),
    email VARCHAR(254),
    phone VARCHAR(50),

    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_company_country_tax_id
        UNIQUE (country_code, tax_id),

    CONSTRAINT ck_company_legal_name
        CHECK (btrim(legal_name) <> ''),

    CONSTRAINT ck_company_tax_id
        CHECK (btrim(tax_id) <> ''),

    CONSTRAINT ck_company_country_code
        CHECK (country_code ~ '^[A-Z]{2}$')
);