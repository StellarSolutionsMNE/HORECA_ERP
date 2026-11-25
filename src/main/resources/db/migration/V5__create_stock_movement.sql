ALTER TABLE public.article
    ADD CONSTRAINT uq_article_company_id UNIQUE (company_id, id);

CREATE TABLE public.stock_movement (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    company_id BIGINT NOT NULL,
    article_id BIGINT NOT NULL,
    operation_id UUID NOT NULL,
    movement_type VARCHAR(20) NOT NULL,
    quantity_delta NUMERIC(18, 3) NOT NULL,
    recorded_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_stock_movement_article
        FOREIGN KEY (company_id, article_id)
        REFERENCES public.article (company_id, id),

    CONSTRAINT uq_stock_movement_operation
        UNIQUE (company_id, operation_id),

    CONSTRAINT ck_stock_movement_receipt
        CHECK (
            movement_type = 'RECEIPT'
            AND quantity_delta > 0
            AND quantity_delta <> 'NaN'::NUMERIC
        )
);

CREATE INDEX ix_stock_movement_company_article
    ON public.stock_movement (company_id, article_id);

GRANT SELECT, INSERT ON TABLE public.stock_movement TO stellar_erp_app;
GRANT USAGE ON SEQUENCE public.stock_movement_id_seq TO stellar_erp_app;