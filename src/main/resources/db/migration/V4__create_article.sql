CREATE TABLE article (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    company_id BIGINT NOT NULL,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    unit_of_measure VARCHAR(30) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_article_company
        FOREIGN KEY (company_id) REFERENCES company (id),

    CONSTRAINT uq_article_company_code
        UNIQUE (company_id, code)
);

GRANT SELECT, INSERT ON TABLE public.article TO stellar_erp_app;
GRANT USAGE ON SEQUENCE public.article_id_seq TO stellar_erp_app;