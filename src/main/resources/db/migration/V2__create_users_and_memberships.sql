CREATE TABLE app_user (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    email VARCHAR(254) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,

    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT ck_app_user_email
        CHECK (email = btrim(email) AND email <> ''),

    CONSTRAINT ck_app_user_password_hash
        CHECK (btrim(password_hash) <> ''),

    CONSTRAINT ck_app_user_first_name
        CHECK (btrim(first_name) <> ''),

    CONSTRAINT ck_app_user_last_name
        CHECK (btrim(last_name) <> '')
);

CREATE UNIQUE INDEX uq_app_user_email_lower
    ON app_user (lower(email));


CREATE TABLE company_membership (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    company_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    role VARCHAR(30) NOT NULL,

    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_membership_company
        FOREIGN KEY (company_id)
        REFERENCES company (id),

    CONSTRAINT fk_membership_user
        FOREIGN KEY (user_id)
        REFERENCES app_user (id),

    CONSTRAINT uq_membership_company_user
        UNIQUE (company_id, user_id),

    CONSTRAINT ck_membership_role
        CHECK (role IN ('OWNER', 'ADMIN', 'ACCOUNTANT', 'EMPLOYEE'))
);

CREATE INDEX ix_membership_user_id
    ON company_membership (user_id);